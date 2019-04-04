package com.net.TeamCalen.controller;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import com.net.TeamCalen.entity.Schedule;
import com.net.TeamCalen.service.ScheduleService;

@Controller
public class ScheduleController {
	@Autowired
	ScheduleService scheduleService;
	@GetMapping("/createSchedule")
	public String createSchedule() {
		return  "createSchedule";
	}
	//@RequestMapping("/docreateSchedule")
	@PostMapping("/docreateSchedule")
	@ResponseBody
	/**
	 * 新建日程
	 * @param year
	 * @param month
	 * @param day
	 * @param startHour
	 * @param startMinute
	 * @param endHour
	 * @param endMinute
	 * @param scheduleText
	 * @param hasReminder
	 * @return
	 */
	public String docreateSchedule(@RequestParam("year" )String year,@RequestParam("month" )String month,@RequestParam("day" )String day,
			@RequestParam("startHour" )int  startHour,@RequestParam("startMinute" )int startMinute,
			@RequestParam("endHour" )int endHour,@RequestParam("endMinute" ) int endMinute,
			@RequestParam("scheduleText" )String scheduleText,@RequestParam("hasReminder" ) String hasReminder) {
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);
		//userid从session中获取
		scheduleService.insertSchedule(233,date,startHour,startMinute,endHour,endMinute,scheduleText,hasReminder);
		return "success";
	}
	@GetMapping("/modifySchedule")
	public String modifySchedule() {
		return  "modifySchedule";
	}
	@PostMapping("/domodifySchedule")
	@ResponseBody
	public String domodifySchedule() {
		return "success";
	}

}
