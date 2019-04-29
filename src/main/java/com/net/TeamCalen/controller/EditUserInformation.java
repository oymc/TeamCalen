package com.net.TeamCalen.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.net.TeamCalen.config.ServiceInfo;
import com.net.TeamCalen.config.SystemApi;
import com.net.TeamCalen.service.UserService;
import com.net.TeamCalen.utils.JsonSet;

import net.minidev.json.JSONObject;

@Controller
public class EditUserInformation {
	@Autowired
	private UserService userservice;
	@Autowired
	private ServiceInfo serviceInfo;
	//得到用户信息
		@GetMapping("controlPanel/getUserInfo")
		@ResponseBody
		public JSONObject getUserInfo(HttpServletRequest request,HttpServletResponse response) {
			try {
				HttpSession session=request.getSession();
			    int user_id=(int) session.getAttribute("user_id");
			    String username="";
			    username=userservice.selectNamebyId(user_id);
			    String picture=userservice.selectPicbyId(user_id);
//		        int i=hFile.available();
//		        byte data[]=new byte[i];
//		        hFile.read(data);
//		        hFile.close();
//		        response.setContentType("image/*");
//		        OutputStream toClient=response.getOutputStream();
//		        toClient.write(data);
//		        toClient.close();
			    JSONObject jsonName =new JSONObject();
			    jsonName.put("username", username);
			    if(picture!=null)
			    	jsonName.put("avatarSrc", "/server/image/"+picture);
				return JsonSet.jsonReturnSet(200, jsonName);
			}catch(Exception e) {
				e.printStackTrace();
				return JsonSet.jsonReturnSet(500, null);
			}
			
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
		/*
		 * 上传头像
		 */
		@PostMapping("controlPanel/uploadAvatar")
		@ResponseBody
		public JSONObject uploadAvatar(@RequestParam("avatar") MultipartFile avatar,HttpServletRequest request)throws Exception{
			HttpSession session=request.getSession();
			int user_id=(int) session.getAttribute("user_id");
			String username=userservice.selectNamebyId(user_id);
			if (!avatar.isEmpty()) {    
	            try { 
	            	String path=SystemApi.filePath;//从配置文件中获取
	            	System.out.println("path="+path);
	            	String filename=username+avatar.getOriginalFilename().substring(
	            			avatar.getOriginalFilename().lastIndexOf("."));
	            	File filepath=new File(path,filename);
	            	 // 判断路径是否存在，如果不存在就创建一个
	                if (!filepath.getParentFile().exists()) { 
	                 filepath.getParentFile().mkdirs();
	                }    
	                BufferedOutputStream out = new BufferedOutputStream(    
	                        new FileOutputStream(filepath));    
	                System.out.println(filename);  
	                out.write(avatar.getBytes());    
	                out.flush();    
	                out.close();
//	                String picture=path+filename;
//	                System.out.println(picture);
//	                return JsonSet.jsonReturnSet(200, null);
	                if(userservice.updatePicbyId(user_id, filename)) {
	                	 return JsonSet.jsonReturnSet(200, null); 
	                }
	                else {
	                	return JsonSet.jsonReturnSet(500, null); 
	                }
	            } catch (FileNotFoundException e) {    
	                e.printStackTrace();  
	                System.out.println(e.getMessage());
	                return JsonSet.jsonReturnSet(500, null);    
	            }
	        } else {    
	            return JsonSet.jsonReturnSet(400, null);   //文件是空的
	        }

		}
}
