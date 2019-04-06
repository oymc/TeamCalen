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
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

import net.minidev.json.JSONObject;

@Controller
//@RequestMapping("controlPanel")
public class ScheduleController {
	@Autowired
	ScheduleService scheduleService;
	@PostMapping("controlPanel/createSchedule")
	@ResponseBody
	public void docreateSchedule(@RequestBody JSONObject jsonObject) {
		//userid从session中获取
//		HttpSession session=request.getSession();
//		int user_id=(int)session.getAttribute("user_id");
		String year=jsonObject.getAsString("year");
		String month=jsonObject.getAsString("month");
		String day=jsonObject.getAsString("day");
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);
		int startHour=(int) jsonObject.getAsNumber("startHour");
		int startMinute=(int) jsonObject.getAsNumber("startMinute");
		int endHour=(int) jsonObject.getAsNumber("endHour");
		int endMinute=(int) jsonObject.getAsNumber("endMinute");
		String scheduleText=jsonObject.getAsString("scheduleText");
		boolean hasReminder=false;
		if(jsonObject.getAsString("hasReminder").equals("true")){
				hasReminder=true;
		}
		Schedule schedule=new Schedule(233,date,startHour,startMinute,endHour,endMinute,scheduleText,hasReminder);
//		schedule.setUser_id(233);
	scheduleService.insertSchedule(schedule);
	}
	//切换日程完成
	/**
	 * 这里的state用的string,记得改
	 * @param scheduleId
	 * @param state
	 * @return
	 */
	@PostMapping("controlPanel/changeScheduleState")
	@ResponseBody
	public void dochangeScheduleState(@RequestBody  JSONObject jsonObject) {
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		String state=jsonObject.getAsString("state");
		String stateStr="";
		if(state.equals("true")) {
			stateStr="finished";
		}
		else {
			stateStr="unfinished";
		}
		scheduleService.updateSchedulebystate(scheduleId, stateStr);
	}
	//恢复已经被取消的日程
	@PostMapping("controlPanel/resumeSchedule")
	@ResponseBody
	public void doresumeSchedule(@RequestBody  JSONObject jsonObject) {
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		scheduleService.updateSchedulebystate(scheduleId, "unfinished");
	}
	//取消日程
		@PostMapping("controlPanel/cancelSchedule")
		@ResponseBody
		public void docancelSchedule(@RequestBody  JSONObject jsonObject) {
			int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
			scheduleService.updateSchedulebystate(scheduleId, "canceled");
		}
	//删除日程
	@PostMapping("controlPanel/deleteSchedule")
	@ResponseBody
	public void dodeleteSchedule(@RequestBody  JSONObject jsonObject) {
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		scheduleService.deleteSchedule(scheduleId);
	}
	//编辑日程
	@PostMapping("controlPanel/modifySchedule")
	@ResponseBody
	public void domodifySchedule(@RequestBody  JSONObject jsonObject) {
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		String year=jsonObject.getAsString("year");
		String month=jsonObject.getAsString("month");
		String day=jsonObject.getAsString("day");
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);
		int startHour=(int) jsonObject.getAsNumber("startHour");
		int startMinute=(int) jsonObject.getAsNumber("startMinute");
		int endHour=(int) jsonObject.getAsNumber("endHour");
		int endMinute=(int) jsonObject.getAsNumber("endMinute");
		String scheduleText=jsonObject.getAsString("scheduleText");
		boolean hasReminder=false;
		if(jsonObject.getAsString("hasReminder").equals("true")){
				hasReminder=true;
		}
		Schedule schedule=new Schedule(date, startHour, startMinute, endHour, endMinute, scheduleText,hasReminder);
		//userid从session中获取
		scheduleService.updateSchedule(scheduleId, schedule);
	}
	//根据 ID 返回对应日程信息
	@GetMapping("controlPanel/getScheduleById")
	@ResponseBody
	public Map<String, Object> dogetScheduleById(@RequestBody  JSONObject jsonObject) {
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		Map<String, Object> map=scheduleService.selectSchedulebyscheduleid(scheduleId);
		return map;
	}
	//得到某一天的所有日程
	@GetMapping("controlPanel/getSchedulesByDay")
	@ResponseBody
	public List<Map<String,Object>> getSchedulesByDay(@RequestBody  JSONObject jsonObject){
		String year=jsonObject.getAsString("year");
		String month=jsonObject.getAsString("month");
		String day=jsonObject.getAsString("day");
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);		
		List<Map<String, Object>> schedate=scheduleService.selectSchedulebydate(date);
		return schedate;
	}
	//返回用户从今天起到未来的特定条日程
	@GetMapping("controlPanel/getRecentSchedules")
	@ResponseBody
	public List<Map<String,Object>> getRecentSchedules(@RequestBody  JSONObject jsonObject){
		int amount=(int) jsonObject.getAsNumber("amount");
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		List<Map<String, Object>> schedate=scheduleService.selectRecentSchedules(date,"ASC",amount);
		return schedate;
	}
	//获取指定年月中每一天的日程数量
	@GetMapping("controlPanel/getEveryDayScheduleAmountInAMonth")
	@ResponseBody
	public int[] getEveryDayScheduleAmountInAMonth(@RequestBody  JSONObject jsonObject) {
		String year=jsonObject.getAsString("year");
		String month=jsonObject.getAsString("month");
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
