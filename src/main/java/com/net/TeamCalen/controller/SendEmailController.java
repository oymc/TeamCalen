package com.net.TeamCalen.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.TeamCalen.service.SendByEmailTools;
import com.net.TeamCalen.service.UserService;
import com.net.TeamCalen.utils.JsonSet;
import com.net.TeamCalen.utils.RemoveSessionUtils;

import net.minidev.json.JSONObject;
@Controller
@EnableAsync//处理异步
public class SendEmailController {
	/**
     * 异步处理：使用springBoot自带async注解
     */
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private SendByEmailTools sendbyEmailTools;
	//向指定邮箱发送验证码
		@PostMapping("account/sendVerificationCodeByEmail")
		@ResponseBody
		public JSONObject sendVerificationCodeByEmail(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
			try {
				String receiver=jsonObject.getAsString("email");
				System.out.println(receiver);
				String sender ="TeamCalen@163.com";
				String title="TeamCalen绑定邮箱";
				String code=String.valueOf(new Random().nextInt(899999)+100000);
				System.out.println("code"+code);
				sendbyEmailTools.asyncSendEmail(sender, receiver, title, "验证码为:"+code);
				HttpSession session=request.getSession();
				session.setAttribute("verificationCode", code);
				RemoveSessionUtils.removeAttrbute(session, "verificationCode");
				logger.info(Thread.currentThread().getName());//开一个线程返回json
				return JsonSet.jsonReturnSet(200, null);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return JsonSet.jsonReturnSet(500, null);
			}
		}
		@PostMapping("account/sendVerificationCodeByUsername")
		@ResponseBody
		public JSONObject sendVerificationCodeByUsername(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
			try {
				String username=jsonObject.getAsString("username");
				int user_id=userService.selectUserbyname(username);
				if(user_id==0) {
					return JsonSet.jsonReturnSet(404, null);//用户名不存在
				}
				System.out.println(user_id);
				String receiver=userService.selectEmailbyName(username);
				System.out.println(receiver);
				String sender ="TeamCalen@163.com";
				String title="TeamCalen修改密码";
				String code=String.valueOf(new Random().nextInt(899999)+100000);
				HttpSession session=request.getSession();
				System.out.println("code"+code);
				sendbyEmailTools.asyncSendEmail(sender, receiver, title, "验证码为:"+code);
				session.setAttribute("retrieveVerificationCode", code);
				RemoveSessionUtils.removeAttrbute(session, "retrieveVerificationCode");
				logger.info(Thread.currentThread().getName());//开一个线程返回json
				return JsonSet.jsonReturnSet(200, null);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return JsonSet.jsonReturnSet(500, null);
			}
		}
}
