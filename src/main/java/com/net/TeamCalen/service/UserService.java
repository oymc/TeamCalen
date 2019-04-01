package com.net.TeamCalen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.TeamCalen.dao.UserDao;
import com.net.TeamCalen.entity.User;


@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	public boolean inserUser(String username,String password,String email) {
		userDao.inserUser(username, password,email);
		return true;
	}
	 
    public User selectUserbyname(String username,String password) {
    	System.out.println(username+" "+username);
    	return userDao.selectUserbyname(username, password);
    }
//    public User selectUserbymailbox(String email) {
//    	//System.out.println(username+" "+username);
//    	return userDao.selectUserbymailbox(email);
//    }
	public String selectUserbyusername(String username) {
    	return userDao.selectUserbyusername(username);
  }
}
