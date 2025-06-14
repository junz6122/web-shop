package com.jun.backend.controller;

import com.auth0.jwt.JWT;
import com.jun.backend.annotation.Authority;
import com.jun.backend.common.Result;
import com.jun.backend.entity.AuthorityType;
import com.jun.backend.entity.Good;
import com.jun.backend.service.GoodService;
import com.jun.backend.service.UserService;
import com.jun.backend.entity.Carousel;
import com.jun.backend.service.CarouselService;
import com.jun.backend.entity.User;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/carousel")
public class CarouselController {
    @Resource
    private CarouselService carouselService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UserService userService;
    @Resource
    private GoodService goodService;

    public User getUser() {
        String token = request.getHeader("token");
        String username = JWT.decode(token).getAudience().get(0);
        return userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
    }

    /*
    select
    */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        return Result.success(carouselService.getById(id));
    }

    @GetMapping
    public Result findAll() {
        List<Carousel> list = carouselService.getAllCarousel();
        return Result.success(list);
    }

    /*
    save
    */
    @Authority(AuthorityType.requireAuthority)
    @PostMapping
    public Result save(@RequestBody Carousel carousel) {
        Good good = goodService.getById(carousel.getGoodId());
        if(good == null) {
            return Result.error("400", "商品id错误，未查询到商品id = " + carousel.getGoodId());
        }
        carouselService.saveOrUpdate(carousel);
        return Result.success();
    }
    @Authority(AuthorityType.requireAuthority)
    @PutMapping
    public Result update(@RequestBody Carousel carousel) {
        Good good = goodService.getById(carousel.getGoodId());
        if(good == null) {
            return Result.error("400", "商品id错误，未查询到商品id = " + carousel.getGoodId());
        }
        carouselService.updateById(carousel);
        return Result.success();
    }

    /*
    delete
    */
    @Authority(AuthorityType.requireAuthority)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        carouselService.removeById(id);
        return Result.success();
    }





}
