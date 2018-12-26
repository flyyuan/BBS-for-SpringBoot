package com.bennyshi.demo.controller;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bennyshi.demo.ann.CurrentUser;
import com.bennyshi.demo.ann.LoginRequired;
import com.bennyshi.demo.bean.File;
import com.bennyshi.demo.bean.BaseResponse;
import com.bennyshi.demo.bean.User;
import com.bennyshi.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/file/*")
@ResponseBody
@CrossOrigin

public class FileController {

    @Value("${web.upload-path}")
    private String pathroot;

    @Autowired
    FileService fileService;

    @Autowired
    Environment environment;

//    @LoginRequired
//    @RequestMapping(path = "/list", method = RequestMethod.GET)
//    private Object list(@CurrentUser User user){
//        return user;
//    }


    @RequestMapping(path = "/list", method = RequestMethod.GET)
    @LoginRequired
    private List<File> list(){
        return fileService.findAll();
    }

    @LoginRequired
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    private BaseResponse upload(@RequestBody File file, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
        try{
        fileService.insertFile(file.getFileName(), file.getFileUrl(), user.getUsername());
        baseResponse.setMsg("上传成功");
        baseResponse.setSuccess(true);
        }
        catch (JWTVerificationException e) {
            baseResponse.setSuccess(true);
            baseResponse.setMsg(new RuntimeException("数据库操作失败"));}
            return baseResponse;
        }

    @RequestMapping("upload")
    @LoginRequired
    public BaseResponse springUpload(HttpServletRequest request, @CurrentUser User user) throws IllegalStateException, IOException
    {
        BaseResponse baseResponse = new BaseResponse();

        File myfile = new File();

        String fileName = "";
        String path = "";

        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();

            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    path=pathroot+file.getOriginalFilename();
                    //上传
                    file.transferTo(new java.io.File(path));
                }
                fileName = file.getOriginalFilename();
            }

        }

        myfile.setFileName(fileName);
        myfile.setFileUrl(Inet4Address.getLocalHost().getHostAddress()+":"+environment.getProperty("local.server.port")+"/"+fileName);
        myfile.setCreateMan(user.getUsername());
        fileService.insertFile(myfile.getFileName(), myfile.getFileUrl(), myfile.getCreateMan());

        baseResponse.setSuccess(true);
        baseResponse.setMsg(fileName);
        return baseResponse;
    }

    }
