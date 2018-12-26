package com.bennyshi.demo.board.service.impl;

import com.bennyshi.demo.board.entity.Post;
import com.bennyshi.demo.board.entity.Sub;
import com.bennyshi.demo.board.mapper.PostMapper;
import com.bennyshi.demo.board.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javafx.geometry.Pos;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-12-24
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
    @Override
    public boolean save(Post entity) {
        entity.createdDate =  LocalDateTime.now();
        entity.updateDate =  LocalDateTime.now();
        entity.state = 0;
        return super.save(entity);
    }

    @Override
    public boolean updateById(Post entity) {
        entity.updateDate =  LocalDateTime.now();
        return super.updateById(entity);
    }
}
