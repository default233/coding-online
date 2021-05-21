package com.chen.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.mapper.ClassMapper;
import com.chen.biz.pojo.Class;
import com.chen.biz.service.ClassService;
import com.chen.biz.vo.ClassInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author danger
 * @date 2021/5/13
 */
@Slf4j
@Service
public class ClassServiceImpl extends BaseServiceImpl<Class, ClassMapper> implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public List<ClassInformation> getAllClass() {
        return classMapper.getAllClass();
    }

    @Override
    public Class getClassByName(String className) {
        if (!StringUtils.hasLength(className)) {
            return null;
        }
        Class filter = new Class();
        filter.setClassName(className);
        QueryWrapper<Class> wrapper = new QueryWrapper<>(filter);
        return classMapper.selectOne(wrapper);
    }

    @Override
    public int updateClassByName(String oldClass, String newClass) {
        return classMapper.updateClassByName(oldClass, newClass);
    }

}
