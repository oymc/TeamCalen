package com.net.TeamCalen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
@MapperScan("com.net.TeamCalen.dao")
public class TeamCalenApplication 
//extends WebMvcConfigurationSupport
{
//	/**
//	   * 1、 extends WebMvcConfigurationSupport
//	   * 2、重写下面方法;
//	   * setUseSuffixPatternMatch : 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真即匹配；
//	   * setUseTrailingSlashMatch : 设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认真即匹配；
//	   */
//	  @Override
//	  public void configurePathMatch(PathMatchConfigurer configurer) {
//	    configurer.setUseSuffixPatternMatch(false)
//	          .setUseTrailingSlashMatch(true);
//	  }
	public static void main(String[] args) {
		SpringApplication.run(TeamCalenApplication.class, args);
	}

}
