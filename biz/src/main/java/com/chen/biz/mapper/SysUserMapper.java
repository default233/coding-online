package com.chen.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * SysUser Mapper 层
 * @author danger
 * @date 2021/3/9
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
