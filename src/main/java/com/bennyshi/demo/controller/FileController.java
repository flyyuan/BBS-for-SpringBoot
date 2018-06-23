package com.bennyshi.demo.controller;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bennyshi.demo.ann.LoginRequired;
import com.bennyshi.demo.bean.File;
import com.bennyshi.demo.bean.Info;
import com.bennyshi.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/file/*")
@ResponseBody
public class FileController {

    @Autowired
    FileService fileService;

//    @LoginRequired
//    @RequestMapping(path = "/list", method = RequestMethod.GET)
//    private Object list(@CurrentUser User user){
//        return user;
//    }

    @LoginRequired
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    private List<File> list(){
        return fileService.findAll();
    }

    @LoginRequired
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    private Info upload(@RequestBody File file){
        Info info = new Info();
        try{
        fileService.insertFile(file.getFileName(), file.getFileUrl());
        info.setMsg("上传成功");
        info.setSuccess(true);
        info.setDate(new Date());}
        catch (JWTVerificationException e) {
            info.setSuccess(true);
            info.setDate(new Date());
            info.setMsg(new RuntimeException("数据库操作失败"));}
            return info;
        }
    }
