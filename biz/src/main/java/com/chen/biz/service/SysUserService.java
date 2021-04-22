package com.chen.biz.service;

import com.chen.biz.exception.BadArgumentException;
import com.chen.biz.exception.CustomException;
import com.chen.biz.pojo.SysUser;

/**
 * SysUser Service 层
 * @author danger
 * @date 2021/3/9
 */
public interface SysUserService {
    /**
     * 根据用户 id 查询
     * @param id 用户 id
     * @return 用户
     */
    SysUser selectUserById(Long id);

    /**
     * 向数据库中插入用户
     * @param sysUser 用户
     * @return 影响行数
     */
    int insertUser(SysUser sysUser);

    /**
     * 根据用户 用户名 查询
     * @param username
     * @return
     */
    SysUser selectUserByName(String username);

    /**
     * 根据用户 邮箱 查询
     * @param email
     * @return
     */
    SysUser selectUserByEmail(String email);

    /**
     * 注册用户
     * @param sysUser 用户信息
     * @return 是否成功
     * @throws Exception 用户邮箱重复错误
     */
    int register(SysUser sysUser) throws CustomException;

    /**
     * 修改密码
     * @param sysUser 用户信息
     * @return 是否成功
     */
    int recoverPassword(SysUser sysUser, String newPassword) ;

    int updateUserNameById(Long id, String username);

    int updateEmailById(Long id, String email);

    String checkPassword(String password);

}
