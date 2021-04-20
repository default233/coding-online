package com.chen.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.mapper.QuestionMapper;
import com.chen.biz.pojo.Question;
import com.chen.biz.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author danger
 * @date 2021/4/16
 */
@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public int insertQuestion(Question question) {
        return questionMapper.insert(question);
    }

    @Override
    public Long getQuestionId(Question question) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>(question);
        return questionMapper.selectOne(wrapper).getQuestionId();
    }

    @Override
    public List<Question> getQuestionByTypeId(Long id) {
        Question filter = new Question();
        filter.setQuestionTypeId(id);
        QueryWrapper<Question> wrapper = new QueryWrapper<>(filter);
        List<Question> questions = questionMapper.selectList(wrapper);
        return questions;
    }

    @Override
    public Question getQuestionByOrder(Long order) {
        Question filter = new Question();
        filter.setQuestionOrder(order);
        QueryWrapper<Question> wrapper = new QueryWrapper<>(filter);
        Question question = questionMapper.selectOne(wrapper);
        return question;
    }
}
