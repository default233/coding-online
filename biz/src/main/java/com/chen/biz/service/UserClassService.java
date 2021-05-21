package com.chen.biz.service;

import com.chen.biz.mapper.UserClassMapper;
import com.chen.biz.pojo.UserClass;
import com.chen.biz.vo.UserClassInformation;

import java.util.List;

/**
 * @author danger
 * @date 2021/5/13
 */
public interface UserClassService extends BaseService<UserClass, UserClassMapper> {
    int authenticate(UserClass userClass);
    int deleteClass(String className);
    List<UserClassInformation> getUserListByClassName(String className);
    int deleteUser(String studentName);
}
