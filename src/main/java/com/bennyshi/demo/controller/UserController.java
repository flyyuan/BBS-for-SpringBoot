package com.bennyshi.demo.controller;

import com.bennyshi.demo.bean.BaseResponse;
import com.bennyshi.demo.bean.InfoWithToken;
import com.bennyshi.demo.bean.User;
import com.bennyshi.demo.service.AuthenticationService;
import com.bennyshi.demo.service.UserService;
import com.bennyshi.demo.util.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "用户接口--登录注册")
@RestController
@RequestMapping("/user/*")
@ResponseBody
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation(value = "注册", notes="传账号密码")
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody User user) {
            BaseResponse baseResponse = new BaseResponse();
            String username = user.getUsername();
            String password = user.getPassword();
            if(username.equals("")){
                baseResponse.setMsg("用户名为空");
                baseResponse.setSuccess(false);
                return baseResponse;
            }
            if(password.equals("")){
                baseResponse.setMsg("密码为空");
                baseResponse.setSuccess(false);
                return baseResponse;
            }
            if(!userService.findByUsername(username).isEmpty()){
                baseResponse.setMsg("用户名重复");
                baseResponse.setSuccess(false);
                return baseResponse;
            }
            Boolean insert = userService.insertByUser(username, password);
            if (insert){
                baseResponse.setMsg("注册成功");
                baseResponse.setSuccess(true);
            }else {
                baseResponse.setMsg("服务器内部错误");
                baseResponse.setSuccess(true);
            }
            return baseResponse;
    }


    @ApiOperation(value = "登录", notes="传账号密码")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public InfoWithToken login(@RequestBody User user){
        InfoWithToken info = new InfoWithToken();
        List<User> userList = userService.findByUsername(user.getUsername());
        if (!userList.isEmpty()){
            User userDb;
            userDb = userList.get(0);
            if( userDb.getPassword().equals(MD5Util.crypt(user.getPassword()))){
                String token = authenticationService.getToken(userDb);
                info.setToken(token);
                info.setDate(new Date());
                info.setMsg(userDb);
                info.setSuccess(true);
                return info;
            }else{
                info.setDate(new Date());
                info.setMsg("密码错误");
                info.setSuccess(false);
            }
            return info;
        }else{
            info.setDate(new Date());
            info.setMsg("账号错误");
            info.setSuccess(false);
            return info;
        }
    }

}
