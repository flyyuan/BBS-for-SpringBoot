package com.bennyshi.demo.controller;

import com.bennyshi.demo.bean.Info;
import com.bennyshi.demo.bean.InfoWithToken;
import com.bennyshi.demo.bean.User;
import com.bennyshi.demo.service.AuthenticationService;
import com.bennyshi.demo.service.UserService;
import com.bennyshi.demo.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user/*")
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public Info register(@RequestBody User user) {
            Info info = new Info();
            String username = user.getUsername();
            String password = user.getPassword();
            if(username.equals("")){
                info.setMsg("用户名为空");
                info.setSuccess(false);
                info.setDate(new Date());
                return info;
            }
            if(password.equals("")){
                info.setMsg("密码为空");
                info.setSuccess(false);
                info.setDate(new Date());
                return info;
            }

            if(!userService.findByUsername(username).isEmpty()){
                info.setMsg("用户名重复");
                info.setSuccess(false);
                info.setDate(new Date());
                return info;
            }

            Boolean insert = userService.insertByUser(username, password, "0", new Date(), new Date(), "https://pic2.zhimg.com/v2-1e02c1531c33f9460ae82eb88a999cdd_r.jpg");
            if (insert){
                info.setMsg("注册成功");
                info.setSuccess(true);
                info.setDate(new Date());
            }else {
                info.setMsg("服务器内部错误");
                info.setSuccess(true);
                info.setDate(new Date());
            }
            return info;
    }


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public InfoWithToken login(@RequestBody User user){
        InfoWithToken info = new InfoWithToken();
        List<User> userList = userService.findByUsername(user.getPassword());
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
