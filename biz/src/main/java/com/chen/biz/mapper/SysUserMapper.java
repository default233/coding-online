package com.chen.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * SysUser Mapper 层
 * @author danger
 * @date 2021/3/9
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    int updateUserName(@Param("userId") Long userId,@Param("username") String username);
    int updateEmail(@Param("userId") Long userId,@Param("email") String email);
}
