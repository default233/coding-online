package com.chen.admin.controller;

import com.alibaba.fastjson.JSON;
import com.chen.biz.pojo.Question;
import com.chen.biz.pojo.QuestionStatus;
import com.chen.biz.service.ClassService;
import com.chen.biz.service.JudgeResultService;
import com.chen.biz.service.QuestionService;
import com.chen.biz.service.QuestionStatusService;
import com.chen.biz.vo.ClassInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author danger
 * @date 2021/5/14
 */
@RestController
@Slf4j
public class ChartController {

    @Autowired
    private QuestionStatusService questionStatusService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private JudgeResultService judgeResultService;
    @Autowired
    private ClassService classService;

    @PostMapping("/question-chart-pie")
    public String questionChartPie(@RequestParam("order") String order) {

        Long questionOrder = Long.parseLong(order);
        QuestionStatus questionStatus = questionStatusService.getByOrder(questionOrder);
        Integer questionSubmit = questionStatus.getQuestionSubmit();
        Integer questionSuccess = questionStatus.getQuestionSuccess();
        int[] arr = new int[]{questionSuccess, questionSubmit-questionSuccess};
        return JSON.toJSONString(arr);
    }

    @PostMapping("/question-chart-radar")
    public String questionChartRadar(@RequestParam("order") String order) {
        Long questionOrder = Long.parseLong(order);
        Question question = questionService.getQuestionByOrder(questionOrder);
        Integer subTimes = judgeResultService.getSubTimesByQuestionId(question.getQuestionId());
        List<Integer> errorArr = judgeResultService.getErrorTypeByQuestionId(question.getQuestionId());
        errorArr.remove(0);
        errorArr.remove(1);
        errorArr.remove(5);
        List<BigDecimal> doubles = new ArrayList<>();
        for (Integer integer : errorArr) {
            double v = (double) (subTimes - integer) / subTimes;
            BigDecimal right = BigDecimal.valueOf(v);
            right.setScale(2, BigDecimal.ROUND_HALF_UP);
            doubles.add(right);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("errorArr", errorArr);
        map.put("doubles", doubles);
        return JSON.toJSONString(map);
    }

    @PostMapping("/question-chart-bar")
    public String questionChartBar(@RequestParam("order") String order) {
        Long questionOrder = Long.parseLong(order);
        List<ClassInformation> allClass = classService.getAllClass();
        HashMap<String, Object> result = new HashMap<>();
        result.put("allClass", allClass);
        return JSON.toJSONString(result);
    }
}
