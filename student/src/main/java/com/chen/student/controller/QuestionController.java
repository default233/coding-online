package com.chen.student.controller;

import com.alibaba.fastjson.JSON;
import com.chen.biz.exception.BadArgumentException;
import com.chen.biz.pojo.*;
import com.chen.biz.service.*;
import com.chen.student.utils.QuestionUtils;
import com.chen.student.utils.compiler.CompileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author danger
 * @date 2021/4/7
 */
@Controller
@Slf4j
public class QuestionController {

    @Autowired
    private CompileUtils compileUtils;

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private QuestionTypeService questionTypeService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private InputExampleService inputExampleService;

    @Autowired
    private OutputExampleService outputExampleService;

    @PostMapping("/code-submit")
    @ResponseBody
    public String codeJudge(@RequestBody Map<String, String> map) {
        String className = map.get("className");
        String codeSource = map.get("codeSource");

        if (!StringUtils.hasLength(className)) {
            throw new BadArgumentException("类名不能为空");
        }

        if (!StringUtils.hasLength(codeSource)) {
            throw new BadArgumentException("代码不能为空");
        }

//        compileUtils.testInvoke(className, codeSource);
        JudgeTask judgeTask = new JudgeTask();
//        judgeTask.
        JudgeResult judgeResult = judgeService.judge(judgeTask);
        return JSON.toJSONString(judgeResult);
    }


    @PostMapping("/question-type")
    @ResponseBody
    public String getQuestionType() {
        List<String> allType = questionTypeService.getAllType();
        return JSON.toJSONString(allType);
    }


    @PostMapping("/question-submit")
    @ResponseBody
    public String questionSubmit(@RequestBody Map<String, Object> map) {
        String title = (String) map.get("title");
        if (!StringUtils.hasLength(title)) {
            throw new BadArgumentException("题目标题不能为空！");
        }
        String difficulty = (String) map.get("difficulty");
        if (!StringUtils.hasLength(difficulty)) {
            throw new BadArgumentException("题目难度不能为空！");
        }

        String type = (String) map.get("type");
        if (!StringUtils.hasLength(type)) {
            throw new BadArgumentException("题目种类不能为空！");
        }

        String description = (String) map.get("description");
        if (!StringUtils.hasLength(description)) {
            throw new BadArgumentException("题目描述不能为空！");
        }

        String inputDescription = (String) map.get("input");
        if (!StringUtils.hasLength(inputDescription)) {
            throw new BadArgumentException("题目输入参数描述不能为空！");
        }

        String outputDescription = (String) map.get("output");
        if (!StringUtils.hasLength(outputDescription)) {
            throw new BadArgumentException("题目输出参数描述不能为空！");
        }

        Integer timeLimit = Integer.parseInt((String) map.get("time"));
        if (null == timeLimit) {
            throw new BadArgumentException("题目时间限制不能为空！");
        }

        Integer memoryLimit = Integer.parseInt((String) map.get("memory"));
        if (null == memoryLimit) {
            throw new BadArgumentException("题目内存限制不能为空！");
        }

        List<String> inputExample = (List<String>) map.get("inputExample");
        if (inputExample.isEmpty()) {
            throw new BadArgumentException("输入示例不能为空");
        }

        List<String> outputExample = (List<String>) map.get("outputExample");
        if (outputExample.isEmpty()) {
            throw new BadArgumentException("输入示例不能为空");
        }
        Question question = new Question();
        Long typeId = questionTypeService.selectIdByTypeName(type);
        question.setTitle(title);
        question.setQuestionDifficulty(difficulty);
        question.setQuestionDescription(description);
        question.setInputDescription(inputDescription);
        question.setOutputDescription(outputDescription);
        question.setTimeLimit(timeLimit);
        question.setMemoryLimit(memoryLimit);
        question.setQuestionOrder(QuestionUtils.questionOrder++);
        question.setQuestionTypeId(typeId);

        questionService.insertQuestion(question);
        Long questionId = question.getQuestionId();
        if (questionId == null) {
            throw new BadArgumentException("Id么得");
        }
        List<InputExample> inputExampleList = new ArrayList<>();
        for (String input : inputExample) {
            InputExample example = new InputExample();
            example.setInputExample(input);
            example.setQuestionId(questionId);
            inputExampleList.add(example);
        }

        inputExampleService.insertInputExampleList(inputExampleList);

        List<OutputExample> outputExampleList = new ArrayList<>();
        for (String output : outputExample) {
            OutputExample example = new OutputExample();
            example.setOutputExample(output);
            example.setQuestionId(questionId);
            outputExampleList.add(example);
        }
        outputExampleService.insertOutputExampleList(outputExampleList);
        return "";
    }

}
