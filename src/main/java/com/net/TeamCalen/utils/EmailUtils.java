package com.net.TeamCalen.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailUtils {
	private JavaMailSender mailSender;

    public EmailUtils(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public boolean sendEmail(String sender,String receiver,String title,String text){  
        //建立邮件消息  
        SimpleMailMessage mainMessage = new SimpleMailMessage();  
        //发送者 
        mainMessage.setFrom(sender);  
        //接收者  
        mainMessage.setTo(receiver);  
        //发送的标题  
        mainMessage.setSubject(title);  
        //发送的内容  
        mainMessage.setText(text);  
        mailSender.send(mainMessage);  
        return true;  
    }  
}
