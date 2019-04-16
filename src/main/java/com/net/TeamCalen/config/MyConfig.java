package com.net.TeamCalen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfig  extends WebMvcConfigurerAdapter{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//对应.yml中resources:static-locations:
		//访问前缀/image/，头像的真实存储位置
		registry.addResourceHandler("/image/**").addResourceLocations("file:"+SystemApi.filePath);
		System.out.println("file:"+SystemApi.filePath);
	}
}
