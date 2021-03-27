package com.chen.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chen.biz.exception.BadArgumentException;
import com.chen.biz.exception.CustomException;
import com.chen.biz.exception.UserAlreadyExistException;
import com.chen.biz.mapper.SysUserMapper;
import com.chen.biz.pojo.SysUser;
import com.chen.biz.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * SysUser Service 实现类层
 * @author danger
 * @date 2021/3/9
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

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
     * @throws Exception 用户邮箱重复错误
     */
    @Override
    public int register(SysUser sysUser) throws CustomException {

        if (sysUser == null) {
            throw new BadArgumentException("用户为空！！");
        }

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", sysUser.getUsername());
        queryWrapper.eq("email", sysUser.getEmail());
        SysUser user = sysUserMapper.selectOne(queryWrapper);
        if (user != null) {
            throw new UserAlreadyExistException("用户已存在");
        }
        String password = sysUser.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(password);
        log.info(encode);
        sysUser.setPassword(encode);
        int insert = sysUserMapper.insert(sysUser);
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
}
