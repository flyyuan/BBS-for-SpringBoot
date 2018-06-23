package com.bennyshi.demo.dao;

import com.bennyshi.demo.bean.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
//这个注解代表这是一个mybatis的操作数据库的类
@Mapper
public interface FileDao {
    @Insert("INSERT INTO file(file_name, file_url," +
            "create_time, update_time) VALUE ( #{fileName}, #{fileUrl},  #{create_time}, #{update_time}) ")
    Boolean  insertFile(@Param("fileName") String fileName, @Param("fileUrl") String fileUrl,
                          @Param("create_time")Date createTime, @Param("update_time") Date updateTime);

    @Select("Select * FROM file")
    List<File> findAll();


}
