package com.net.TeamCalen.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.net.TeamCalen.service.SendByEmailTools;
//import org.yuyi.full.handler.exception.ResultBO;
//import org.yuyi.full.handler.exception.ResultTool;
// 
//import com.yuyi.mcb.tool.SendByEmailTools;
 
/**
 * @author mcb
 * 2018年5月4日 下午3:52:30
 *         
 */
@RestController
public class SendByEmailController {
	@Autowired
	@Qualifier("serdbyemail")
	private SendByEmailTools service;
	
	@GetMapping("/send")
	public String send(){
		
		String sender="TeamCalen@163.com";   //这个是发送人的邮箱
		String receiver="2994780356@qq.com";  //这个是接受人的邮箱
		String title="约翰福音";    //标题
		String text="【约3:16】“　神爱世人，甚至将他的独生子赐给他们，叫一切信他的，不至灭亡，反得永生。";
		
		String result=service.send(sender, receiver, title, text);
		return result;
	}
 
}
