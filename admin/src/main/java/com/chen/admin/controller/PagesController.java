package com.chen.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.pojo.*;
import com.chen.biz.service.*;
import com.chen.biz.vo.ClassInformation;
import com.chen.biz.vo.TypeInformation;
import com.chen.biz.vo.UserClassInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
    @Autowired
    private ClassService classService;
    @Autowired
    private UserClassService userClassService;
    @Autowired
    private UserPassService userPassService;
    @Autowired
    private SysUserService sysUserService;

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

    @GetMapping("/type-management")
    public String toTypeManagement(Model model) {
        List<TypeInformation> typeInformation = questionTypeService.getTypeInformation();
        model.addAttribute("typeInformation", typeInformation);
        return "management/type-management";
    }

    @GetMapping("/class-management")
    public String toClassManagement(Model model) {
        List<ClassInformation> classInformation = classService.getAllClass();
        model.addAttribute("classInformation", classInformation);
        return "management/class-management";
    }

    @GetMapping("/question-management")
    public String toQuestionManagement(Model model) {
        List<QuestionStatus> questionStatuses = questionStatusService.selectList(new QueryWrapper<>());
        model.addAttribute("questionList", questionStatuses);
        return "management/question-management";
    }
    @GetMapping("/user-management")
    public String toUserManagement(Model model) {
        List<UserClassInformation> userList = userClassService.getUserListByClassName(null);
        model.addAttribute("userList", userList);
        return "management/user-management";
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
        return "management/question-management";
    }


    @GetMapping("/user-list")
    public String toUserList(@RequestParam(value = "clazz", required = false) String clazz, Model model) {
        List<UserClassInformation> userList = userClassService.getUserListByClassName(clazz);
        model.addAttribute("userList", userList);
        return "management/user-management";
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

    @GetMapping("/question-detail")
    public String toQuestionDetail(@RequestParam("order") String order, Model model) {
        Long questionOrder = Long.parseLong(order);
        Question question = questionService.getQuestionByOrder(questionOrder);
        model.addAttribute("question", question);
        return "question/question-detail";
    }

    @GetMapping("/user-detail")
    public String toUserDetail(@RequestParam("userId") String userId, Model model) {

        UserPass filter = new UserPass();
        filter.setUserId(Long.parseLong(userId));
        QueryWrapper<UserPass> wrapper = new QueryWrapper<>(filter);
        List<UserPass> passes = userPassService.selectList(wrapper);
        List<Question> res = new ArrayList<>();
        for (UserPass pass : passes) {
            Question question = questionService.getById(pass.getQuestionId());
            res.add(question);
        }
        model.addAttribute("questions", res);
        return "user/user-detail";
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
