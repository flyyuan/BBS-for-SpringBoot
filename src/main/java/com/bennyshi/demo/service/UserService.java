package com.bennyshi.demo.service;

import com.bennyshi.demo.dao.UserDao;
import com.bennyshi.demo.bean.User;
import com.bennyshi.demo.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public Boolean insertByUser(String username, String password) {
        password = MD5Util.crypt(password);
        Boolean insert = userDao.insertByUser(username, password, new Date(), new Date());
        return insert;
    }

    public Boolean deleteById(int id) {
        return null;
    }

    public Boolean UpdataById(int id, String password) {
        return null;
    }

    public List<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> findByID(int id) {
        return userDao.findByID(id);
    }
}
