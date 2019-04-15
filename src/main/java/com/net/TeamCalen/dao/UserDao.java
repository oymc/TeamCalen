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
	 * @return user_id 若用户不存在返回0
	 */
	public int selectUser(@Param("username") String username,@Param("password") String password);
	/**
	 * 根据用户名查找邮箱,找回密码时用
	 * @param username
	 * @return email
	 */
	public String selectEmailbyName(@Param("username") String username);
	
//	public User selectUser(@Param("userid") String userid,@Param("password") String password,@Param("email") String email);
	/**
	 * 查找用户名是否存在
	 * @param username
	 * @return user_id 若用户不存在返回0
	 */
	public int selectUserbyname(@Param("username") String username);
	/*
	 * 找回密码时，通过username查找并修改密码
	 */
	public boolean updatePasswordbyName(@Param("username")String username,@Param("password") String password);
	/*
	 * 直接修改密码
	 */
	public boolean updatePasswordbyId(@Param("user_id") int user_id,@Param("password") String password);
	/**
	 * 返回username
	 * @param user_id
	 * @return username
	 */
	public String selectNamebyId(@Param("user_id")int user_id);
	/**
	 * 修改email
	 * @param email
	 * @param user_id
	 * @return
	 */
	public boolean updateEmail(@Param("email")String email,@Param("user_id") int user_id);
	/**
	 * 
	 * @param user_id
	 * @return password
	 */
	public String selectPassword(@Param("user_id") int user_id);
}
