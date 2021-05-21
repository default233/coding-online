package com.chen.biz.service;

import com.chen.biz.mapper.JudgeResultMapper;
import com.chen.biz.mapper.OutputExampleMapper;
import com.chen.biz.pojo.JudgeResult;
import com.chen.biz.pojo.OutputExample;

import java.util.List;
import java.util.Map;

/**
 * @author danger
 * @date 2021/5/6
 */
public interface JudgeResultService extends BaseService<JudgeResult, JudgeResultMapper>{
    List<Integer> getErrorTypeByQuestionId(Long questionId);
    Integer getSubTimesByQuestionId(Long questionId);
    Integer getSubTimesByUserId(Long userId);
    List<Integer> getErrorTypeByUserId(Long userId);
}
