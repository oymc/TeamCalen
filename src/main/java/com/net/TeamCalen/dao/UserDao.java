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
	public void inserUser(@Param("username") String username,@Param("password") String password,@Param("email") String email);
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return user
	 */
	public User selectUserbyname(@Param("username") String username,@Param("password") String password);
	
//	public User selectUser(@Param("userid") String userid,@Param("password") String password,@Param("email") String email);
	/**
	 * 找回密码
	 * @param username
	 * @return email
	 */
	public String selectUserbyusername(@Param("username") String username);
}
