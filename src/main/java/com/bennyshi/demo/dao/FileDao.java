package com.bennyshi.demo.dao;

import com.bennyshi.demo.bean.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
//这个注解代表这是一个mybatis的操作数据库的类
@Mapper
public interface FileDao {
    @Insert("INSERT INTO file(file_name, file_url," +
            "create_man , create_time, update_time) VALUE ( #{fileName}, #{fileUrl}, #{create_man}, #{create_time}, #{update_time}) ")
    Boolean  insertFile(@Param("fileName") String fileName, @Param("fileUrl") String fileUrl,@Param("create_man")String create_man,
                          @Param("create_time")Date createTime, @Param("update_time") Date updateTime);

    @Select("Select * FROM file where state = 1")
    List<File> findAll();

    @Select("Select * FROM file where create_man =  #{create_man} and  state = 1")
    List<File> findMyFile(@Param("create_man")String create_man);

    @Update("UPDATE file SET state = 0 WHERE id = #{id} and create_man =  #{create_man}")
    Boolean deleteFile(@Param("id")String id ,@Param("create_man")String create_man);

    @Update("UPDATE file SET file_name = #{file_name} WHERE id = #{id} and create_man =  #{create_man}")
    Boolean updateFileName(@Param("id")String id ,@Param("create_man")String create_man, @Param("file_name") String fileName);
}
