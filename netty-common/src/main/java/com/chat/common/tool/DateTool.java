package com.chat.common.tool;

import java.util.Calendar;
import java.util.Date;

public class DateTool {
	
	public static boolean isOneDay(Calendar calendar, long timeMill){
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(timeMill);
		return isOneDay(calendar, calendar2);
	}
	
	public static boolean isOneDay(Date date, long timeMill){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(timeMill);
		return isOneDay(calendar, calendar2);
	}
	
	public static boolean isOneDay(Date date1,Date date2){
		Calendar ca1 = Calendar.getInstance();ca1.setTime(date1);
		Calendar ca2 = Calendar.getInstance();ca2.setTime(date2);
		return isOneDay(ca1, ca2);
	}
	
	public static boolean isToday(Date date){
		Calendar temp = Calendar.getInstance();
		temp.setTime(date);
		return isOneDay(temp, Calendar.getInstance());
	}
	
	public static boolean isToday(long millTime){
		Calendar temp = Calendar.getInstance();
		temp.setTimeInMillis(millTime);
		return isOneDay(temp, Calendar.getInstance());
	}
	
	public static boolean isOneDay(Calendar calendar, Calendar calendar2){
		int fYear = calendar.get(Calendar.YEAR);
		int sYear = calendar2.get(Calendar.YEAR);
		if (fYear != sYear){
			return true;
		}
		int fDay = calendar.get(Calendar.DAY_OF_YEAR);
		int sDay = calendar2.get(Calendar.DAY_OF_YEAR);
		if(fDay != sDay){
			return true;
		}
		return false;
	}
}
