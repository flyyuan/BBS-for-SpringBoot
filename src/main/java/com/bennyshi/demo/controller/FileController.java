package com.bennyshi.demo.controller;


import com.bennyshi.demo.ann.CurrentUser;
import com.bennyshi.demo.ann.LoginRequired;
import com.bennyshi.demo.bean.Info;
import com.bennyshi.demo.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file/*")
@ResponseBody
public class FileController {

    @LoginRequired
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    private Object list(@CurrentUser User user){
        return user;
    }
}
