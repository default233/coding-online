package com.chen.biz.service;

import com.chen.biz.mapper.QuestionMapper;
import com.chen.biz.pojo.Question;

import java.util.List;

/**
 * @author danger
 * @date 2021/4/16
 */
public interface QuestionService extends BaseService<Question, QuestionMapper> {

    int insertQuestion(Question question);

    Long getQuestionId(Question question);

    List<Question> getQuestionByTypeId(Long id);

    Question getQuestionByOrder(Long order);

    int removeQuestionById(Long id);

    Long getMaxOrder();

    int removeQuestionByTypeId(Long typeId);
}
