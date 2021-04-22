package com.chen.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

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
}
