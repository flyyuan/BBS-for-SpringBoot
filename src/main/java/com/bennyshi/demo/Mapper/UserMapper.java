package com.bennyshi.demo.Mapper;

import com.bennyshi.demo.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * t_user 操作：演示两种方式
 * <p>第一种是基于mybatis3.x版本后提供的注解方式<p/>
 * <p>第二种是早期写法，将SQL写在 XML 中<p/>
 *
 */
@Mapper
public interface UserMapper {


    /**
     * 根据用户名查询用户结果集
     *
     * @param username 用户名
     * @return 查询结果
     */
    @Select("SELECT * FROM t_user WHERE username = #{username}")
    List<User> findByUsername(@Param("username") String username);

    /**
     * 用户注册，插入用户信息
     *
     * @param username 用户名; password 密码（经过加密）;
     * @return 插入状态
     */
    @Insert("INSERT INTO user(username, password, " +
            "create_time, update_time, image) VALUE (#{username}, #{password}, #{create_time}, #{update_time}, #{image}) ")
    int insertByUser(@Param("username") String username, @Param("password") String password,
                     @Param("create_time")Date createTime, @Param("update_time") Date updateTime, @Param("image") String image);

    /**
     * 用户修改密码
     *
     * @param username 用户名; password 密码
     * @return 修改状态
     */

    @Update("")

}
