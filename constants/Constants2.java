package com.zcf.live.constants;

public class Constants2 {

	// Stremname配置

	public final static String ALI_LIVE_STREM_NAME = "Strem";

	// AppName配置

	public final static String ALI_LIVE_APP_NAME = "/cnalivelive/";

	// 直播中心推流地址

	public final static String ALI_LIVE_CENTER_PULL_URL = "rtmp://video-center-sg.alivecdn.com";

	// 边缘推流

	public final static String ALI_LIVE_ROUND_PULL_URL = "rtmp://push.chnekang.top";

	// 拉流 原画
	public final static String ALI_LIVE_ORIGIN_PLAY_URL = "rtmp://play.chnekang.top";

	// 拉流 其他
	public final static String ALI_LIVE_OTHER_PLAY_URL = "http://play.chnekang.top";

	// 直播推流验证密钥

	public final static String ALI_LIVE_PULL_KEY = "DNnAsVYRjR";

	// 直播拉流验证密钥

	public final static String ALI_LIVE_PLAY_KEY = "NwZtszeI37";

	// 直播域
	public final static String ALI_LIVE_STREM_DOMAIN = "live.alivelive.cc";

	/**
	 * 边缘推流地址
	 * 
	 * @return
	 */
	public static String getPullUrl(String StremName) {
		return ALI_LIVE_ROUND_PULL_URL + ALI_LIVE_APP_NAME + StremName;
	}

	// 拉流 原画地址
	public static String getORginPlayUrl(String StremName) {
		return ALI_LIVE_ORIGIN_PLAY_URL + ALI_LIVE_APP_NAME + StremName;
	}

	// 拉流 原画地址 其他
	public static String getOtherPlayUrl(String StremName) {
		return ALI_LIVE_OTHER_PLAY_URL + ALI_LIVE_APP_NAME + StremName + ".flv";
	}

}
