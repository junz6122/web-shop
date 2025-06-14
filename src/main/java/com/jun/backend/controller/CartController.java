package com.jun.backend.controller;

import cn.hutool.core.date.DateUtil;
import com.jun.backend.annotation.Authority;
import com.jun.backend.common.Result;
import com.jun.backend.entity.AuthorityType;
import com.jun.backend.entity.Cart;
import com.jun.backend.service.CartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Authority(AuthorityType.requireLogin)
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Resource
    private CartService cartService;

    /*
    select
    */
    //根据购物车id查询
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Long id) {
        return Result.success(cartService.getById(id));
    }
    //查找所有用户的购物车
    @GetMapping
    public Result findAll() {
        List<Cart> list = cartService.list();
        return Result.success(list);
    }
    //查找某个用户的购物车
    @GetMapping("/userid/{userId}")
    public Result selectByUserId(@PathVariable Long userId) {
        return Result.success(cartService.selectByUserId(userId)) ;
    }

    /*
    save
    */
    @PostMapping
    public Result save(@RequestBody Cart cart) {
        cart.setCreateTime(DateUtil.now());
        cartService.saveOrUpdate(cart);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Cart cart) {
        cartService.updateById(cart);
        return Result.success();
    }

    /*
    delete
    */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        cartService.removeById(id);
        return Result.success();
    }





}
