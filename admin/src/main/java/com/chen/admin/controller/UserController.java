package com.chen.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chen.admin.config.security.login.ManageUser;
import com.chen.biz.exception.CustomException;
import com.chen.biz.pojo.Class;
import com.chen.biz.pojo.SysUser;
import com.chen.biz.pojo.UserClass;
import com.chen.biz.pojo.UserInfo;
import com.chen.biz.service.ClassService;
import com.chen.biz.service.SysUserService;
import com.chen.biz.service.UserClassService;
import com.chen.biz.service.UserInfoService;
import com.chen.biz.vo.ClassInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author danger
 * @date 2021/4/21
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ClassService classService;
    @Autowired
    private UserClassService userClassService;
    @Value("${upload-path}")
    private String path;

    @RequestMapping("/username")
    @ResponseBody
    public void updateUsername(@RequestBody Map<String, Object> map) {
        Object username = map.get("name");
        Object value = map.get("value");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        SysUser currentUser = sysUserService.selectUserByName(currentUserName);
        userInfoService.updateUsername(currentUser, value.toString());
        ManageUser user = new ManageUser();
        user.setUsername(value.toString());
        user.setPassword(currentUser.getPassword());
        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "role";
            }
        };
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @RequestMapping("/sex")
    @ResponseBody
    public void updateSex(@RequestBody Map<String, Object> map) {
        Object sex = map.get("name");
        Object value = map.get("value");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        SysUser currentUser = sysUserService.selectUserByName(currentUserName);
        userInfoService.updateSex(currentUser, value.toString());
    }

    @RequestMapping("/email")
    @ResponseBody
    public void updateEmail(@RequestBody Map<String, Object> map) {
        Object email = map.get("name");
        Object value = map.get("value");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        SysUser currentUser = sysUserService.selectUserByName(currentUserName);
        userInfoService.updateEmail(currentUser, value.toString());
    }

    @RequestMapping("/birthday")
    @ResponseBody
    public void updateBirthday(@RequestBody Map<String, Object> map) {
        Object email = map.get("name");
        String value = map.get("value").toString();

        LocalDate time = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        SysUser currentUser = sysUserService.selectUserByName(currentUserName);
        userInfoService.updateBirthday(currentUser, time);
    }


    @GetMapping("/getCurrentUser")
    @ResponseBody
    public String getCurrentUser(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        SysUser sysUser = sysUserService.selectUserByName(currentUserName);
        Long userId = sysUser.getUserId();
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        request.setAttribute("currentUser", sysUser);
        return JSON.toJSONString(userInfo);
    }

    @PostMapping("/head-img")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file){
        log.info("come in");
        if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        log.info(fileName);
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        UUID uuid = UUID.randomUUID();
        File dest = new File(path + uuid +fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            SysUser sysUser = sysUserService.selectUserByName(currentUserName);
            userInfoService.updateImg(sysUser,uuid + fileName);

            return JSON.toJSONString("true");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return JSON.toJSONString("false");
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString("false");
        }
    }


    @PostMapping("/getClass")
    @ResponseBody
    public String getAllClass() {
        List<ClassInformation> allClass = classService.getAllClass();
        List<String> classNames = new ArrayList<>();
        for (ClassInformation aClass : allClass) {
            classNames.add(aClass.getClassName());
        }
        return JSON.toJSONString(classNames);
    }

    @PostMapping("/user-update")
    @ResponseBody
    public String authenticate(@RequestBody Map<String, String> map) {
        String oldName = map.get("oldName");
        if (!StringUtils.hasLength(oldName)) {
            throw new CustomException("学生姓名不能为空");
        }
        String studentName = map.get("studentName");
        if (!StringUtils.hasLength(studentName)) {
            throw new CustomException("学生学号不能为空");
        }
        String studentId = map.get("studentId");
        if (!StringUtils.hasLength(studentId)) {
            throw new CustomException("学生班级不能为空");
        }

        String studentClass = map.get("studentClass");
        if (!StringUtils.hasLength(studentClass)) {
            throw new CustomException("学生班级不能为空");
        }
        Class clazz = classService.getClassByName(studentClass);
        UserClass filter = new UserClass();
        filter.setRealName(oldName);
        UserClass userClass = new UserClass();
        userClass.setClassId(clazz.getClassId());
        userClass.setRealName(studentName);
        userClass.setStudentId(studentId);
        userClassService.updateByEntity(userClass, new UpdateWrapper<>(filter));
        return JSON.toJSONString(studentClass);
    }

    @RequestMapping("/user-delete")
    @ResponseBody
    @Transactional
    public String userDelete(@RequestParam("studentName") String studentName) {
        if (!StringUtils.hasLength(studentName)) {
            throw new CustomException("选择为空！");
        }
        userClassService.deleteUser(studentName);
        return JSON.toJSONString(200);
    }

    @GetMapping("/show-img")
    @ResponseBody
    public void getImage(@RequestParam("filename") String imageUrl, HttpServletResponse response) {
        if (imageUrl != null) {
            log.info("请求的图片URL：" + path + imageUrl);
            FileInputStream in = null;
            OutputStream out = null;
            try {
                File file = new File(path+imageUrl);
                if (!file.exists()) {
                    throw new CustomException("图片不存在");
                }
                in = new FileInputStream(path+imageUrl);
                int i = in.available();
                byte[] buffer = new byte[i];
                in.read(buffer);
                //设置输出流内容格式为图片格式
                response.setContentType("image/jpeg");
                //response的响应的编码方式为utf-8
                response.setCharacterEncoding("utf-8");
                out = response.getOutputStream();
                out.write(buffer);
            } catch (Exception e) {
                throw new CustomException("网络错误");
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    throw new CustomException("网络错误");
                }
            }
        }
    }

}
