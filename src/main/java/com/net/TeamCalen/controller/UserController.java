package com.net.TeamCalen.controller;

import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.TeamCalen.entity.User;
import com.net.TeamCalen.service.SendByEmailTools;
import com.net.TeamCalen.service.UserService;

import ch.qos.logback.core.net.LoginAuthenticator;
import net.minidev.json.JSONObject;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private SendByEmailTools sendbyEmailTools;
	@PostMapping("account/login")
	@ResponseBody
	public JSONObject login(@RequestBody  JSONObject jsonObject,HttpServletRequest request)
	{
		JSONObject json=new JSONObject();
		try {
			String username=jsonObject.getAsString("username");
			String password=jsonObject.getAsString("password");
			String user_id="";
			user_id=userService.selectUser(username, password);
			HttpSession session=request.getSession();
			if(user_id!=null) {
				session.setAttribute("user_id",Integer.parseInt(user_id));
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				json.put("code", 200);
				json.put("data", null);
			}
			else {
				json.put("code", 404);
				json.put("data", null);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", 400);
			json.put("data", null);
		}
		return json;
	}
	//向指定邮箱发送验证码
	@PostMapping("account/sendVerificationCodeByEmail")
	@ResponseBody
	public JSONObject sendVerificationCodeByEmail(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
		JSONObject json=new JSONObject();
		try {
			String receiver=jsonObject.getAsString("email");
			System.out.println(receiver);
			String sender ="TeamCalen@163.com";
			String title="TeamCalen注册";
			String code=String.valueOf(new Random().nextInt(899999)+100000);
//			HttpSession session=request.getSession();
//			session.setAttribute("verificationCode", code);
			if(sendbyEmailTools.send(sender, receiver, title, "验证码为:"+code)) {
				json.put("code", 200);
				json.put("data", null);
			}
			HttpSession session=request.getSession();
			session.setAttribute("verificationCode", code);
			this.removeAttrbute(session, "verificationCode");
//			System.out.println(session.getAttribute("verificationCode"));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", 400);
			json.put("data", null);
		}
		return json;
	}
	@PostMapping("account/signUp")
	@ResponseBody
	public JSONObject signUp(@RequestBody JSONObject jsonObject,HttpServletRequest request) {
		JSONObject json=new JSONObject();
		try {
			HttpSession session=request.getSession();
			String username=jsonObject.getAsString("username");
			String password=jsonObject.getAsString("password");
			String email=jsonObject.getAsString("email");
			String verificationCode=jsonObject.getAsString("verificationCode");
			String verificationCode2=(String) session.getAttribute("verificationCode");
			if(userService.selectUserbyname(username)!=null) {
				json.put("code", 409);
				json.put("data", null);
				return json;
			}
//			System.out.println(verificationCode);
//			System.out.println(verificationCode2);
			if(verificationCode.equals(verificationCode2)) {
				User user=new User(username,password,email);
			    userService.inserUser(user);
			    int user_id=user.getUser_id();
			    session.setAttribute("user_id", user_id);
			    System.out.println("iaghiawrghiorqghqugh"+session.getAttribute("user_id"));
			}
			else {
				json.put("code", 403);
				json.put("data", null);
				return json;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", 400);
			json.put("data", null);
			return json;
		}
		json.put("code", 200);
		json.put("data", null);
		return json;
	}
	private void removeAttrbute(final HttpSession session,final String verificationCode) {
		final Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// 5分钟后删除session中的验证码
				session.removeAttribute(verificationCode);
				timer.cancel();
			}
		},5*60*1000);
	}
}
