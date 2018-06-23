package com.bennyshi.demo.service;

import com.bennyshi.demo.bean.File;
import com.bennyshi.demo.dao.FileDao;
import com.bennyshi.demo.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileDao fileDao;

    public Boolean insertFile(String fileName,String fileUrl) {
        Boolean insert = fileDao.insertFile(fileName, fileUrl, new Date(),  new Date());
        return insert;
    }

    public List<File> findAll(){
        return fileDao.findAll();
    }
}
