package com.chen.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.exception.UserAlreadyExistException;
import com.chen.biz.mapper.UserInfoMapper;
import com.chen.biz.pojo.SysUser;
import com.chen.biz.pojo.UserInfo;
import com.chen.biz.pojo.UserRanking;
import com.chen.biz.service.SysUserService;
import com.chen.biz.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @author danger
 * @date 2021/4/21
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, UserInfoMapper> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private SysUserService userService;

    @Override
    public int insertUserInfo(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);

    }

    @Override
    @Transactional
    public int updateUsername(SysUser user, String username) {
        SysUser check = userService.selectUserByName(username);
        if (check != null) {
            throw new UserAlreadyExistException("用户名已存在！");
        }
        int userInfo = userInfoMapper.updateUserName(user.getUserId(), username);
        int sysUser = userService.updateUserNameById(user.getUserId(), username);
        return userInfo + sysUser;
    }

    @Override
    @Transactional
    public int updateSex(SysUser user, String sex) {
        return userInfoMapper.updateSex(user.getUserId(), sex);
    }

    @Override
    public int updateEmail(SysUser user, String email) {
        SysUser check = userService.selectUserByEmail(email);
        if (check != null) {
            throw new UserAlreadyExistException("邮箱已被注册！");
        }
        int userInfo = userInfoMapper.updateEmail(user.getUserId(), email);
        int sysUser = userService.updateEmailById(user.getUserId(), email);
        return userInfo + sysUser;
    }

    @Override
    public int updateBirthday(SysUser user, LocalDate birthday) {
        return userInfoMapper.updateBirthday(user.getUserId(), birthday);
    }

    @Override
    public int updateImg(SysUser user, String path) {
        return userInfoMapper.updateImg(user.getUserId(), path);
    }

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        UserInfo filter = new UserInfo();
        filter.setUserId(userId);
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>(filter);
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        return userInfo;
    }

    @Override
    public int updateStatus(Long userId, Integer status) {
        int i;
        if (status.equals(0)) {
            i = userInfoMapper.updateStatusSuccessByUserId(userId);
        } else {
            i = userInfoMapper.updateStatusFailureByUserId(userId);
        }
        return i;
    }

    @Override
    public List<UserRanking> getUserRankingByPassQuestion() {
        return userInfoMapper.getUserRankingByPassQuestion();
    }

    @Override
    public List<UserRanking> getUserRankingByPassRate() {
        return userInfoMapper.getUserRankingByPassRate();
    }
}
