package com.zcf.live.Oss;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.zcf.live.constants.CommonConstants;

/**
 * @author chenkang
 *
 */
public class OssUtil {

	private static String OssDir = "test" + File.separator;

	/**
	 * 
	 * 文件上传到 Oss
	 * 
	 * @param file
	 * @return
	 */
	public static String upload2AlisOss(MultipartFile file) {
		// 创建新实例
		AliOssClient client = new AliOssClient();
		// 连接需要的信息
		client.setAccessKeyId(CommonConstants.AccessKey);
		client.setAccessKeySecret(CommonConstants.AccessKeySecret);
		client.setEndpoint(OssConstants.Endpoint);
		try {
			// 获取文件的原始名字
			String originalfileName = file.getOriginalFilename();
			// 重新命名文件
			String suffix = originalfileName.substring(originalfileName.lastIndexOf(".") + 1);
			String fileName = new Date().getTime() + "-img." + suffix;
			// 获得文件流
			InputStream inputStream = file.getInputStream();
			// 上传到OSS
			client.putObject(OssConstants.BucketName, OssDir + fileName, inputStream);
			// 是否有可访问的地址
			return "http://" + OssConstants.BucketName + "." + OssConstants.Endpoint + File.separator + fileName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}
