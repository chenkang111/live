package com.zcf.live.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.zcf.live.constants.CommonConstants;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
@Slf4j
public class RequstParamsUtil {

	private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	private static final String ENCODING = "UTF-8";

	private final static String HTTP_METHOD = "GET";

	// 接口请求地址
	private static String live_url = "https://live.aliyuncs.com?";

	// 假设您获得了AccessKeyID=testid以及AccessKeySecret=testsecret，并且假定所有请求参数放在一个Java
	// Map<String, String> 对象里。
	private static String formatIso8601Date(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
		df.setTimeZone(new SimpleTimeZone(0, "GMT"));
		return df.format(date);
	}

	/**
	 * 
	 * 键 值 都是通过utf-8 编码 将编码后的参数名称和值用英文等号（=）进行连接。
	 * 
	 * @param value
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String percentEncode(String value) throws UnsupportedEncodingException {
		return value != null
				? URLEncoder.encode(value, ENCODING).replace("+", "%20").replace("*", "%2A").replace("%7E", "~")
				: null;
	}

	/**
	 * 
	 * 获取基础url 整合公用参数
	 * 
	 * @param action
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getliveBaseUrl(String action, Map<String, String> parameters)
			throws UnsupportedEncodingException {
		// 输入请求参数
		parameters.put("Action", action);
		parameters.put("Version", "2016-11-01");
		parameters.put("AccessKeyId", CommonConstants.AccessKey);
		parameters.put("Timestamp", formatIso8601Date(new Date()));
		parameters.put("SignatureMethod", "HMAC-SHA1");
		parameters.put("SignatureVersion", "1.0");
		parameters.put("SignatureNonce", UUID.randomUUID().toString());
		parameters.put("Format", "JSON");
		// 公共请求参数”和接口的自定义参数 排序参数
		String[] sortedKeys = parameters.keySet().toArray(new String[] {});
		Arrays.sort(sortedKeys);
		String SEPARATOR = "&";
		// 构造 stringToSign 字符串
		StringBuilder stringToSign = new StringBuilder();
		stringToSign.append(HTTP_METHOD).append(SEPARATOR);
		stringToSign.append(percentEncode("/")).append(SEPARATOR);
		StringBuilder urlParameters = new StringBuilder();
		StringBuilder canonicalizedQueryString = new StringBuilder();
		for (String key : sortedKeys) { // 构造 参数构造成字符
			// 这里注意编码 key 和 value
			canonicalizedQueryString.append("&").append(percentEncode(key)).append("=")
					.append(percentEncode(parameters.get(key)));
			urlParameters.append("&").append(key).append("=").append(parameters.get(key));
		}
		// 这里注意编码 canonicalizedQueryString
		stringToSign.append(percentEncode(canonicalizedQueryString.toString().substring(1)));
		String signature = HMACSha1(stringToSign.toString(), CommonConstants.AccessKeySecret + "&");
		urlParameters.append("&").append("Signature").append("=").append(percentEncode(signature));
		return live_url + urlParameters.toString().substring(1);
	}

	/**
	 * 
	 * 测试demo
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("DomainName", "play.chnekang.top");
			log.info(getliveBaseUrl("DeleteLiveStreamsNotify1UrlConfig", parameters));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算 HMAC-SHA1
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static String HMACSha1(String data, String key) {
		String result;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			result = (new BASE64Encoder()).encode(rawHmac);
		} catch (Exception e) {
			throw new Error("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}

	/*
	 * 计算MD5+BASE64
	 */
	public static String MD5Base64(String s) {
		if (s == null)
			return null;
		String encodeStr = "";
		byte[] utfBytes = s.getBytes();
		MessageDigest mdTemp;
		try {
			mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(utfBytes);
			byte[] md5Bytes = mdTemp.digest();
			BASE64Encoder b64Encoder = new BASE64Encoder();
			encodeStr = b64Encoder.encode(md5Bytes);
		} catch (Exception e) {
			throw new Error("Failed to generate MD5 : " + e.getMessage());
		}
		return encodeStr;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static String Base64(String str) {
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			byte[] textByte = str.getBytes("UTF-8");
			return encoder.encode(textByte);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
