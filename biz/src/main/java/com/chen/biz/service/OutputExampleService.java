package com.chen.biz.service;

import com.chen.biz.pojo.OutputExample;

import java.util.List;

/**
 * @author danger
 * @date 2021/4/17
 */
public interface OutputExampleService {
    int insertOutputExample(OutputExample outputExample);

    int insertOutputExampleList(List<OutputExample> outputExamples);

    List<OutputExample> getOutputExampleById(Long questionId);
}
