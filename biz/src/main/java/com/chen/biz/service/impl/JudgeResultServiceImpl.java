package com.chen.biz.service.impl;

import com.chen.biz.mapper.JudgeResultMapper;
import com.chen.biz.pojo.JudgeResult;
import com.chen.biz.service.JudgeResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author danger
 * @date 2021/5/6
 */
@Service
public class JudgeResultServiceImpl extends BaseServiceImpl<JudgeResult, JudgeResultMapper> implements JudgeResultService {
    @Autowired
    private JudgeResultMapper judgeResultMapper;

    @Override
    public List<Integer> getErrorTypeByQuestionId(Long questionId) {
        return judgeResultMapper.getErrorTypeByQuestionId(questionId);
    }

    @Override
    public Integer getSubTimesByQuestionId(Long questionId) {
        return judgeResultMapper.getSubTimesByQuestionId(questionId);
    }

    @Override
    public Integer getSubTimesByUserId(Long userId) {
        return judgeResultMapper.getSubTimesByUserId(userId);
    }

    @Override
    public List<Integer> getErrorTypeByUserId(Long userId) {
        return judgeResultMapper.getErrorTypeByUserId(userId);
    }
}
