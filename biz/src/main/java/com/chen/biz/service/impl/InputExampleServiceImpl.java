package com.chen.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.mapper.InputExampleMapper;
import com.chen.biz.pojo.InputExample;
import com.chen.biz.service.InputExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author danger
 * @date 2021/4/17
 */
@Service
@Slf4j
public class InputExampleServiceImpl extends BaseServiceImpl<InputExample, InputExampleMapper> implements InputExampleService {
    @Autowired
    private InputExampleMapper inputExampleMapper;


    @Override
    public int insertInputExample(InputExample inputExample) {
        return inputExampleMapper.insert(inputExample);
    }

    @Override
    public int insertInputExampleList(List<InputExample> inputExamples) {
        int sum = 0;
        for (InputExample inputExample : inputExamples) {
            sum += inputExampleMapper.insert(inputExample);
        }
        return sum;
    }

    @Override
    public List<InputExample> getInputExampleById(Long questionId) {
        InputExample filter = new InputExample();
        filter.setQuestionId(questionId);
        QueryWrapper<InputExample> wrapper = new QueryWrapper<>(filter);
        return inputExampleMapper.selectList(wrapper);
    }

    @Override
    public int removeByQuestionId(Long questionId) {
        InputExample filter = new InputExample();
        filter.setQuestionId(questionId);
        QueryWrapper<InputExample> wrapper = new QueryWrapper<>(filter);
        return inputExampleMapper.delete(wrapper);
    }
}
