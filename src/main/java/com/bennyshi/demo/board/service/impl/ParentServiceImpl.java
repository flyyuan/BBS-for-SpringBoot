package com.bennyshi.demo.board.service.impl;

import com.bennyshi.demo.board.entity.Parent;
import com.bennyshi.demo.board.mapper.ParentMapper;
import com.bennyshi.demo.board.service.IParentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-12-22
 */
@Service
public class ParentServiceImpl extends ServiceImpl<ParentMapper, Parent> implements IParentService {
    @Override
    public boolean save(Parent parent) {
        parent.createdDate =  LocalDateTime.now();
        parent.updateDate =  LocalDateTime.now();
        parent.state = 0;
        return super.save(parent);
    }

    @Override
    public boolean updateById(Parent entity) {
        entity.updateDate =  LocalDateTime.now();
        return super.updateById(entity);
    }
}
