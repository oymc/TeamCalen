package com.net.TeamCalen.dao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.net.TeamCalen.entity.User;
@Repository
public interface UserDao {
	/**
	 * 注册
	 * @param userid
	 * @param password
	 * @param email
	 */
	public boolean inserUser(@Param("user") User user);
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return user
	 */
	public User selectUser(@Param("username") String username,@Param("password") String password);
	
	public String selectEmailbyName(@Param("username") String username);
	
//	public User selectUser(@Param("userid") String userid,@Param("password") String password,@Param("email") String email);
	/**
	 * 找回密码
	 * @param username
	 * @return email
	 */
	public String selectUserbyname(@Param("username") String username);
}
