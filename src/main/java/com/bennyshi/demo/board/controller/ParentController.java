package com.bennyshi.demo.board.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bennyshi.demo.ann.CurrentUser;
import com.bennyshi.demo.ann.LoginRequired;
import com.bennyshi.demo.bean.BaseResponse;
import com.bennyshi.demo.bean.User;
import com.bennyshi.demo.board.entity.Parent;
import com.bennyshi.demo.board.service.impl.ParentServiceImpl;
import com.bennyshi.demo.util.AdminJudge;
import com.bennyshi.demo.util.ResponseGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.bennyshi.demo.common.BaseController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bennyShi
 * @since 2018-12-22
 */

@Api(tags = "父论坛接口，就是论坛的根目录")
@RestController
@RequestMapping("/board")
public class ParentController extends BaseController {

    @Autowired
    private ParentServiceImpl parentService;

    @ApiOperation(value = "新建父论坛", notes = "跟着提示的json对象传就可以")
    @PostMapping("/parent")
    @LoginRequired
    public BaseResponse save(@ApiParam @Valid @RequestBody Parent parent , BindingResult bindingResult, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
        if(bindingResult.hasErrors()){
            baseResponse.setMsg(bindingResult.getFieldError().getDefaultMessage());
            return baseResponse;
        }
        parent.adminId = user.getId();
        if(parentService.save(parent)){
            baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        }else{
            baseResponse = ResponseGenerator.setFailRs(baseResponse);
        }

        return baseResponse;
    }

    @ApiOperation(value = "删除父论坛", notes = "传父论坛id")
    @DeleteMapping("/parent/{id}")
    @LoginRequired
    public BaseResponse delete(@PathVariable int id, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
        Parent parent = parentService.getById(id);
        if(parent != null && AdminJudge.admin(user.getId(), parent.adminId, user.getState())){
            parent.state = 1;
            parentService.updateById(parent);
            baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("删除失败，论坛ID不存在或非本论坛管理员");
        }
        return baseResponse;
    }

    @ApiOperation(value = "修改父论坛", notes = "修改完传整个json对象")
    @PutMapping("/parent")
    @LoginRequired
    public BaseResponse update(@Valid @RequestBody Parent parent , BindingResult bindingResult, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
        if(bindingResult.hasErrors()){
            baseResponse.setMsg(bindingResult.getFieldError().getDefaultMessage());
            return baseResponse;
        }
        Parent parentOrgin = parentService.getById(parent.id);
        if( AdminJudge.admin(user.getId(), parentOrgin.adminId, user.getState())){
            if(parentService.updateById(parent)){
                baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
            }else{
                baseResponse = ResponseGenerator.setFailRs(baseResponse);
            }
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("修改失败，非本论坛管理员");
        }

        return baseResponse;
    }


    @ApiOperation(value = "获取单个父论坛信息", notes = "传父论坛id")
    @GetMapping("/parent/{id}")
    public BaseResponse get(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        Parent parent = parentService.getById(id);
        if(parent != null){
            baseResponse.setData(parent);
            baseResponse.setSuccess(true);
            baseResponse.setMsg("获取成功");
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("获取失败,论坛ID不存在");
        }
        return baseResponse;
    }

    @ApiOperation(value = "父论坛分页查询", notes = "1、currentPage是当前页，2、pageSize是页的长度，3、all为true是查询全部，不分页")
    @GetMapping("/parent")
    public BaseResponse get(@RequestParam(value = "currentPage",required = false, defaultValue = "1") Integer currentPage,
                            @RequestParam(value = "pageSize",required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "all", required = false, defaultValue = "false")  Boolean all
                            ){
        BaseResponse baseResponse = new BaseResponse();
        if (all){
            baseResponse.setData(parentService.list());
        }else {
            Page page = new Page(currentPage,pageSize);
            IPage<Parent> iPage = parentService.page(page);
            baseResponse.setData(iPage);
        }
        baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        return baseResponse;
    }

}
