package com.chen.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.pojo.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author danger
 * @date 2021/4/16
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    Long getMaxOrder();
}
