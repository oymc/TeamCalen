package com.net.TeamCalen.service;

import org.apache.ibatis.annotations.Param;
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
    public int selectUser(String username,String password) {
//    	System.out.println(username+" "+username);
    	return userDao.selectUser(username, password);
    }
//    public User selectUserbymailbox(String email) {
//    	//System.out.println(username+" "+username);
//    	return userDao.selectUserbymailbox(email);
//    }
    public int selectUserbyname(String username) {
    	return userDao.selectUserbyname(username);
    }
	public String selectEmailbyName(String username) {
    	return userDao.selectEmailbyName(username);
  }
	public boolean updatePasswordbyName(String username,String password) {
		return userDao.updatePasswordbyName(username, password);
	}
	public boolean updatePasswordbyId(int user_id,String password) {
		return userDao.updatePasswordbyId(user_id, password);
	}
	public String selectNamebyId(int user_id) {
		return userDao.selectNamebyId(user_id);
	}
	public String selectPicbyId(int user_id) {
		return userDao.selectPicbyId(user_id);
	} 
	public boolean updatePicbyId(int user_id,String picture) {
		return userDao.updatePicbyId(user_id, picture);
	}
	public boolean updateEmail(String email,int user_id) {
		return userDao.updateEmail(email, user_id);
	}
	public String selectPassword(int user_id) {
		return userDao.selectPassword(user_id);
	}
}
