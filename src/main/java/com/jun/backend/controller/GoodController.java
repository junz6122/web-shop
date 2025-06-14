package com.jun.backend.controller;

import com.jun.backend.annotation.Authority;
import com.jun.backend.constants.Constants;
import com.jun.backend.common.Result;
import com.jun.backend.entity.AuthorityType;
import com.jun.backend.entity.Good;
import com.jun.backend.entity.Standard;
import com.jun.backend.service.GoodService;
import com.jun.backend.service.StandardService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/good")
public class GoodController {
    @Resource
    private GoodService goodService;

    @Resource
    private StandardService standardService;



    @Authority(AuthorityType.requireAuthority)
    @PostMapping
    public Result save(@RequestBody Good good) {
        System.out.println(good);
        return Result.success(goodService.saveOrUpdateGood(good));
    }

    @Authority(AuthorityType.requireAuthority)
    @PutMapping
    public Result update(@RequestBody Good good) {
        goodService.update(good);
        return Result.success();
    }

    @Authority(AuthorityType.requireAuthority)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        goodService.deleteGood(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        return Result.success(goodService.getGoodById(id));
    }

    //Get the product's specifications
    @GetMapping("/standard/{id}")
    public Result getStandard(@PathVariable int id) {
        return Result.success(goodService.getStandard(id));
    }
    //Query recommended products, that is, recommend=1
    @GetMapping
    public Result findAll() {

        return Result.success(goodService.findFrontGoods());
    }
    //Check the sales ranking
    @GetMapping("/rank")
    public Result getSaleRank(@RequestParam int num){
        return Result.success(goodService.getSaleRank(num));
    }
    //Save the product's specifications
    @PostMapping("/standard")
    public Result saveStandard(@RequestBody List<Standard> standards, @RequestParam int goodId) {
        //Delete all old records first
        standardService.deleteAll(goodId);
        //Then insert a new record
        for (Standard standard : standards) {
            standard.setGoodId(goodId);
            if(!standardService.save(standard)){
                return Result.error(Constants.CODE_500,"save fail");
            }
        }
        return Result.success();
    }

    //Delete the product's specifications
    @Authority(AuthorityType.requireAuthority)
    @DeleteMapping("/standard")
    public Result delStandard(@RequestBody Standard standard) {
        boolean delete = standardService.delete(standard);
        if(delete) {
            return Result.success();
        }else {
            return Result.error(Constants.CODE_500,"delete fail");
        }
    }

    //Modify the recommendation field for the product
    @Authority(AuthorityType.requireAuthority)
    @GetMapping("/recommend")
    public Result setRecommend(@RequestParam Long id,@RequestParam Boolean isRecommend){
        return Result.success(goodService.setRecommend(id,isRecommend));
    }

    @GetMapping("/page")
    public Result findPage(
                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false, defaultValue = "") String searchText,
                            @RequestParam(required = false) Integer categoryId) {

        return Result.success(goodService.findPage(pageNum,pageSize,searchText,categoryId));
    }
    @GetMapping("/fullPage")
    public Result findFullPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String searchText,
            @RequestParam(required = false) Integer categoryId) {

        return Result.success(goodService.findFullPage(pageNum,pageSize,searchText,categoryId));
    }

}
