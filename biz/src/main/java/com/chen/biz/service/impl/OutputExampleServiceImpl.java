package com.chen.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.mapper.OutputExampleMapper;
import com.chen.biz.pojo.InputExample;
import com.chen.biz.pojo.OutputExample;
import com.chen.biz.service.OutputExampleService;
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
public class OutputExampleServiceImpl implements OutputExampleService {
    @Autowired
    private OutputExampleMapper outputExampleMapper;

    @Override
    public int insertOutputExample(OutputExample outputExample) {
        return outputExampleMapper.insert(outputExample);
    }

    @Override
    public int insertOutputExampleList(List<OutputExample> outputExamples) {
        int sum = 0;
        for (OutputExample outputExample : outputExamples) {
            sum += outputExampleMapper.insert(outputExample);
        }
        return sum;
    }

    @Override
    public List<OutputExample> getOutputExampleById(Long questionId) {
        OutputExample filter = new OutputExample();
        filter.setQuestionId(questionId);
        QueryWrapper<OutputExample> wrapper = new QueryWrapper<>(filter);
        return outputExampleMapper.selectList(wrapper);
    }
}
