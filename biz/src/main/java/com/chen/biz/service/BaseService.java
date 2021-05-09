package com.chen.biz.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * @author danger
 * @date 2021/4/23
 */
public interface BaseService <T, M extends BaseMapper<T>>{

    public T getById(Long id);
    public T selectOne(Wrapper<T> queryWrapper);
    public List<T> selectList(Wrapper<T> queryWrapper);
    public int insert(T entity);
    public int remove(Long id);
    public int updateByEntity(T entity, Wrapper<T> updateWrapper);
}
