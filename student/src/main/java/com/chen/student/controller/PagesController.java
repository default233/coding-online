package com.chen.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author danger
 * @date 2021/3/16
 */
@Controller
public class PagesController {

    @RequestMapping({"/index", "/index.html"})
    public String index() {
        System.out.println("=========index=========");
        return "index";
    }

    @GetMapping({"/login.html", "/login"})
    public String toLogin() {
        System.out.println("=======get login======");
        return "login";
    }

    @GetMapping("/register")
    public String toRegister() {
        return "register";
    }

    @GetMapping("/forgot-password")
    public String toForgotPassword() {
        return "forgot-password";
    }

    @GetMapping("/recover-password")
    public String toRecoverPassword() {
        return "recover-password";
    }
}
