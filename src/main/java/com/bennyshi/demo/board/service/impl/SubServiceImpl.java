package com.bennyshi.demo.board.service.impl;

import com.bennyshi.demo.board.entity.Parent;
import com.bennyshi.demo.board.entity.Sub;
import com.bennyshi.demo.board.mapper.SubMapper;
import com.bennyshi.demo.board.service.ISubService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SubServiceImpl extends ServiceImpl<SubMapper, Sub> implements ISubService {
    @Override
    public boolean save(Sub entity) {
        entity.createdDate =  LocalDateTime.now();
        entity.updateDate =  LocalDateTime.now();
        entity.state = 0;
        return super.save(entity);
    }

    @Override
    public boolean updateById(Sub entity) {
        entity.updateDate =  LocalDateTime.now();
        return super.updateById(entity);
    }
}
