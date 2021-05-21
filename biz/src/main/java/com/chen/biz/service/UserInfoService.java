package com.chen.biz.service;

import com.chen.biz.mapper.UserInfoMapper;
import com.chen.biz.pojo.SysUser;
import com.chen.biz.pojo.UserInfo;
import com.chen.biz.pojo.UserRanking;

import java.time.LocalDate;
import java.util.List;

/**
 * @author danger
 * @date 2021/4/21
 */
public interface UserInfoService extends BaseService<UserInfo, UserInfoMapper> {
    int insertUserInfo(UserInfo userInfo);
    int updateUsername(SysUser user, String username);
    int updateSex(SysUser user, String sex);
    int updateEmail(SysUser user, String email);
    int updateBirthday(SysUser user, LocalDate birthday);
    int updateImg(SysUser user, String path);
    int updateAuth(Long userId);
    int deleteAuth(Long userId);
    UserInfo getUserInfoByUserId(Long userId);
    int updateStatus(Long userId, Integer status);
    List<UserRanking> getUserRankingByPassQuestion();
    List<UserRanking> getUserRankingByPassRate();
}
