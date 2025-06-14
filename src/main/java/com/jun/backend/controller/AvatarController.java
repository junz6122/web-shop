package com.jun.backend.controller;

import com.jun.backend.annotation.Authority;
import com.jun.backend.constants.Constants;
import com.jun.backend.common.Result;
import com.jun.backend.entity.AuthorityType;
import com.jun.backend.entity.Avatar;
import com.jun.backend.service.AvatarService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    @Resource
    private AvatarService avatarService;
    //upload avatar
    @PostMapping()
    public Result uploadAvatar(@RequestParam MultipartFile file){
        System.out.println("uploadAvatar====>");
        String url = avatarService.upload(file);
        return Result.success(url);
    }
    //Download the file based on the file name, which is the URL of the file
    @GetMapping("/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response){
        avatarService.download(fileName,response);
    }
    //Delete files based on their IDs
    @Authority(AuthorityType.requireAuthority)
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable int id){
        int i = avatarService.delete(id);
        if(i == 1){
            return Result.success();
        }else{
            return Result.error(Constants.CODE_500,"deleted fail");
        }
    }
    // Paginated queries
    @GetMapping("/page")
    public Result selectPage(@RequestParam int pageNum,
                             @RequestParam int pageSize){
        int index = (pageNum - 1) * pageSize;
        List<Avatar> avatars = avatarService.selectPage(index, pageSize);
        int total = avatarService.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("records",avatars);
        map.put("total",total);
        return Result.success(map);
    }
}
