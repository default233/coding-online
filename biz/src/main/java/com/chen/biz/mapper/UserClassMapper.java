package com.chen.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.pojo.UserClass;
import com.chen.biz.vo.UserClassInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author danger
 * @date 2021/5/13
 */
@Mapper
public interface UserClassMapper extends BaseMapper<UserClass> {
    List<UserClassInformation> getUserListByClassId(@Param("classId") Long classId);
}
