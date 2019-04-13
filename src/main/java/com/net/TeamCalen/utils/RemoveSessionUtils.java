package com.net.TeamCalen.utils;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

public class RemoveSessionUtils {
	public static void removeAttrbute(final HttpSession session,final String verificationCode) {
		final Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// 5分钟后删除session中的验证码
				session.removeAttribute(verificationCode);
				timer.cancel();
			}
		},5*60*1000);
	}
}
