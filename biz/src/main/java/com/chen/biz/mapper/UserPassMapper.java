package com.chen.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.pojo.UserInfo;
import com.chen.biz.pojo.UserPass;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author danger
 * @date 2021/5/8
 */
@Mapper
public interface UserPassMapper extends BaseMapper<UserPass> {
}
