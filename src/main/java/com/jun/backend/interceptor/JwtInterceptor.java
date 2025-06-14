package com.jun.backend.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jun.backend.constants.Constants;
import com.jun.backend.constants.RedisConstants;
import com.jun.backend.entity.User;
import com.jun.backend.service.UserService;
import com.jun.backend.utils.UserHolder;
import com.jun.backend.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/*
 * The first layer of interceptor, which verifies the user's token and stores the user from Redis into ThreadLocal.
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Resource
    RedisTemplate<String, User> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        // If it is not mapped to a method, pass directly
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // Verify whether the token exists
        if (!StringUtils.hasLength(token)) {
            throw new ServiceException(Constants.TOKEN_ERROR, "Token is invalid, please log in again");
        }
        // Retrieve the user from Redis and store it in ThreadLocal (UserHolder) using the token
        User user = redisTemplate.opsForValue().get(RedisConstants.USER_TOKEN_KEY + token);

        if (user == null) {
            throw new ServiceException(Constants.TOKEN_ERROR, "Token is invalid, please log in again");
        }
        UserHolder.saveUser(user);
        // Reset the expiration time
        redisTemplate.expire(RedisConstants.USER_TOKEN_KEY + token, RedisConstants.USER_TOKEN_TTL, TimeUnit.MINUTES);
        // Verify the token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUsername())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.TOKEN_ERROR, "Token verification failed, please log in again");
        }
        return true;
    }
}
