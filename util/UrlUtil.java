package com.zcf.live.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UrlUtil {

	/**
	 * 获取固定32 位md5字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getMd5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] bs = md5.digest(str.getBytes());
		StringBuilder sb = new StringBuilder(40);
		for (byte x : bs) {
			if ((x & 0xff) >> 4 == 0) {
				sb.append("0").append(Integer.toHexString(x & 0xff));
			} else {
				sb.append(Integer.toHexString(x & 0xff));
			}
		}
		return sb.toString();
	}

	/**
	 * 获取unix时间戳
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Long getUnixTime(Long epoch) {
		return epoch / 1000;
	}

}
