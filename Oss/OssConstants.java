package com.zcf.live.Oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.zcf.live.constants.CommonConstants;

//阿里云 oss  参数配置
public class OssConstants {

	public static final String BucketName = "jietu22";

	// end 节点
	public static final String Endpoint = "oss-ap-southeast-1.aliyuncs.com";

	/**
	 * 
	 * 获取该推流的视频截图 访问路径
	 * 
	 * @param appName
	 * @param stremName
	 * @return
	 */
	public static String getScreenshotUrl(String appName, String stremName) {
		return "http://" + BucketName + "." + Endpoint + appName + File.separator + stremName + ".jpg";
	}

	public static void up() {
		// 创建新实例
		AliOssClient client = new AliOssClient();
		// 连接需要的信息
		client.setAccessKeyId(CommonConstants.AccessKey);
		client.setAccessKeySecret(CommonConstants.AccessKeySecret);
		client.setEndpoint(Endpoint);
		// 返回的文件访问路径
		try {
			InputStream inputStream = new FileInputStream("D:\\demo.mp4");
			client.putObject(BucketName, "test/" + "demo.mp4", inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		up();
	}
}
