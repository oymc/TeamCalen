package com.net.TeamCalen.controller;

import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.print.attribute.standard.RequestingUserName;
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
import com.net.TeamCalen.utils.JsonSet;
import com.net.TeamCalen.utils.RemoveSessionUtils;

import ch.qos.logback.core.net.LoginAuthenticator;
import net.minidev.json.JSONObject;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@PostMapping("account/login")
	@ResponseBody
	public JSONObject login(@RequestBody  JSONObject jsonObject,HttpServletRequest request)
	{
		JSONObject json=new JSONObject();
		try {
			String username=jsonObject.getAsString("username");
			String password=jsonObject.getAsString("password");
			int user_id=userService.selectUser(username, password);
			HttpSession session=request.getSession();
			if(user_id!=0) {
				session.setAttribute("user_id",user_id);
//				session.setAttribute("username", username);
//				session.setAttribute("password", password);
				return JsonSet.jsonReturnSet(200, null);
			}
			else {
				return JsonSet.jsonReturnSet(404, null);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return JsonSet.jsonReturnSet(500, null);
		}
		
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
			if(userService.selectUserbyname(username)!=0) {
				return JsonSet.jsonReturnSet(409, null);
				
			}
//			System.out.println(verificationCode);
//			System.out.println(verificationCode2);
			if(verificationCode.equals(verificationCode2)) {
				User user=new User(username,password,email);
			    userService.inserUser(user);
//			    int user_id=user.getUser_id();
//			    session.setAttribute("user_id", user_id);
//			    System.out.println("iaghiawrghiorqghqugh"+session.getAttribute("user_id"));
			}
			else {
				return JsonSet.jsonReturnSet(403, null);
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return JsonSet.jsonReturnSet(500, null);
		}
		return JsonSet.jsonReturnSet(200, null);
	}
	@PostMapping("account/retrievePassword")
	@ResponseBody
	public JSONObject retrievePassword(@RequestBody JSONObject jsonObject,HttpServletRequest request) {
		HttpSession session=request.getSession();
		String username=jsonObject.getAsString("username");
		System.out.println("iaghiawrghiorqghqugh"+username);
		String verificationCode=jsonObject.getAsString("verificationCode");
		System.out.println("iaghiawrghiorqghqugh"+verificationCode);
		String password=jsonObject.getAsString("password");
		System.out.println("iaghiawrghiorqghqugh"+password);
		String retrieveVerificationCode=(String) session.getAttribute("retrieveVerificationCode");
		System.out.println("iaghiawrghiorqghqugh"+retrieveVerificationCode);
//		System.out.println(verificationCode);
//		System.out.println(verificationCode2);
		if(verificationCode.equals(retrieveVerificationCode)) {
//			User user=userService.selectUserbyname(username);
			int user_id=userService.selectUserbyname(username);
			if(user_id==0) {
				return JsonSet.jsonReturnSet(404, null);//用户名不存在
			}
//		    int user_id=user.getUser_id();
//		    session.setAttribute("user_id", user_id);
//		    System.out.println("iaghiawrghiorqghqugh"+session.getAttribute("user_id"));
			if(userService.updatePasswordbyName(username, password)) {
				return JsonSet.jsonReturnSet(200, null);
			}
			else {
				return JsonSet.jsonReturnSet(400, null);
			}
		}
		else {
			return JsonSet.jsonReturnSet(403, null);//验证码错误
		}
	}
	@PostMapping("account/logout")
	@ResponseBody
	public JSONObject logout(HttpServletRequest request) {
		try {
			HttpSession session=request.getSession();
			session.invalidate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return JsonSet.jsonReturnSet(500, null);
		}
		return JsonSet.jsonReturnSet(200, null);
		
	}
}
