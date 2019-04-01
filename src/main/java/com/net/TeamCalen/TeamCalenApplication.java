package com.net.TeamCalen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.net.TeamCalen.dao")
public class TeamCalenApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamCalenApplication.class, args);
	}

}
