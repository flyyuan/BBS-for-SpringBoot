package com.bennyshi.demo.board.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bennyshi.demo.ann.CurrentUser;
import com.bennyshi.demo.ann.LoginRequired;
import com.bennyshi.demo.bean.BaseResponse;
import com.bennyshi.demo.bean.User;
import com.bennyshi.demo.board.entity.Parent;
import com.bennyshi.demo.board.entity.Post;
import com.bennyshi.demo.board.entity.Sub;
import com.bennyshi.demo.board.service.impl.ParentServiceImpl;
import com.bennyshi.demo.board.service.impl.PostServiceImpl;
import com.bennyshi.demo.board.service.impl.SubServiceImpl;
import com.bennyshi.demo.util.AdminJudge;
import com.bennyshi.demo.util.ResponseGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javafx.geometry.Pos;
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
@Api(tags = "帖子接口")
@RestController
@RequestMapping("/board")
public class PostController extends BaseController {

    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private ParentServiceImpl parentService;
    @Autowired
    private SubServiceImpl subService;

    @ApiOperation(value = "新建帖子", notes = "传帖子的对象，对象中的子论坛id用于指明该帖子所属子论坛")
    @PostMapping("/post")
    @LoginRequired
    public BaseResponse save(@ApiParam @Valid @RequestBody Post post , BindingResult bindingResult, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
        if(bindingResult.hasErrors()){
            baseResponse.setMsg(bindingResult.getFieldError().getDefaultMessage());
            return baseResponse;
        }
        post.adminId = user.getId();
        if(postService.save(post)){
            baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        }else{
            baseResponse = ResponseGenerator.setFailRs(baseResponse);
        }

        return baseResponse;
    }

    @ApiOperation(value = "删除帖子", notes = "传帖子id")
    @DeleteMapping("/post/{id}")
    @LoginRequired
    public BaseResponse delete(@PathVariable int id, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
        Post post = postService.getById(id);
        if(post != null){
            Parent parent = parentService.getById(post.parId);
            Sub sub = subService.getById(post.subId);
            if(AdminJudge.admin(user.getId(), post.adminId, user.getState())
                    || AdminJudge.admin(user.getId(), sub.adminId, user.getState())
                    || AdminJudge.admin(user.getId(), parent.adminId, user.getState())
            ){
                post.state = 1;
                postService.updateById(post);
                baseResponse.setSuccess(true);
                baseResponse.setMsg("删除成功");
            }else{
                baseResponse.setSuccess(false);
                baseResponse.setMsg("删除失败,非贴主或非管理员");
            }
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("删除失败,论坛ID不存在");
        }
        return baseResponse;
    }

    @ApiOperation(value = "修改子论坛", notes = "修改完传整个json对象")
    @PutMapping("/post")
    @LoginRequired
    public BaseResponse update(@Valid @RequestBody Post post , BindingResult bindingResult, @CurrentUser User user){
        BaseResponse baseResponse = new BaseResponse();
        if(bindingResult.hasErrors()){
            baseResponse.setMsg(bindingResult.getFieldError().getDefaultMessage());
            return baseResponse;
        }

        Post postOrgin = postService.getById(post.id);
        Parent parent = parentService.getById(postOrgin.parId);
        Sub sub = subService.getById(postOrgin.subId);

        if(AdminJudge.admin(user.getId(), postOrgin.adminId, user.getState())
                || AdminJudge.admin(user.getId(), sub.adminId, user.getState())
                || AdminJudge.admin(user.getId(), parent.adminId, user.getState())
        ){
            if(postService.updateById(post)){
                baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
            }else{
                baseResponse = ResponseGenerator.setFailRs(baseResponse);
            }
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("删除失败,非贴主或非管理员");
        }

        return baseResponse;
    }

    @ApiOperation(value = "获取单个帖子信息", notes = "传帖子id")
    @GetMapping("/post/{id}")
    public BaseResponse get(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        Post post = postService.getById(id);
        if(post != null){
            baseResponse.setData(post);
            baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("获取失败,帖子ID不存在");
        }
        return baseResponse;
    }

    @ApiOperation(value = "子论坛分页查询", notes = "1、subId是查询指明子论坛下的所有帖子，2、currentPage是当前页，3、pageSize是页的长度，4、all为true是查询全部，不分页")
    @GetMapping("/post")
    public BaseResponse get(@RequestParam(value = "currentPage",required = false, defaultValue = "1") Integer currentPage,
                            @RequestParam(value = "pageSize",required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "all", required = false, defaultValue = "false")  Boolean all,
                            @RequestParam(value = "subId",required = false) Integer subId
    ){
        BaseResponse baseResponse = new BaseResponse();
        if (all){
            baseResponse.setData(postService.list());
        }else {
            Page page = new Page(currentPage,pageSize);
            QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sub_id",subId);
            IPage<Post> iPage = postService.page(page,queryWrapper);
            baseResponse.setData(iPage);
        }
        baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        return baseResponse;
    }
}
