package com.chen.biz.service;

import com.chen.biz.mapper.ClassMapper;
import com.chen.biz.pojo.Class;
import com.chen.biz.vo.ClassInformation;

import java.util.List;

/**
 * @author danger
 * @date 2021/5/13
 */
public interface ClassService extends BaseService<Class, ClassMapper> {
    List<ClassInformation> getAllClass();

    Class getClassByName(String className);

    int updateClassByName(String oldClass, String newClass);

}
