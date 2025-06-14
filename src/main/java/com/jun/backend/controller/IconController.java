package com.jun.backend.controller;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jun.backend.annotation.Authority;
import com.jun.backend.common.Result;
import com.jun.backend.entity.AuthorityType;
import com.jun.backend.entity.Icon;
import com.jun.backend.entity.User;
import com.jun.backend.service.IconService;
import com.jun.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/icon")
public class IconController {
    @Resource
    private IconService iconService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UserService userService;

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
        return Result.success(iconService.getById(id));
    }

    @GetMapping
    public Result findAll() {
        List<Icon> list = iconService.getIconCategoryMapList();
        return Result.success(list);
    }


    /*
    save
    */
    @Authority(AuthorityType.requireAuthority)
    @PostMapping
    public Result save(@RequestBody Icon icon) {
        iconService.saveOrUpdate(icon);
        return Result.success();
    }

    @Authority(AuthorityType.requireAuthority)
    @PutMapping
    public Result update(@RequestBody Icon icon) {
        iconService.updateById(icon);
        return Result.success();
    }

    /*
     *delete
     */
    @Authority(AuthorityType.requireAuthority)
    @GetMapping("/delete")
    public Map<String, Object> delete(@RequestParam("id") Long id) {
        return iconService.deleteById(id);
    }

}
