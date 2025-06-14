package com.jun.backend.controller;

import com.jun.backend.annotation.Authority;
import com.jun.backend.constants.Constants;
import com.jun.backend.common.Result;
import com.jun.backend.entity.AuthorityType;
import com.jun.backend.entity.Address;
import com.jun.backend.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Authority(AuthorityType.requireLogin)
@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Resource
    private AddressService addressService;

    /*
    select
    */
    @GetMapping("/{userId}")
    public Result findAllById(@PathVariable Long userId) {
        return Result.success(addressService.findAllById(userId));
    }

    @GetMapping
    public Result findAll() {
        List<Address> list = addressService.list();
        return Result.success(list);
    }


    /*
    save
    */
    @PostMapping
    public Result save(@RequestBody Address address) {
        boolean b = addressService.saveOrUpdate(address);
        if(b){
            return Result.success();
        }else{
            return Result.error(Constants.CODE_500,"保存地址失败");
        }

    }

    @PutMapping
    public Result update(@RequestBody Address address) {
        addressService.updateById(address);
        return Result.success();
    }

    /*
    delete
    */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        addressService.removeById(id);
        return Result.success();
    }





}
