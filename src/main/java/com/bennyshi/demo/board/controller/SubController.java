package com.bennyshi.demo.board.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bennyshi.demo.ann.CurrentUser;
import com.bennyshi.demo.ann.LoginRequired;
import com.bennyshi.demo.bean.BaseResponse;
import com.bennyshi.demo.bean.User;
import com.bennyshi.demo.board.entity.Parent;
import com.bennyshi.demo.board.entity.Sub;
import com.bennyshi.demo.board.service.impl.ParentServiceImpl;
import com.bennyshi.demo.board.service.impl.SubServiceImpl;
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
 * @author jobob
 * @since 2018-12-24
 */

@Api(tags = "子论坛，就是论坛的子目录")
@RestController
@RequestMapping("/board")
public class SubController extends BaseController {

    @Autowired
    private SubServiceImpl subService;

    @ApiOperation(value = "新建子论坛", notes = "传子论坛的对象，对象中的父论坛id是该子论坛的上层")
    @PostMapping("/sub")
    @LoginRequired
    public BaseResponse save(@ApiParam @Valid @RequestBody Sub sub , BindingResult bindingResult, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
        if(bindingResult.hasErrors()){
            baseResponse.setMsg(bindingResult.getFieldError().getDefaultMessage());
            return baseResponse;
        }
        sub.adminId = user.getId();
        if(subService.save(sub)){
            baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        }else{
            baseResponse = ResponseGenerator.setFailRs(baseResponse);
        }

        return baseResponse;
    }

    @ApiOperation(value = "删除子论坛", notes = "传子论坛id")
    @DeleteMapping("/sub/{id}")
    @LoginRequired
    public BaseResponse delete(@PathVariable int id, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
        Sub sub = subService.getById(id);
        if(sub != null && AdminJudge.admin(user.getId(), sub.adminId, user.getState())){
            sub.state = 1;
            subService.updateById(sub);
            baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("删除失败,论坛ID不存在或非本论坛管理员");
        }
        return baseResponse;
    }

    @ApiOperation(value = "修改子论坛", notes = "修改完传整个json对象")
    @PutMapping("/sub")
    @LoginRequired
    public BaseResponse update(@Valid @RequestBody Sub sub , BindingResult bindingResult, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
        if(bindingResult.hasErrors()){
            baseResponse.setMsg(bindingResult.getFieldError().getDefaultMessage());
            return baseResponse;
        }
        Sub subOrgin = subService.getById(sub.id);
        if( AdminJudge.admin(user.getId(), subOrgin.adminId, user.getState())){
        if(subService.updateById(sub)){
            baseResponse.setSuccess(true);
            baseResponse.setMsg("修改成功");
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("修改失败，参数有误");
        }
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("修改失败，非本论坛管理员");
        }

        return baseResponse;
    }

    @ApiOperation(value = "获取单个子论坛信息", notes = "传子论坛id")
    @GetMapping("/sub/{id}")
    public BaseResponse get(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        Sub sub = subService.getById(id);
        if(sub != null){
            baseResponse.setData(sub);
            baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("获取失败,论坛ID不存在");
        }
        return baseResponse;
    }

    @ApiOperation(value = "子论坛分页查询", notes = "1、parentId是查询指明父论坛下的子论坛，2、currentPage是当前页，3、pageSize是页的长度，4、all为true是查询全部，不分页")
    @GetMapping("/sub")
    public BaseResponse get(@RequestParam(value = "currentPage",required = false, defaultValue = "1") Integer currentPage,
                            @RequestParam(value = "pageSize",required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "parentId",required = false) Integer parentId,
                            @RequestParam(value = "all", required = false, defaultValue = "false")  Boolean all
    ){
        BaseResponse baseResponse = new BaseResponse();
        if (all){
            baseResponse.setData(subService.list());
            baseResponse.setSuccess(true);
            baseResponse.setMsg("获取成功");
        }else {
            Page page = new Page(currentPage,pageSize);
            QueryWrapper<Sub> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("board_parent_id",parentId);
            IPage<Sub> iPage = subService.page(page, queryWrapper);
            baseResponse.setData(iPage);
            baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        }
        return baseResponse;
    }
}
