package com.net.TeamCalen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.net.TeamCalen.utils.EmailUtils;

import lombok.extern.slf4j.Slf4j;

@Service
//@EnableAspectJAutoProxy(exposeProxy = true)
//@Slf4j
public class SendByEmailTools {
	
	@Autowired  
    private JavaMailSender mailSender; 
	/**异步方法
     * 有@Async注解的方法，默认就是异步执行的，会在默认的线程池中执行，但是此方法不能在本类调用；启动类需添加直接开启异步执行@EnableAsync。
     * */
	@Async
	public void asyncSendEmail(String sender,String receiver,String title,String text){  
		try {
			EmailUtils emailUtils=new EmailUtils(mailSender);
			while(emailUtils.sendEmail(sender, receiver, title, text)==false) {
				System.out.println("邮件发送错误，重新发送中");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
    }  
}
