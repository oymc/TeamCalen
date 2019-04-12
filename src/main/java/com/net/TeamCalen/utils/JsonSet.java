package com.net.TeamCalen.utils;

import net.minidev.json.JSONObject;

public class JsonSet {
	//code and data
	public static JSONObject jsonReturnSet(int code,Object data) {
		JSONObject json=new JSONObject();
		json.put("code", code);
		json.put("data", data);
		return json;
	}
}
