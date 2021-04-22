package com.chen.biz.service;

import com.chen.biz.pojo.SysUser;
import com.chen.biz.pojo.UserInfo;

import java.time.LocalDate;

/**
 * @author danger
 * @date 2021/4/21
 */
public interface UserInfoService {
    int insertUserInfo(UserInfo userInfo);
    int updateUsername(SysUser user, String username);
    int updateSex(SysUser user, String sex);
    int updateEmail(SysUser user, String email);
    int updateBirthday(SysUser user, LocalDate birthday);
    int updateImg(SysUser user, String path);
    UserInfo getUserInfoByUserId(Long userId);
}
