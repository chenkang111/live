package com.zcf.live;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zcf.live.constants.Constants;
import com.zcf.live.constants.Constants2;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 资源监听类接口
 * 
 * @author Administrator
 *
 */
@Slf4j
public class ResourceListener {

	/**
	 * 
	 * 获取房间在线人数 （会有延迟）
	 * 
	 * @param roomid
	 * @return
	 */
	public static Integer getOnlineNumer(String roomnumber) {
		Integer online = 0;
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("DomainName", "play.chnekang.top");
		parameters.put("AppName", Constants.APP_NAME);
		parameters.put("StreamName", roomnumber);
		String action = "DescribeLiveStreamOnlineUserNum";
		JSONObject res = LiveUtil.alisRequest(action, parameters);
		log.info("获取在线人数接口返回：{}", res.toJSONString());
		JSONArray infolist = res.getJSONObject("OnlineUserInfo").getJSONArray("LiveStreamOnlineUserNumInfo");
		for (Iterator<Object> tor = infolist.iterator(); tor.hasNext();) {
			JSONObject job = (JSONObject) tor.next();
			if (job.get("StreamUrl").toString().equals(Constants2.getORginPlayUrl(roomnumber))) {
				return job.getInteger("UserNumber");
			}
		}
		return online;
	}

}
