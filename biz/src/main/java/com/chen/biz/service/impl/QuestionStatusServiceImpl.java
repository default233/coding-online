package com.chen.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.mapper.QuestionStatusMapper;
import com.chen.biz.pojo.QuestionStatus;
import com.chen.biz.service.QuestionStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author danger
 * @date 2021/5/8
 */
@Service
@Slf4j
public class QuestionStatusServiceImpl extends BaseServiceImpl<QuestionStatus, QuestionStatusMapper> implements QuestionStatusService {

    @Autowired
    private QuestionStatusMapper questionStatusMapper;

    @Override
    public int updateStatus(Long questionId, Integer status) {
        int i;
        if (status.equals(0)) {
            i = questionStatusMapper.updateStatusSuccessByQuestionId(questionId);
        } else {
            i = questionStatusMapper.updateStatusFailureByQuestionId(questionId);
        }
        return i;
    }

    @Override
    public int removeStatusByQuestionId(Long questionId) {
        QuestionStatus filter = new QuestionStatus();
        filter.setQuestionId(questionId);
        QueryWrapper<QuestionStatus> wrapper = new QueryWrapper<>(filter);
        return questionStatusMapper.delete(wrapper);
    }

    @Override
    public int removeStatusByTypeId(Long typeId) {
        QuestionStatus filter = new QuestionStatus();
        filter.setQuestionTypeId(typeId);
        QueryWrapper<QuestionStatus> wrapper = new QueryWrapper<>(filter);
        return questionStatusMapper.delete(wrapper);
    }

    @Override
    public QuestionStatus getByOrder(Long questionOrder) {
        QuestionStatus filter = new QuestionStatus();
        filter.setQuestionOrder(questionOrder);
        QueryWrapper<QuestionStatus> wrapper = new QueryWrapper<>(filter);
        return questionStatusMapper.selectOne(wrapper);
    }
}
