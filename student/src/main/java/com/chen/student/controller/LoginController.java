package com.chen.student.controller;

import com.chen.biz.pojo.SysUser;
import com.chen.biz.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author danger
 * @date 2021/3/16
 */
@Controller
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public String login() {
        System.out.println("=======post login=========");
        return "redirect:/index";
    }

    @PostMapping("/register")
    public String register(SysUser sysUser) throws Exception {
        System.out.println("=========post register=========");

        sysUserService.register(sysUser);
        return "redirect:/login";
    }
}
