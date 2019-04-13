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
@EnableAspectJAutoProxy(exposeProxy = true)
@Slf4j
public class SendByEmailTools {
	
	@Autowired  
    private JavaMailSender mailSender;  
	@Async 
    public boolean asyncSendEmail(String sender,String receiver,String title,String text){  
		try {
			EmailUtils emailUtils=new EmailUtils(mailSender);
			emailUtils.sendEmail(sender, receiver, title, text);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
    }  
}
