package com.bennyshi.demo.board.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bennyshi.demo.bean.BaseResponse;
import com.bennyshi.demo.board.entity.Post;
import com.bennyshi.demo.board.entity.PostReply;
import com.bennyshi.demo.board.service.impl.PostReplyServiceImpl;
import com.bennyshi.demo.board.service.impl.PostServiceImpl;
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
@Api(tags = "帖子评论接口")
@RestController
@RequestMapping("/board/post")
public class PostReplyController extends BaseController {

    @Autowired
    private PostReplyServiceImpl postReplyService;

    @ApiOperation(value = "增加评论", notes = "传评论的对象，对象中的帖子id用于指明该评论所属帖子")
    @PostMapping("/postReply")
    public BaseResponse save(@ApiParam @Valid @RequestBody PostReply postReply , BindingResult bindingResult){
        BaseResponse baseResponse = new BaseResponse();
        if(bindingResult.hasErrors()){
            baseResponse.setMsg(bindingResult.getFieldError().getDefaultMessage());
            return baseResponse;
        }
        if(postReplyService.save(postReply)){
            baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        }else{
            baseResponse = ResponseGenerator.setFailRs(baseResponse);
        }

        return baseResponse;
    }

    @ApiOperation(value = "删除评论", notes = "传评论id")
    @DeleteMapping("/postReply/{id}")
    public BaseResponse delete(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        PostReply postReply = postReplyService.getById(id);
        if(postReply != null){
            postReply.state = 1;
            postReplyService.updateById(postReply);
            baseResponse.setSuccess(true);
            baseResponse.setMsg("删除成功");
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("删除失败,论坛ID不存在");
        }
        return baseResponse;
    }

    @ApiOperation(value = "修改评论", notes = "修改完传整个json对象")
    @PutMapping("/postReply")
    public BaseResponse update(@Valid @RequestBody PostReply postReply , BindingResult bindingResult){
        BaseResponse baseResponse = new BaseResponse();
        if(bindingResult.hasErrors()){
            baseResponse.setMsg(bindingResult.getFieldError().getDefaultMessage());
            return baseResponse;
        }
        if(postReplyService.updateById(postReply)){
            baseResponse.setSuccess(true);
            baseResponse.setMsg("修改成功");
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("修改失败，参数有误");
        }

        return baseResponse;
    }

    @ApiOperation(value = "获取单个评论信息", notes = "传评论id")
    @GetMapping("/postReply/{id}")
    public BaseResponse get(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        PostReply postReply = postReplyService.getById(id);
        if(postReply != null){
            baseResponse.setData(postReply);
            baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMsg("获取失败,帖子ID不存在");
        }
        return baseResponse;
    }

    @ApiOperation(value = "评论分页查询", notes = "1、postId是查询帖子下的所有评论，2、currentPage是当前页，3、pageSize是页的长度，4、all为true是查询全部，不分页")
    @GetMapping("/postReply")
    public BaseResponse get(@RequestParam(value = "currentPage",required = false, defaultValue = "1") Integer currentPage,
                            @RequestParam(value = "pageSize",required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "all", required = false, defaultValue = "false")  Boolean all,
                            @RequestParam(value = "postId",required = false) Integer postId
    ){
        BaseResponse baseResponse = new BaseResponse();
        if (all){
            baseResponse.setData(postReplyService.list());
        }else {
            Page page = new Page(currentPage,pageSize);
            QueryWrapper<PostReply> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("post_id",postId);
            IPage<Post> iPage = postReplyService.page(page,queryWrapper);
            baseResponse.setData(iPage);
        }
        baseResponse = ResponseGenerator.setSuccessRs(baseResponse);
        return baseResponse;
    }
}
