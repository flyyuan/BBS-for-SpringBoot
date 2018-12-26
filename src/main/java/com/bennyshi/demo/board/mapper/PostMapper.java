package com.bennyshi.demo.board.mapper;

import com.bennyshi.demo.board.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2018-12-24
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

}
