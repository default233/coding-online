package com.chen.admin.controller;

import com.alibaba.fastjson.JSON;
import com.chen.biz.exception.BadArgumentException;
import com.chen.biz.exception.UserNotExistException;
import com.chen.biz.pojo.SysUser;
import com.chen.biz.service.SysUserService;
import com.chen.admin.utils.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author danger
 * @date 2021/3/16
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private IMailService iMailService;

    public static final String SUBJECT = "修改密码";


    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody SysUser sysUser) throws Exception {
        sysUserService.register(sysUser, 1);
        String user = JSON.toJSONString(sysUser);
        return user;
    }



    @PostMapping("/recover-password")
    @ResponseBody
    public String recoverPassword(@RequestBody Map map) throws Exception {
        Object newPasswordObj = map.get("newPassword");
        Object sysUserObj = map.get("sysUser");
        if (newPasswordObj == null) {
            throw new BadArgumentException("新密码不能为空！");
        }
        if (sysUserObj == null) {
            throw new BadArgumentException("当前用户不存在！");
        }
        String newPassword = newPasswordObj.toString();
        SysUser sysUser = JSON.parseObject(sysUserObj.toString(), SysUser.class);
        System.out.println(sysUser);
        int res = sysUserService.recoverPassword(sysUser, newPassword);
//        sysUserService.register(sysUser);
//        String user = JSON.toJSONString(sysUser);
//        return user;
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("res", res);
        String resJson = JSON.toJSONString(result);
        return resJson;
    }

    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(@RequestBody Map map) throws Exception {
        Object emailObj = map.get("email");
        if (emailObj == null) {
            throw new UserNotExistException("邮箱为空！");
        }
        String email = emailObj.toString();
        SysUser sysUser = sysUserService.selectUserByEmail(email);
        if (sysUser == null) {
            throw new UserNotExistException("该邮箱尚未注册！");
        }
        String verificationCode = iMailService.createVerificationCode();
        iMailService.sendSimpleMail(email, SUBJECT, verificationCode);
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("verificationCode", verificationCode);
        res.put("sysUser", sysUser);
        return JSON.toJSONString(res);
    }

    @PostMapping("/checkPassword")
    @ResponseBody
    public String checkPassword(@RequestBody Map map) throws Exception {

        Object passwordObj = map.get("password");
        if (passwordObj == null) {
            throw new UserNotExistException("密码不能为空！");
        }
        String password = passwordObj.toString();
        System.out.println("password = " + password);
        sysUserService.checkPassword(password);
        return "true";
    }

}
