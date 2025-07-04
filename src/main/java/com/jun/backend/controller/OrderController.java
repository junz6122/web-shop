package com.jun.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jun.backend.annotation.Authority;
import com.jun.backend.constants.Constants;
import com.jun.backend.common.Result;
import com.jun.backend.entity.AuthorityType;
import com.jun.backend.entity.Order;
import com.jun.backend.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.xml.internal.fastinfoset.stax.events.Util;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
@Authority(AuthorityType.requireLogin)
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    private OrderService orderService;



    /*
    select
    */
    @GetMapping("/userid/{userid}")
    public Result selectByUserId(@PathVariable int userid) {
        return Result.success(orderService.selectByUserId(userid));
    }
    @GetMapping("/orderNo/{orderNo}")
    public Result selectByOrderNo(@PathVariable String orderNo) {
        return Result.success(orderService.selectByOrderNo(orderNo));
    }
    @GetMapping
    public Result findAll() {
        List<Order> list = orderService.list();
        return Result.success(list);
    }

    /*
    分页查询
    */
    @GetMapping("/page")
    public Result findPage(@RequestParam int pageNum,
                           @RequestParam int pageSize,
                           String orderNo,String state){
        IPage<Order> orderPage = new Page<>(pageNum,pageSize);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.ne("state","待付款");
        if(!Util.isEmptyString(state)){
            orderQueryWrapper.eq("state",state);
        }
        if(!Util.isEmptyString(orderNo)){
            orderQueryWrapper.like("order_no",orderNo);
        }

        orderQueryWrapper.orderByDesc("create_time");
        return Result.success(orderService.page(orderPage,orderQueryWrapper));
    }
    /*
    save
    */
    @PostMapping
    public Result save(@RequestBody Order order) {
        String orderNo = orderService.saveOrder(order);
        return Result.success(orderNo);

    }
    //支付订单
    @GetMapping("/paid/{orderNo}")
    public Result payOrder(@PathVariable String orderNo){
        orderService.payOrder(orderNo);
        return Result.success();
    }
    //发货
    @Authority(AuthorityType.requireAuthority)
    @GetMapping("/delivery/{orderNo}")
    public Result delivery(@PathVariable String orderNo){
        orderService.delivery(orderNo);
        return Result.success();
    }
    //确认收货
    @GetMapping("/received/{orderNo}")
    public Result receiveOrder(@PathVariable String orderNo){
        if(orderService.receiveOrder(orderNo)){
            return Result.success();
        }
        else {
            return Result.error(Constants.CODE_500,"确认收货失败");
        }
    }

    @PutMapping
    public Result update(@RequestBody Order order) {
        orderService.updateById(order);
        return Result.success();
    }

    /*
    delete
    */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        orderService.removeById(id);
        return Result.success();
    }





}
