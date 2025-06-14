package com.jun.backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jun.backend.annotation.Authority;
import com.jun.backend.constants.Constants;
import com.jun.backend.common.Result;
import com.jun.backend.entity.AuthorityType;
import com.jun.backend.entity.MyFile;
import com.jun.backend.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;
    //upload file
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file){
        String url = fileService.upload(file);
        return Result.success(url);
    }

    //Download the file based on the file name, which is the URL of the file
    @GetMapping("/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response){
        fileService.download(fileName,response);
    }
    //Delete files based on their IDs
    @Authority(AuthorityType.requireAuthority)
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable int id){
        int i = fileService.fakeDelete(id);
        if(i == 1){
            return Result.success();
        }else{
            return Result.error(Constants.CODE_500,"delete fail");
        }
    }
    //Delete files in bulk
    @Authority(AuthorityType.requireAuthority)
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        for (Integer id : ids) {
            int i = fileService.fakeDelete(id);
            if(i != 1){
                return Result.error(Constants.CODE_500,"删除文件："+fileService.getById(id).getName()+"时失败，删除已终止");
            }
        }
        return Result.success();
    }

    @Authority(AuthorityType.requireAuthority)
    @GetMapping("/enable")
    public Result changeEnable(@RequestParam int id,@RequestParam boolean enable){
        int i = fileService.changeEnable(id, enable);
        if(i == 0){
            return Result.error(Constants.CODE_500,"修改失败");
        }else {
            return Result.success();
        }

    }
    //select
    @GetMapping("/page")
    public Result selectPage(@RequestParam int pageNum,
                           @RequestParam int pageSize,
                           @RequestParam(required = false) String fileName){

        IPage<MyFile> myFileIPage = fileService.selectPage(pageNum, pageSize, fileName);
        return Result.success(myFileIPage);
    }
}
