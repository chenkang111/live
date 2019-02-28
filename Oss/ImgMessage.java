package com.zcf.live.Oss;

import java.util.Map;

import lombok.Data;

@Data
public class ImgMessage {

	private String status;

	private Map<String, Object> data;

	public ImgMessage(String status, Map<String, Object> data) {
		this.status = status;
		this.data = data;
	}

	public ImgMessage() {
	}

}
