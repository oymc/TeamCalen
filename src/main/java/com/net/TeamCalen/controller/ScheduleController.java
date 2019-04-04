package com.net.TeamCalen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.TeamCalen.entity.Schedule;
import com.net.TeamCalen.service.ScheduleService;

@Controller
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;
	@PostMapping("/createSchedule")
	@ResponseBody
	public void createSchedule(@RequestParam int user_id,@RequestParam Schedule schedule)
	{
		scheduleService.insertSchedule(schedule.getSchedule_id(), schedule.getDate(), schedule.getStart_hour(),
				schedule.getStart_minute(), schedule.getEnd_hour(), schedule.getEnd_minute(),
				schedule.getSchedule_text(), schedule.getSchedule_state());
	}
}
