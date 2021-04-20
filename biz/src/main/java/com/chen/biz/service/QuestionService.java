package com.chen.biz.service;

import com.chen.biz.pojo.Question;

import java.util.List;

/**
 * @author danger
 * @date 2021/4/16
 */
public interface QuestionService {

    int insertQuestion(Question question);

    Long getQuestionId(Question question);

    List<Question> getQuestionByTypeId(Long id);

    Question getQuestionByOrder(Long order);
}
