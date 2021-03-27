package com.chen.student.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author danger
 * @date 2021/3/16
 */
@Controller
@Slf4j
public class PagesController {

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
//
//    @GetMapping("/pages-register")
//    public String toRecoverPassword() {
//        return "pages-register";
//    }
}
