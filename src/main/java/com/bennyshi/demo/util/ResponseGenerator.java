package com.bennyshi.demo.util;

import com.bennyshi.demo.bean.BaseResponse;

/**
 * Demo class
 *
 * @author Benny Shi
 * @date 2018/12/24
 */
public class ResponseGenerator {
    public static BaseResponse setSuccessRs(BaseResponse baseResponse){
        baseResponse.setSuccess(true);
        baseResponse.setMsg("成功");
        return baseResponse;
    }

    public static BaseResponse setFailRs(BaseResponse baseResponse){
        baseResponse.setSuccess(false);
        baseResponse.setMsg("失败");
        return baseResponse;
    }

    public static BaseResponse update(boolean state, BaseResponse baseResponse){
        if(state){
            baseResponse.setMsg("修改成功");
            baseResponse.setSuccess(true);
        }else{
            baseResponse.setMsg("修改失败");
            baseResponse.setSuccess(false);
        }
        return  baseResponse;
    }
}
