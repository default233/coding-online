package com.chen.admin.controller;

import com.alibaba.fastjson.JSON;
import com.chen.admin.utils.compiler.CompileUtils;
import com.chen.biz.exception.BadArgumentException;
import com.chen.biz.exception.CustomException;
import com.chen.biz.pojo.*;
import com.chen.biz.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    private JudgeTaskService judgeTaskService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private QuestionTypeService questionTypeService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private InputExampleService inputExampleService;

    @Autowired
    private OutputExampleService outputExampleService;

    @Autowired
    private QuestionStatusService questionStatusService;

    @PostMapping("/code-submit")
    @ResponseBody
    public String codeJudge(@RequestBody Map<String, String> map) {
        String questionOrder = map.get("questionOrder");
        String source = map.get("source");
        String compilerId = map.get("compilerId");

        if (!StringUtils.hasLength(questionOrder)) {
            throw new BadArgumentException("问题id不能为空");
        }

        if (!StringUtils.hasLength(source)) {
            throw new BadArgumentException("代码不能为空");
        }

        if (!StringUtils.hasLength(compilerId)) {
            throw new BadArgumentException("请选择代码语言");
        }

        Question question = questionService.getQuestionByOrder(Long.parseLong(questionOrder));

        JudgeTask judgeTask = new JudgeTask();
        judgeTask.setQuestionId(question.getQuestionId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        SysUser sysUser = sysUserService.selectUserByName(currentUserName);
        Long userId = sysUser.getUserId();
        judgeTask.setUserId(userId);
        judgeTask.setCompilerId(Integer.parseInt(compilerId));
        judgeTask.setMemoryLimit(question.getMemoryLimit());
        judgeTask.setTimeLimit(question.getTimeLimit());
        judgeTask.setSource(source);

        int insert = judgeTaskService.insert(judgeTask);

//        compileUtils.testInvoke(className, codeSource);
////        judgeTask.
        JudgeResult judgeResult = judgeService.judge(judgeTask);
        log.info(judgeResult.toString());
        return JSON.toJSONString(judgeResult);
//        return "";
    }


    @PostMapping("/type-put")
    @ResponseBody
    public String typePut(@RequestBody Map<String, String> map) {
        String questionType = map.get("questionType");
        if (!StringUtils.hasLength(questionType)) {
            throw new CustomException("课程名称不能为空");
        }
        Long id = questionTypeService.selectIdByTypeName(questionType.trim());
        if (id != null) {
            throw new CustomException("课程名称已存在");
        }
        QuestionType type = new QuestionType();
        type.setQuestionType(questionType.trim());
        int insert = questionTypeService.insert(type);
        return JSON.toJSONString(insert);
    }



    @PostMapping("/question-type")
    @ResponseBody
    public String getQuestionType() {
        List<String> allType = questionTypeService.getAllType();
        return JSON.toJSONString(allType);
    }

//    @PostMapping("/type-information")
//    @ResponseBody
//    public String getTypeInformation(Model model) {
//        List<TypeInformation> typeInformation = questionTypeService.getTypeInformation();
//        model.addAttribute("typeInformation", typeInformation);
//        return JSON.toJSONString(typeInformation);
//    }


    @RequestMapping("/type-edit")
    @ResponseBody
    @Transactional
    public String typeEdit(@RequestParam("oldType") String oldType, @RequestParam("newType") String newType) {

        if (!StringUtils.hasLength(newType)) {
            throw new CustomException("课程名称不能为空！");
        }
        Long id = questionTypeService.selectIdByTypeName(newType.trim());
        if (id != null) {
            throw new CustomException("课程名称已存在");
        } else {
            questionTypeService.updateQuestionTypeByName(oldType, newType);
        }
        return JSON.toJSONString(200);
    }


    @RequestMapping("/type-delete")
    @ResponseBody
    @Transactional
    public String typeDelete(@RequestParam("type") String type) {

        if (!StringUtils.hasLength(type)) {
            throw new CustomException("选择为空！");
        }
        Long id = questionTypeService.selectIdByTypeName(type.trim());
        if (id == null) {
            throw new CustomException("课程名称不存在");
        } else {
            questionTypeService.remove(id);
            questionService.removeQuestionByTypeId(id);
            questionStatusService.removeStatusByTypeId(id);
        }
        return JSON.toJSONString(200);
    }

    @PostMapping("/question-delete/{questionOrder}")
    @ResponseBody
    public String questionDelete(@PathVariable("questionOrder") String questionOrder) {
        if (!StringUtils.hasLength(questionOrder)) {
            throw new BadArgumentException("题目不能为空！");
        }
        Question question = questionService.getQuestionByOrder(Long.parseLong(questionOrder));
        if (question == null) {
            throw new BadArgumentException("题目不存在！");
        }
        Long questionId = question.getQuestionId();
        int remove = questionService.removeQuestionById(questionId);
        return JSON.toJSONString(remove);
    }

    @PostMapping("/question-submit")
    @ResponseBody
    @Transactional
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

        // 插入题目表
        Question question = new Question();
        Long typeId = questionTypeService.selectIdByTypeName(type);
        question.setTitle(title);
        question.setQuestionDifficulty(difficulty);
        question.setQuestionDescription(description);
        question.setInputDescription(inputDescription);
        question.setOutputDescription(outputDescription);
        question.setTimeLimit(timeLimit);
        question.setMemoryLimit(memoryLimit);
        Long maxOrder = questionService.getMaxOrder();
        question.setQuestionOrder(maxOrder+1);
        question.setQuestionTypeId(typeId);

        questionService.insertQuestion(question);
        Long questionId = question.getQuestionId();
        if (questionId == null) {
            throw new BadArgumentException("Id不存在");
        }
        // 插入题目输入输出
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


        // 创建题目状态记录
        QuestionStatus questionStatus = new QuestionStatus();
        questionStatus.setQuestionId(questionId);
        questionStatus.setQuestionSubmit(0);
        questionStatus.setQuestionSuccess(0);
        questionStatus.setQuestionOrder(question.getQuestionOrder());
        questionStatus.setQuestionDifficulty(question.getQuestionDifficulty());
        questionStatus.setTitle(question.getTitle());
        questionStatus.setQuestionTypeId(question.getQuestionTypeId());
        questionStatusService.insert(questionStatus);
        return JSON.toJSONString(question);
    }

}
