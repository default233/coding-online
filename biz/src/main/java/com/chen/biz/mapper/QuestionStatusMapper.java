package com.chen.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.pojo.QuestionStatus;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author danger
 * @date 2021/5/8
 */
@Mapper
public interface QuestionStatusMapper extends BaseMapper<QuestionStatus> {
    int updateStatusSuccessByQuestionId(Long questionId);
    int updateStatusFailureByQuestionId(Long questionId);
}
