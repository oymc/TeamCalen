package com.net.TeamCalen.entity;

import java.io.Serializable;

import org.apache.tomcat.util.http.fileupload.ThresholdingOutputStream;

public class User implements Serializable {
	private int user_id;
	private String username;
	private String password;
	private String email;
	private String picture;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ",email='" + email +'\''+
                '}';
    }
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public User() {}
	public User(String username,String password,String email) {
		this.username=username;
		this.password=password;
		this.email=email;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
}
