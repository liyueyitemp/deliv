package com.sky.controller.admin;
import com.sky.result.Result;

import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
//接口文档生成
@Api(tags = "common controller")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;


    /**
     * 增加分类
     * @param file the image that needs to be added
     * @return whether the add the successful
     */

    @PostMapping("/upload")
    @ApiOperation(value = "upload image")
    public Result<String> upload(@RequestBody MultipartFile file) {
        //multipartFile is put together by springMVC
        //the variable name file is from the documentation
        log.info("the file that needs to be uploaded ... {}", file);
        //note here we do not use the name file came in with, instead we generate name from uuid to make sure the image name is unique
        //this is so we know which one to fetch
        try {
            // this is processing file name
            String originalFilename = file.getOriginalFilename();
            //this is to get the file form, jpg/png or something like that
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + suffix;

            String filepath = aliOssUtil.upload(file.getBytes(), newFileName);
            return Result.success(filepath);

        } catch (IOException e) {
            log.error("upload image error: ", e);
//            throw new RuntimeException(e);
        }
        return null;
    }


}
