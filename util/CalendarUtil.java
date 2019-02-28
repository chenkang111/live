package com.zcf.live.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	/**
	 * 
	 * 
	 * @param minute
	 *            失效时间单位（min）
	 */
	public static synchronized Long getExpireTime(Integer minute) {
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, minute);
		return nowTime.getTimeInMillis() / 1000;
	}

	/**
	 * 
	 * 计算时间差 (单位是分钟)
	 * 
	 * @param stime
	 * @return
	 */
	public static int diffminutes(Date stime) {
		Calendar dateOne = Calendar.getInstance();
		Calendar dateTwo = Calendar.getInstance();
		dateOne.setTime(new Date()); // 设置为当前系统时间
		dateTwo.setTime(stime); // 设置为2015年1月15日
		long timeOne = dateOne.getTimeInMillis();
		long timeTwo = dateTwo.getTimeInMillis();
		return Integer.parseInt(Math.abs((timeOne - timeTwo) / (1000 * 60)) + "");// 转化minute
	}

	public static void main(String[] args) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			System.out.println(diffminutes(sf.parse("2019-2-25 18:50:00")));
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
