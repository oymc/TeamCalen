package com.net.TeamCalen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.TeamCalen.dao.UserDao;
import com.net.TeamCalen.entity.User;


@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	public boolean inserUser(User user) {
		return userDao.inserUser(user);
	}
    public User selectUser(String username,String password) {
    	System.out.println(username+" "+username);
    	return userDao.selectUser(username, password);
    }
//    public User selectUserbymailbox(String email) {
//    	//System.out.println(username+" "+username);
//    	return userDao.selectUserbymailbox(email);
//    }
	public String selectEmailbyName(String username) {
    	return userDao.selectEmailbyName(username);
  }
}
