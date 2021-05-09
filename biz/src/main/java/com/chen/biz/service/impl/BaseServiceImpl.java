package com.chen.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author danger
 * @date 2021/4/23
 */
public class BaseServiceImpl<T, M extends BaseMapper<T>> implements BaseService<T, M> {

    @Autowired
    private M baseMapper;

    @Override
    public T getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public T selectOne(Wrapper<T> queryWrapper) {
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<T> selectList(Wrapper<T> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public int insert(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int remove(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateByEntity(T entity, Wrapper<T> updateWrapper) {
        return baseMapper.update(entity, updateWrapper);
    }
}
