package com.chen.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.pojo.UserInfo;
import com.chen.biz.pojo.UserRanking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * @author danger
 * @date 2021/4/21
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    int updateUserName(@Param("userId")Long userId,@Param("username") String username);
    int updateSex(@Param("userId") Long userId,@Param("sex") String sex);
    int updateBirthday(@Param("userId") Long userId,@Param("birthday") LocalDate birthday);
    int updateImg(@Param("userId") Long userId,@Param("path") String path);
    int updateEmail(@Param("userId") Long userId,@Param("email") String email);
    int updateStatusSuccessByUserId(Long userId);
    int updateStatusFailureByUserId(Long userId);
    List<UserRanking> getUserRankingByPassQuestion();
    List<UserRanking> getUserRankingByPassRate();
    int updateAuth(Long userId);
    int deleteAuth(Long userId);
}
