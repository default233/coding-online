package com.chen.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.exception.CustomException;
import com.chen.biz.pojo.Class;
import com.chen.biz.pojo.QuestionType;
import com.chen.biz.service.ClassService;
import com.chen.biz.service.UserClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/class-put")
    @ResponseBody
    public String classPut(@RequestBody Map<String, String> map) {
        String className = map.get("className");
        if (!StringUtils.hasLength(className)) {
            throw new CustomException("班级名称不能为空");
        }
        Class filter = new Class();
        filter.setClassName(className);
        QueryWrapper<Class> wrapper = new QueryWrapper<>(filter);
        Class one = classService.selectOne(wrapper);
        if (one != null) {
            throw new CustomException("班级名称已存在");
        }
        int insert = classService.insert(filter);
        return JSON.toJSONString(insert);
    }

    @RequestMapping("/class-edit")
    @ResponseBody
    @Transactional
    public String classEdit(@RequestParam("oldClass") String oldClass, @RequestParam("newClass") String newClass) {

        if (!StringUtils.hasLength(newClass)) {
            throw new CustomException("班级名称不能为空！");
        }
        Class classByName = classService.getClassByName(newClass);
        if (classByName != null) {
            throw new CustomException("课程名称已存在");
        } else {
            classService.updateClassByName(oldClass, newClass);
        }
        return JSON.toJSONString(200);
    }

    @RequestMapping("/class-delete")
    @ResponseBody
    @Transactional
    public String classDelete(@RequestParam("class") String clazz) {
        if (!StringUtils.hasLength(clazz)) {
            throw new CustomException("选择为空！");
        }
        userClassService.deleteClass(clazz);
        return JSON.toJSONString(200);
    }
}
