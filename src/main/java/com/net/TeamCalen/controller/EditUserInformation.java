package com.net.TeamCalen.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.TeamCalen.service.UserService;
import com.net.TeamCalen.utils.JsonSet;

import net.minidev.json.JSONObject;

@Controller
public class EditUserInformation {
	@Autowired
	private UserService userservice;
	//得到用户信息
		@GetMapping("controlPanel/getUserInfo")
		@ResponseBody
		public JSONObject getUserInfo(HttpServletRequest request) {
			HttpSession session=request.getSession();
			int user_id=(int) session.getAttribute("user_id");
			String username="";
			username=userservice.selectNamebyId(user_id);
			JSONObject jsonName =new JSONObject();
			jsonName.put("username", username);
			return JsonSet.jsonReturnSet(200, jsonName);
		}
		@PostMapping("controlPanel/changeEmail")
		@ResponseBody
		public JSONObject changeEmail(@RequestBody JSONObject jsonObject,HttpServletRequest request) {
			HttpSession session=request.getSession();
			String verificationCodesession=(String) session.getAttribute("verificationCode");
			String email=jsonObject.getAsString("email");
			String verificationCode=jsonObject.getAsString("verificationCode");
			int user_id=(int) session.getAttribute("user_id");
			if(verificationCode.equals(verificationCodesession)&&userservice.updateEmail(email, user_id)) {
				return JsonSet.jsonReturnSet(200, null);
			}
			else {
				return JsonSet.jsonReturnSet(403, null);
			}
		}
		@PostMapping("controlPanel/changePassword")
		@ResponseBody
		public JSONObject changePassword(@RequestBody JSONObject jsonObject,HttpServletRequest request) {
			HttpSession session=request.getSession();
			int user_id=(int) session.getAttribute("user_id");
			String retrieveVerificationCode=(String) session.getAttribute("retrieveVerificationCode");
			String password=jsonObject.getAsString("password");
			String newPassword=jsonObject.getAsString("newPassword");
			String verificationCode=jsonObject.getAsString("verificationCode");
			if(!userservice.selectPassword(user_id).equals(password)) {
				return JsonSet.jsonReturnSet(409, null);//原密码错误
			}
			if(retrieveVerificationCode.equals(verificationCode)
					&&userservice.updatePasswordbyId(user_id, newPassword)) {
					return JsonSet.jsonReturnSet(200, null);
			}
			else {
				return JsonSet.jsonReturnSet(403, null);//新密码不合法？
			}
		}
}
