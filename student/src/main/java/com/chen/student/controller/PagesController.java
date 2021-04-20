package com.chen.student.controller;

import com.chen.biz.pojo.InputExample;
import com.chen.biz.pojo.OutputExample;
import com.chen.biz.pojo.Question;
import com.chen.biz.service.InputExampleService;
import com.chen.biz.service.OutputExampleService;
import com.chen.biz.service.QuestionService;
import com.chen.biz.service.QuestionTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping({"/index", "/index.html"})
    public String index() {
        return "index";
    }

    @GetMapping({"/pages-login.html", "/pages-login"})
    public String toLogin() {
        return "pages-login";
    }

    @GetMapping("/pages-register")
    public String toRegister() {
        return "pages-register";
    }

    @GetMapping("/pages-recoverpw")
    public String toForgotPassword() {
        return "pages-recoverpw";
    }

    @GetMapping("/pages-profile")
    public String toPagesProfile() {
        return "pages-profile";
    }

    @GetMapping("/question-list")
    public String toQuestionList(@RequestParam("type") String type, Model model) {
        Long questionTypeId = questionTypeService.selectIdByTypeName(type);
        List<Question> questions= questionService.getQuestionByTypeId(questionTypeId);
        model.addAttribute("questionList", questions);
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
//
//    @GetMapping("/pages-register")
//    public String toRecoverPassword() {
//        return "pages-register";
//    }
}
