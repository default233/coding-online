package com.chen.admin.controller;

import com.alibaba.fastjson.JSON;
import com.chen.biz.pojo.Question;
import com.chen.biz.pojo.QuestionStatus;
import com.chen.biz.pojo.UserInfo;
import com.chen.biz.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @date 2021/5/15
 */
@RestController
@Slf4j
public class UserStatisticsController {

    @Autowired
    private QuestionStatusService questionStatusService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private JudgeResultService judgeResultService;
    @Autowired
    private ClassService classService;
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/user-chart-pie")
    public String userChartPie(@RequestParam("userId") String userId) {
        Long userIdL = Long.parseLong(userId);
        UserInfo info = userInfoService.getUserInfoByUserId(userIdL);
        Integer problemSubmit = info.getProblemSubmit();
        Integer problemSuccess = info.getProblemSuccess();
        int[] arr = new int[]{problemSuccess, problemSubmit-problemSuccess};
        return JSON.toJSONString(arr);
    }

    @PostMapping("/user-chart-radar")
    public String userChartRadar(@RequestParam("userId") String userId) {
        Long userIdL = Long.parseLong(userId);
        List<Integer> errorArr = judgeResultService.getErrorTypeByUserId(userIdL);
        Integer subTimes = judgeResultService.getSubTimesByUserId(userIdL);
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

}
