package com.chen.student.controller;

import com.alibaba.fastjson.JSON;
import com.chen.biz.exception.BadArgumentException;
import com.chen.biz.pojo.SysUser;
import com.chen.student.utils.compiler.CompileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping("/code-submit")
    @ResponseBody
    public String register(@RequestBody Map<String, String> map) throws Exception {
        String className = map.get("className");
        String codeSource = map.get("codeSource");

        if (!StringUtils.hasLength(className)) {
            throw new BadArgumentException("类名不能为空");
        }

        if (!StringUtils.hasLength(codeSource)) {
            throw new BadArgumentException("代码不能为空");
        }

        compileUtils.testInvoke(className, codeSource);
        return " ";
    }

}
