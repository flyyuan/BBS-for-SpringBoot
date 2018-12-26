package com.bennyshi.demo.util;


/**
 * Demo class
 *
 * @author Benny Shi
 * @date 2018/12/26
 */
public class AdminJudge {
    /***
     *   adminState = 3 为管理员状态
     ***/
    static int adminState = 3;
    public static boolean admin(Long userId, Long adminId, int state){
        if (userId.equals(adminId) || state == adminState){
            return true;
        }else{
            return false;
        }
    }
}
