package com.chen.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.pojo.*;
import com.chen.biz.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author danger
 * @date 2021/3/16
 */
@Controller
@Slf4j
public class PagesController {

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
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping({"/index", "/index.html"})
    public String index() {
        return "index";
    }

    @GetMapping({"/pages-login.html", "/pages-login"})
    public String toLogin() {
        return "user/pages-login";
    }

    @GetMapping("/pages-register")
    public String toRegister() {
        return "user/pages-register";
    }

    @GetMapping("/pages-recoverpw")
    public String toForgotPassword() {
        return "user/pages-recoverpw";
    }

    @GetMapping("/pages-profile")
    public String toPagesProfile() {
        return "user/pages-profile";
    }

    @GetMapping("/question-list")
    public String toQuestionList(@RequestParam(value = "type", required = false) String type, Model model) {
        QuestionStatus wrapper = new QuestionStatus();
        Long questionTypeId = null;
        if (StringUtils.hasLength(type)) {
            questionTypeId = questionTypeService.selectIdByTypeName(type);
            wrapper.setQuestionTypeId(questionTypeId);
        }

        List<QuestionStatus> questionStatuses = questionStatusService.selectList(new QueryWrapper<>(wrapper));
        model.addAttribute("questionList", questionStatuses);
        return "question/question-list";
    }

    @GetMapping("/question-put")
    public String toQuestionPut() {
        return "question/question-put";
    }

    @GetMapping("/question/{order}")
    public String toQuestionPage(@PathVariable("order") String order, Model model) {
        Long questionOrder = Long.parseLong(order);
        Question question = questionService.getQuestionByOrder(questionOrder);
        List<InputExample> inputExamples = inputExampleService.getInputExampleById(question.getQuestionId());
        List<OutputExample> outputExamples = outputExampleService.getOutputExampleById(question.getQuestionId());
        model.addAttribute("inputExamples", inputExamples);
        model.addAttribute("outputExamples", outputExamples);
        model.addAttribute("question", question);
        return "question/question";
    }

    @GetMapping("/help")
    public String toHelp() {
        return "pages/help";
    }

    @GetMapping("/ranking")
    public String toRanking(Model model) {

        List<UserRanking> ranks = userInfoService.getUserRankingByPassRate();
        model.addAttribute("ranks", ranks);
        return "pages/ranking";
    }

    @GetMapping("/pages-lock")
    public String toLock() {
        return "user/pages-lock-screen";
    }

}
