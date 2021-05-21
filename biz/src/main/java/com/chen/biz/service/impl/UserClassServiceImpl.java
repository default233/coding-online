package com.chen.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.mapper.UserClassMapper;
import com.chen.biz.pojo.Class;
import com.chen.biz.pojo.UserClass;
import com.chen.biz.service.ClassService;
import com.chen.biz.service.UserClassService;
import com.chen.biz.service.UserInfoService;
import com.chen.biz.vo.UserClassInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author danger
 * @date 2021/5/13
 */
@Service
@Slf4j
public class UserClassServiceImpl extends BaseServiceImpl<UserClass, UserClassMapper> implements UserClassService {

    @Autowired
    private UserClassMapper userClassMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ClassService classService;

    @Override
    @Transactional
    public int authenticate(UserClass userClass) {
        userClassMapper.insert(userClass);
        userInfoService.updateAuth(userClass.getUserId());
        return 0;
    }

    @Override
    @Transactional
    public int deleteClass(String className) {
        Class clazz = classService.getClassByName(className);
        classService.remove(clazz.getClassId());
        UserClass filter = new UserClass();
        filter.setClassId(clazz.getClassId());
        QueryWrapper<UserClass> wrapper = new QueryWrapper<>(filter);
        List<UserClass> userClasses = userClassMapper.selectList(wrapper);
        for (UserClass userClass : userClasses) {
            Long userId = userClass.getUserId();
            userInfoService.deleteAuth(userId);
        }
        userClassMapper.delete(wrapper);
        return 0;
    }

    @Override
    public List<UserClassInformation> getUserListByClassName(String className) {
        Class clazz = classService.getClassByName(className);
        List<UserClassInformation> userList;
        if (clazz == null) {
            userList = userClassMapper.getUserListByClassId(null);
        } else {
            userList = userClassMapper.getUserListByClassId(clazz.getClassId());
        }
        return userList;
    }

    @Override
    @Transactional
    public int deleteUser(String studentName) {
        UserClass filter = new UserClass();
        filter.setRealName(studentName);
        QueryWrapper<UserClass> wrapper = new QueryWrapper<>(filter);
        UserClass userClass = userClassMapper.selectOne(wrapper);
        Long userId = userClass.getUserId();
        // 删除用户认证
        userInfoService.deleteAuth(userId);
        // 删除用户认证信息
        userClassMapper.delete(wrapper);
        return 0;
    }
}
