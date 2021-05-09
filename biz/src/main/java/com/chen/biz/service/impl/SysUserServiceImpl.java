package com.chen.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chen.biz.exception.BadArgumentException;
import com.chen.biz.exception.CustomException;
import com.chen.biz.exception.UserAlreadyExistException;
import com.chen.biz.mapper.SysUserMapper;
import com.chen.biz.pojo.SysUser;
import com.chen.biz.pojo.UserInfo;
import com.chen.biz.service.SysUserService;
import com.chen.biz.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * SysUser Service 实现类层
 * @author danger
 * @date 2021/3/9
 */
@Service
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserMapper> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Value("${default_img_path}")
    private String defaultImgPath;
    /**
     * 根据用户 id 查询
     * @param id 用户 id
     * @return 用户
     */
    @Override
    public SysUser selectUserById(Long id) {
        return sysUserMapper.selectById(id);
    }

    /**
     * 向数据库中插入用户
     * @param sysUser 用户
     * @return 影响行数
     */
    @Override
    public int insertUser(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }

    /**
     * 根据用户 用户名 查询
     * @param username
     * @return
     */
    @Override
    public SysUser selectUserByName(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户 邮箱 查询
     * @param email
     * @return
     */
    @Override
    public SysUser selectUserByEmail(String email) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 注册用户
     * @param sysUser 用户信息
     * @return 是否成功
     * @throws Exception 用户重复错误
     */
    @Override
    @Transactional
    public int register(SysUser sysUser, Integer userType) throws CustomException {

        if (sysUser == null) {
            throw new BadArgumentException("用户为空！！");
        }

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", sysUser.getEmail());
        SysUser user = sysUserMapper.selectOne(queryWrapper);
        if (user != null) {
            throw new UserAlreadyExistException("用户已存在");
        }
        String password = sysUser.getPassword();
        // 密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(password);
        sysUser.setPassword(encode);
        sysUser.setUserType(userType);
        // 向用户表中插入数据
        int insert = sysUserMapper.insert(sysUser);

        // 向用户信息表中插入数据
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(sysUser.getUserId());
        userInfo.setUsername(sysUser.getUsername());
        userInfo.setSex("男");
        userInfo.setEmail(sysUser.getEmail());
        userInfo.setBirthday(LocalDate.now());
        userInfo.setImg(defaultImgPath);
        userInfo.setProblemSubmit(0);
        userInfo.setProblemSuccess(0);
        userInfo.setUserType(userType);
        userInfoService.insertUserInfo(userInfo);
        return insert;
    }

    /**
     * 修改密码
     * @param sysUser 用户信息
     * @param newPassword 新密码
     * @return 是否成功
     */
    @Override
    public int recoverPassword(SysUser sysUser, String newPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(newPassword);
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", sysUser.getUsername());
        sysUser.setPassword(encode);
        int update = sysUserMapper.update(sysUser, updateWrapper);
        return update;
    }

    @Override
    public int updateUserNameById(Long id, String username) {
        return sysUserMapper.updateUserName(id, username);
    }

    @Override
    public int updateEmailById(Long id, String email) {
        return sysUserMapper.updateEmail(id, email);
    }

    @Override
    public String checkPassword(String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        SysUser sysUser = this.selectUserByName(currentUserName);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (bCryptPasswordEncoder.matches(password, sysUser.getPassword()))
            return "true";
        throw new CustomException("密码不正确！");
    }

}
