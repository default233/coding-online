package com.chen.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.pojo.JudgeResult;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author danger
 * @date 2021/5/6
 */
@Mapper
public interface JudgeResultMapper extends BaseMapper<JudgeResult> {
    List<Integer> getErrorTypeByQuestionId(Long questionId);
    List<Integer> getErrorTypeByUserId(Long userId);
    Integer getSubTimesByQuestionId(Long questionId);
    Integer getSubTimesByUserId(Long userId);
}
