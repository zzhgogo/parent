package com.zhuhao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

    /**
     * 配置邮件发送器
     * @return
     */
    @Bean("qqEmail")
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");//指定用来发送Email的邮件服务器主机名
        mailSender.setPort(587);//默认端口，标准的SMTP端口
        mailSender.setUsername("1536191986@qq.com");//用户名
        mailSender.setPassword("peaepdmoaucrgadj");//密码
        return mailSender;
    }
}
