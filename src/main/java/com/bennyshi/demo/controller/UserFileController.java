package com.bennyshi.demo.controller;

import com.bennyshi.demo.ann.CurrentUser;
import com.bennyshi.demo.ann.LoginRequired;
import com.bennyshi.demo.bean.File;
import com.bennyshi.demo.bean.BaseResponse;
import com.bennyshi.demo.bean.User;
import com.bennyshi.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/myfile/*")
@ResponseBody
@CrossOrigin

public class UserFileController {

    @Autowired
    FileService fileService;


    @RequestMapping(path = "/list", method = RequestMethod.GET)
    @LoginRequired
    private List<File> list(@CurrentUser User user){
        return fileService.findMyFile(user.getUsername());
    }

    @RequestMapping(path = "/list", method = RequestMethod.DELETE)
    @LoginRequired
    private BaseResponse delete(@RequestBody File file, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
         baseResponse.setSuccess(fileService.deleteFile(file.getId(), user.getUsername()));
         return baseResponse;
    }

    @RequestMapping(path = "/list", method = RequestMethod.PUT)
    @LoginRequired
    private BaseResponse update(@RequestBody File file, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(fileService.updateFile(file.getId(), user.getUsername(), file.getFileName()));
        return baseResponse;
    }

}
