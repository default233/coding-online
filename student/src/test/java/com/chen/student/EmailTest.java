package com.chen.student;

import com.chen.student.utils.IMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author danger
 * @date 2021/3/25
 */
@SpringBootTest
public class EmailTest {

    /**
     * 注入发送邮件的接口
     */
//    @Autowired
//    private IMailService mailService;
//
//    /**
//     * 测试发送文本邮件
//     */
//    @Test
//    public void sendmail() {
//        mailService.sendSimpleMail("522207269@qq.com","主题：你好普通邮件","内容：第一封邮件");
//    }
//
//    @Test
//    public void sendmailHtml(){
//        mailService.sendHtmlMail("smfx1314@163.com","主题：你好html邮件","<h1>内容：第一封html邮件</h1>");
//    }
}
