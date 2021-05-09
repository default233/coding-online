package com.chen.biz.service;

import com.chen.biz.mapper.QuestionStatusMapper;
import com.chen.biz.pojo.QuestionStatus;

/**
 * @author danger
 * @date 2021/5/8
 */
public interface QuestionStatusService extends BaseService<QuestionStatus, QuestionStatusMapper>{
    int updateStatus(Long questionId, Integer status);
    int removeStatusByQuestionId(Long questionId);
    int removeStatusByTypeId(Long typeId);
}
