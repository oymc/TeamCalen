package com.net.TeamCalen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
@MapperScan("com.net.TeamCalen.dao")//加上映射
public class TeamCalenApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamCalenApplication.class, args);
	}

}
