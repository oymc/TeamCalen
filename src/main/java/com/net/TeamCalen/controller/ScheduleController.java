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
import com.net.TeamCalen.utils.JsonSet;

import net.minidev.json.JSONObject;

@Controller
public class ScheduleController {
	@Autowired
	ScheduleService scheduleService;
	//根据 ID 返回对应日程信息
	@GetMapping("controlPanel/getScheduleById")
	@ResponseBody
	public JSONObject dogetScheduleById(@RequestParam("scheduleId") int scheduleId,HttpServletRequest request) {
		JSONObject jsonReturn=new JSONObject();
		HttpSession session=request.getSession();
		if((int)session.getAttribute("user_id")!=scheduleService.judgeUserbyScheduleId(scheduleId)) {
			jsonReturn=JsonSet.jsonReturnSet(403, null);
			return jsonReturn;
		}
		Map<String, Object> map=scheduleService.selectSchedulebyscheduleid(scheduleId);
		System.out.println("getScheduleId"+map.get("hasReminder"));
		if((Integer)map.get("hasReminder")==1) {
			map.put("hasReminder", true);
		}
		else {
			map.put("hasReminder", false);
		}
		jsonReturn=JsonSet.jsonReturnSet(200,map);
		return jsonReturn;
	}
	//得到某一天的所有日程
	@GetMapping("controlPanel/getSchedulesByDay")
	@ResponseBody
	public JSONObject getSchedulesByDay(@RequestParam("year") String year,@RequestParam("month") String month,@RequestParam("day") String day,HttpServletRequest request){
		HttpSession session=request.getSession();
		int user_id=(int) session.getAttribute("user_id");
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);
		List<Map<String, Object>> schedule=scheduleService.selectSchedulebydate(date,user_id);
		JSONObject jsonData=new JSONObject();
		jsonData.put("schedules", schedule);//将list转为Json
		return JsonSet.jsonReturnSet(200, jsonData);
	}
	//返回用户从今天起到未来的特定条日程
	@GetMapping("controlPanel/getRecentSchedules")
	@ResponseBody
	public JSONObject getRecentSchedules(@RequestParam("amount") int amount,HttpServletRequest request){
//		int amount=(int) jsonObject.getAsNumber("amount");
		HttpSession session=request.getSession();
		int user_id=(int) session.getAttribute("user_id");
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		List<Map<String, Object>> schedule=scheduleService.selectRecentSchedules(date,"ASC",amount,user_id);
		JSONObject jsonData=new JSONObject();
		jsonData.put("schedules", schedule);
		return JsonSet.jsonReturnSet(200, jsonData);
	}
	//TODO 获取指定年月中每一天的日程数量
	@GetMapping("controlPanel/getEveryDayScheduleAmountInAMonth")
	@ResponseBody
	public JSONObject getEveryDayScheduleAmountInAMonth(@RequestParam("year") String year,@RequestParam("month") String month,HttpServletRequest request) {
		HttpSession session=request.getSession();
		int user_id=(int) session.getAttribute("user_id");
		//某年某月 'YYYY-MM'
		String yearmonth=year+'-'+month;
		//获取该年该月份最大天数
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyymm");
		try {
			calendar.setTime(simpleDateFormat.parse(year+month));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int MaxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		//生成数组并初始化
		int[] scheduleAmount=new int[MaxDay];
		for(int i=0;i<MaxDay;i++) {
			scheduleAmount[i]=0;
		}
		List<Amount> list=scheduleService.getEveryDayScheduleAmountInAMonth(yearmonth,user_id);
		for(Amount m:list) {
			String day=m.getDay();
			int index=Integer.parseInt(day);
			int value=m.getScheduleAmount();
			scheduleAmount[index-1]=value;//遍历得到每天对应的日程数
	}
//		for(int i=0;i<MaxDay;i++) {
//			System.out.print(scheduleAmount[i]+'\n');
//		}
		JSONObject jsonData=new JSONObject();
		jsonData.put("scheduleAmount", scheduleAmount);
		return JsonSet.jsonReturnSet(200, jsonData);
	}
	//得到用户信息
	@GetMapping("controlPanel/getUserInfo")
	@ResponseBody
	public JSONObject getUserInfo(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String username=(String) session.getAttribute("username");
		JSONObject jsonName =new JSONObject();
		jsonName.put("username", username);
		return JsonSet.jsonReturnSet(200, jsonName);
	}
	//code and data
//	private JSONObject setJson(int code,Object data) {
//		JSONObject json=new JSONObject();
//		json.put("code", code);
//		json.put("data", data);
//		return json;
//	}
}
