package com.chen.biz.service;

import com.chen.biz.pojo.InputExample;

import java.util.List;

/**
 * @author danger
 * @date 2021/4/17
 */
public interface InputExampleService {
    int insertInputExample(InputExample inputExample);
    int insertInputExampleList(List<InputExample> inputExamples);
    List<InputExample> getInputExampleById(Long questionId);
}
