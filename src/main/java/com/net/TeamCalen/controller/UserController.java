package com.net.TeamCalen.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.TeamCalen.entity.User;
import com.net.TeamCalen.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping("/login")
	public String login()
	{
		return "login";
	}
	@RequestMapping("/signUp")
	public String regist()
	{
		return "signUp";
	}
	@RequestMapping("/retrievePassword")
	public String retrievePassword()
	{
		return "retrievePassword";
	}
	@RequestMapping("/dologin")
	@ResponseBody
	public String dologin(User user,Map<String,Object> map){
		User user1=userService.selectUserbyname(user.getUsername(),user.getPassword());
//		User user1=userService.selectUser(user.getUsername(),user.getPassword(),user.getMailbox());
		System.out.println(user1);
		if(user1==null) {
			map.put("msg", "账号或密码错误");
			return "fail";
		}
		else {
			map.put("msg", "登录成功");
			return "success";
		}
	}
	@RequestMapping("/dosignUp")
	public String doregist(User user,Map<String,Object> map) {
		boolean user_re=userService.inserUser(user.getUsername(), user.getPassword(), user.getEmail());
		if(user_re) {
			map.put("msg", "注册成功");
			return "success";
		}
		else {
			map.put("msg", "用户名重复");
			return "fail";
		}
	}
	@RequestMapping("/doretrievePassword")
	public String doretrievePassword(User user)
	{
		if(userService.selectUserbyusername(user.getUsername())==null)
		{
			return null;
		}
		else {
			//给邮箱发验证码
			//根据前台数据判断验证码并更新数据库
			return null;
		}
		
	}
//	@RequestMapping("/doregist")
//    public String doRegist(User user,Map<String,Object> map) {
//        userService.inserUser(user.getUsername(), user.getPassword(),user.getMailbox());
//        map.put("msg","注册成功");
//        return "success";
//    }
//	@RequestMapping("/dofind")
//	public String dofind(User user,Map<String,Object> map) {
//		User user1=userService.selectUserbymailbox(user.getMailbox());
//		System.out.println(user1);
//		if(user1==null) {
//			map.put("msg", "邮箱错误");
//			return "fail";
//		}
//		else {
//			map.put("msg", "成功");
//			return "success";
//		}
//	}
}
