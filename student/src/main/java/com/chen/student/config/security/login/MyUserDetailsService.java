package com.chen.student.config.security.login;

import com.chen.biz.pojo.SysUser;
import com.chen.biz.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * UserDetailsService自定义接口子类，用于从数据库查询用户验证
 * @author danger
 * @date 2021/3/23
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectUserByEmail(email);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        ManageUser user = new ManageUser();
        user.setUsername(sysUser.getUsername());
        user.setPassword(sysUser.getPassword());
        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "role";
            }
        };
        user.setAuthorities(Set.of(authority));
        return user;
    }
}
