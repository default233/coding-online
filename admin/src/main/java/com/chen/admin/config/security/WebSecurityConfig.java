package com.chen.admin.config.security;

import com.alibaba.fastjson.JSON;
import com.chen.admin.config.security.login.CustomAuthenticationFilter;
import com.chen.admin.config.security.login.MyPersistentTokenBasedRememberMeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * spring security 配置类
 * @author danger
 * @date 2021/3/16
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("myUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    /**
     * token 存储逻辑，利用 Jdbc 将 token 存入数据库
     * @return PersistentTokenRepository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 生成数据库表，第一次运行后注释
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 自定义登录页面
         */
        http.formLogin().loginPage("/pages-login.html")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .successForwardUrl("/index.html");
//                .failureForwardUrl("/error");

        http.logout().addLogoutHandler(rememberMeServices())
                .logoutSuccessUrl("/pages-login")
                .deleteCookies("JSESSIONID")
                .deleteCookies("remember-me");

        /**
         * 自动登录设置
         */
//        http.rememberMe()
//                .tokenRepository(persistentTokenRepository())
//                .userDetailsService(userDetailsService)
//                .rememberMeParameter("remember-me")
//                .tokenValiditySeconds(60*60*24*7);
//        http.rememberMe().rememberMeCookieName("remember-me");

        /**
         * 静态资源文件放行
         */
        http.authorizeRequests()
                .antMatchers("/pages-login.html", "/login", "/assets/**", "/plugins/**",
                        "/pages-register", "/pages-register","/register",
                        "/pages-recoverpw", "/logout", "/recover-password",
                        "/sendEmail")
                .permitAll()
                .anyRequest().authenticated();

        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(rememberMeAuthenticationFilter(), RememberMeAuthenticationFilter.class);
        /**
         * 关闭 csrf
         */
        http.csrf().disable();
    }

    @Override
    @Bean // share AuthenticationManager for web and oauth
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter writer = httpServletResponse.getWriter();
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("message", "登录成功");
                writer.write(JSON.toJSONString(map));
                writer.flush();
                writer.close();
            }
        });
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.setStatus(500);
                PrintWriter writer = httpServletResponse.getWriter();
                Map<String, Object> map = new LinkedHashMap<>();
                if ("Bad credentials".equals(e.getMessage())) {
                    map.put("message", "用户名或密码不正确");
                }
                else {
                    map.put("message", e.getMessage());
                }
                writer.write(JSON.toJSONString(map));
                writer.flush();
                writer.close();
            }
        });
        filter.setFilterProcessesUrl("/login");
        filter.setRememberMeServices(rememberMeServices());
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        RememberMeAuthenticationProvider rememberMeAuthenticationProvider = new RememberMeAuthenticationProvider("INTERNAL_SECRET_KEY");
        return rememberMeAuthenticationProvider;
    }

    @Bean
    public RememberMeAuthenticationFilter rememberMeAuthenticationFilter() throws Exception {
        //重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager

        return new RememberMeAuthenticationFilter(authenticationManager(), rememberMeServices());
    }

    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeServices() {
        PersistentTokenBasedRememberMeServices rememberMeServices = new MyPersistentTokenBasedRememberMeServices("INTERNAL_SECRET_KEY", userDetailsService, persistentTokenRepository());
        rememberMeServices.setCookieName("remember-me");
        rememberMeServices.setParameter("remember-me"); // 修改默认参数remember-me为rememberMe和前端请求中的key要一致
        rememberMeServices.setTokenValiditySeconds(3600 * 24 * 7); //token有效期7天
        return rememberMeServices;
    }



    /**
     * 使用自己的 UserDetailsService
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
        .and().authenticationProvider(rememberMeAuthenticationProvider());
    }

    /**
     * 向容器中注入 PasswordEncoder
     * @return BCryptPasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
