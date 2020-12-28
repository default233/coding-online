package com.chen.student.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 controller
 * author: JINCHENCHEN
 * date: 2020/12/28
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "Hello, coding-online";
    }
}
