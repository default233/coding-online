package com.chen.student.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.exception.CustomException;
import com.chen.biz.pojo.Class;
import com.chen.biz.pojo.SysUser;
import com.chen.biz.pojo.UserClass;
import com.chen.biz.service.ClassService;
import com.chen.biz.service.SysUserService;
import com.chen.biz.service.UserClassService;
import com.chen.biz.vo.ClassInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author danger
 * @date 2021/5/13
 */
@Controller
@Slf4j
public class ClassController {
    @Autowired
    private ClassService classService;
    @Autowired
    private UserClassService userClassService;
    @Autowired
    private SysUserService sysUserService;


    @PostMapping("/getClasses")
    @ResponseBody
    public String getClasses() {
        List<ClassInformation> classInformation = classService.getAllClass();
        return JSON.toJSONString(classInformation);
    }

    @PostMapping("/authenticate")
    @ResponseBody
    public String authenticate(@RequestBody Map<String, String> map) {
        String studentName = map.get("studentName");
        if (!StringUtils.hasLength(studentName)) {
            throw new CustomException("学生姓名不能为空");
        }
        String studentId = map.get("studentId");
        if (!StringUtils.hasLength(studentId)) {
            throw new CustomException("学生学号不能为空");
        }
        String studentClass = map.get("studentClass");
        if (!StringUtils.hasLength(studentClass)) {
            throw new CustomException("学生班级不能为空");
        }
        Class clazz = classService.getClassByName(studentClass);
        UserClass userClass = new UserClass();
        userClass.setClassId(clazz.getClassId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        SysUser currentUser = sysUserService.selectUserByName(currentUserName);

        userClass.setUserId(currentUser.getUserId());
        userClass.setRealName(studentName);
        userClass.setStudentId(studentId);
        userClassService.authenticate(userClass);
        return JSON.toJSONString(studentClass);
    }
}
