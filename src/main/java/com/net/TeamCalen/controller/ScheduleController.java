package com.net.TeamCalen.controller;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.sql.Date;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.coyote.http11.Http11AprProtocol;
import org.junit.validator.PublicClassValidator;
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

import com.fasterxml.jackson.databind.deser.std.DateDeserializers.CalendarDeserializer;
import com.net.TeamCalen.entity.Amount;
import com.net.TeamCalen.entity.Schedule;
import com.net.TeamCalen.service.ScheduleService;

@Controller
//@RequestMapping("controlPanel")
public class ScheduleController {
	@Autowired
	ScheduleService scheduleService;
	@GetMapping("controlPanel/createSchedule")
	public String createSchedule() {
		return  "controlPanel/createSchedule";
	}
	//@RequestMapping("/docreateSchedule")
	@PostMapping("controlPanel/docreateSchedule")
	@ResponseBody
	public int docreateSchedule(@RequestParam("year" )String year,@RequestParam("month" )String month,@RequestParam("day" )String day,
			@RequestParam("startHour" )int  startHour,@RequestParam("startMinute" )int startMinute,
			@RequestParam("endHour" )int endHour,@RequestParam("endMinute" ) int endMinute,
			@RequestParam("scheduleText" )String scheduleText
//			@RequestParam("hasReminder" ) boolean hasReminder
			) {
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);
		Schedule schedule=new Schedule(233, date, startHour, startMinute, endHour, endMinute, scheduleText);
		//userid从session中获取
		scheduleService.insertSchedule(schedule);
		return schedule.getSchedule_id();
	}
	//切换日程完成
	@GetMapping("controlPanel/changeScheduleState")
	public String changeScheduleState() {
		return "controlPanel/changeScheduleState";
	}
	/**
	 * 这里的state用的string,记得改
	 * @param scheduleId
	 * @param state
	 * @return
	 */
	@PostMapping("controlPanel/dochangeScheduleState")
	@ResponseBody
	public void dochangeScheduleState(@RequestParam("scheduleId" ) int scheduleId,@RequestParam("state" ) boolean state) {
		state=true;
		String stateStr="";
		if(state==true) {
			stateStr="finished";
		}
		else {
			stateStr="unfinished";
		}
		scheduleService.updateSchedulebystate(scheduleId, stateStr);
	}
	//恢复已经被取消的日程
	@GetMapping("controlPanel/resumeSchedule")
	public String resumeSchedule() {
		return "controlPanel/resumeSchedule";
	}
	@PostMapping("controlPanel/doresumeSchedule")
	@ResponseBody
	public void doresumeSchedule(@RequestParam("scheduleId" ) int scheduleId) {
		scheduleService.updateSchedulebystate(scheduleId, "unfinished");
	}
	//取消日程
		@GetMapping("controlPanel/cancelSchedule")
		public String cancelSchedule() {
			return "controlPanel/cancelSchedule";
		}
		@PostMapping("controlPanel/docancelSchedule")
		@ResponseBody
		public void docancelSchedule(@RequestParam("scheduleId" ) int scheduleId) {
			scheduleService.updateSchedulebystate(scheduleId, "canceled");
		}
	//删除日程
	@GetMapping("controlPanel/deleteSchedule")
	public String deleteSchedule() {
		return "controlPanel/deleteSchedule";
	}
	@PostMapping("controlPanel/dodeleteSchedule")
	@ResponseBody
	public String dodeleteSchedule(@RequestParam("scheduleId" ) int scheduleId) {
		if(scheduleService.deleteSchedule(scheduleId)) {
			return "success";
		}
		else return "fail";
	}
	//编辑日程
	@GetMapping("controlPanel/modifySchedule")
	public String modifySchedule() {
		return  "controlPanel/modifySchedule";
	}
	@PostMapping("controlPanel/domodifySchedule")
	@ResponseBody
	public String domodifySchedule(@RequestParam("id" )int id,@RequestParam("year" )String year,@RequestParam("month" )String month,@RequestParam("day" )String day,
			@RequestParam("startHour" )int  startHour,@RequestParam("startMinute" )int startMinute,
			@RequestParam("endHour" )int endHour,@RequestParam("endMinute" ) int endMinute,
			@RequestParam("scheduleText" )String scheduleText
//			@RequestParam("hasReminder" ) boolean hasReminder
			) {
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);
		Schedule schedule=new Schedule(233, date, startHour, startMinute, endHour, endMinute, scheduleText);
		//userid从session中获取
		scheduleService.updateSchedule(id, schedule);
		return "success";
	}
	//根据 ID 返回对应日程信息
	@GetMapping("controlPanel/getScheduleById")
	@ResponseBody
	public Map<String, Object> dogetScheduleById(@RequestParam("scheduleId" ) int scheduleId) {
		Map<String, Object> map=scheduleService.selectSchedulebyscheduleid(scheduleId);
		return map;
	}
	//得到某一天的所有日程
	@GetMapping("controlPanel/getSchedulesByDay")
	@ResponseBody
	public List<Map<String,Object>> getSchedulesByDay(HttpServletRequest request){
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		String day=request.getParameter("day");
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);
		List<Map<String, Object>> schedate=scheduleService.selectSchedulebydate(date);
		return schedate;
	}
	//返回用户从今天起到未来的特定条日程
	@GetMapping("controlPanel/getRecentSchedules")
	@ResponseBody
	public List<Map<String,Object>> getRecentSchedules(HttpServletRequest request){
		int amount=Integer.parseInt(request.getParameter("amount"));
//		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
//		Date date=sdf.format(new Date());
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		List<Map<String, Object>> schedate=scheduleService.selectRecentSchedules(date,"ASC",amount);
		return schedate;
	}
	//获取指定年月中每一天的日程数量
	@GetMapping("controlPanel/getEveryDayScheduleAmountInAMonth")
	@ResponseBody
	public int[] getEveryDayScheduleAmountInAMonth(HttpServletRequest request) {
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		String yearmonth=year+'-'+month;
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyymm");
		try {
			calendar.setTime(simpleDateFormat.parse(year+month));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int MaxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int[] scheduleAmount=new int[MaxDay];
		for(int i=0;i<MaxDay;i++) {
			scheduleAmount[i]=0;
		}
		List<Amount> list=scheduleService.getEveryDayScheduleAmountInAMonth(yearmonth);
		for(Amount m:list) {
			String day=m.getDay();
			int index=Integer.parseInt(day);
			int value=m.getScheduleAmount();
			scheduleAmount[index-1]=value;
	}
		for(int i=0;i<MaxDay;i++) {
			System.out.print(scheduleAmount[i]+'\n');
		}
		return scheduleAmount;
	}
}
