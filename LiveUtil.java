package com.zcf.live;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.zcf.common.utils.HttpUtils;
import com.zcf.live.constants.Constants2;
import com.zcf.live.util.CalendarUtil;
import com.zcf.live.util.RequstParamsUtil;
import com.zcf.live.util.UrlUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LiveUtil {

	/**
	 * 
	 * 获取推流地址
	 * 
	 * @param roomName
	 * @return
	 */
	public static String getPushUri(String roomName) {
		return Constants2.getPullUrl(roomName) + "?" + pushGenerateAuthKey(roomName, CalendarUtil.getExpireTime(30));
	}

	/**
	 * 
	 * 获取拉流地址
	 * 
	 * @param roomName
	 * @return
	 */
	public static String getPlayUri(String roomName) {
		return Constants2.getORginPlayUrl(roomName) + "?"
				+ playGenerateAuthKey(roomName, CalendarUtil.getExpireTime(30));
	}

	/**
	 * @param action
	 *            请求接口名称
	 * @param params
	 *            所需的参数
	 * @return
	 */
	public static JSONObject alisRequest(String action, Map<String, String> params) {
		try {
			return HttpUtils.httpsRequest(RequstParamsUtil.getliveBaseUrl(action, params), HttpUtils.GET, null);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("阿里接口请求异常");
			return null;
		}
	}

	// 推完整验签串
	private static String pushGenerateAuthKey(String roomName, Long endTime) {
		return "auth_key=" + endTime + generateUuid()
				+ generateEncryptStr(roomName, endTime, Constants2.ALI_LIVE_PULL_KEY);
	}

	// 拉完整验签串
	private static String playGenerateAuthKey(String roomName, Long endTime) {
		return "auth_key=" + endTime + generateUuid()
				+ generateEncryptStr(roomName, endTime, Constants2.ALI_LIVE_PLAY_KEY);
	}

	// 唯一标识
	private static String generateUuid() {
		String uuid = "0";
		String uid = "0";
		return "-" + uuid + "-" + uid + "-";
	}

	// 验签密钥
	private static String generateEncryptStr(String StremName, Long endTime, String key) {
		String uri = Constants2.ALI_LIVE_APP_NAME + StremName; // 这个应该要+。flv 去加密
		String SString = uri + "-" + endTime + generateUuid() + key;
		return UrlUtil.getMd5(SString);
	}

	public static void main(String[] args) {
		// log.info("推流地址:{}", getPushUri("29453781"));// 可以以此做未房间号
		log.info("拉流地址:{}", getPlayUri("53708419"));

		/*
		 * Map<String, String> parameters = new HashMap<String, String>();
		 * parameters.put("DomainName", "play.chnekang.top"); parameters.put("AppName",
		 * Constants.APP_NAME); parameters.put("StreamName", "53708419"); String action
		 * = "DescribeLiveStreamOnlineUserNum"; log.info(alisRequest(action,
		 * parameters).toJSONString());
		 */
	}

}
