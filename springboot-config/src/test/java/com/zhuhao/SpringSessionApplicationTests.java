package com.zhuhao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSessionApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	@Qualifier("qqEmail")
	private JavaMailSender mailSender;

	@Test
	public void sendSimpleEmail(){
		SimpleMailMessage message = new SimpleMailMessage();//消息构造器
		message.setFrom("1536191986@qq.com");//发件人
		message.setTo("1536191986@qq.com");//收件人
		message.setSubject("Spring Email Test");//主题
		message.setText("hello world!!");//正文
		mailSender.send(message);
		System.out.println("邮件发送完毕");
	}

}
