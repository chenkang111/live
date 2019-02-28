package com.zcf.live.Oss;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;

//阿里云 OSs 服务操作
public class AliOssClient {

	private String accessKeyId;

	private String accessKeySecret;

	private String endpoint;

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * 上传某个Object
	 *
	 * @param bucketName
	 * @param bucketUrl
	 * @param inputStream
	 * @return
	 */
	public boolean putObject(String bucketName, String bucketUrl, InputStream inputStream) {
		OSSClient client = new OSSClient(this.endpoint, this.accessKeyId, this.accessKeySecret);
		try {
			// 上传Object.
			client.putObject(bucketName, bucketUrl, inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			client.shutdown();
		}
		return true;
	}

	/**
	 * 删除某个Object
	 *
	 * @param bucketName
	 * @param bucketUrl
	 * @return
	 */
	public boolean deleteObject(String bucketName, String bucketUrl) {
		OSSClient client = new OSSClient(this.endpoint, this.accessKeyId, this.accessKeySecret);
		try {
			// 删除Object.
			client.deleteObject(bucketName, bucketUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			client.shutdown();
		}
		return true;
	}

	/**
	 * 下载文件
	 * 
	 * @param bucketName
	 * @param objectName
	 * @param filepath
	 * @return
	 */
	public boolean getObjects(String bucketName, String objectName, String filepath) {
		OSSClient client = new OSSClient(this.endpoint, this.accessKeyId, this.accessKeySecret);
		try {
			// 下载文件 名字文件都是自己取
			client.getObject(new GetObjectRequest(bucketName, objectName), new File(filepath));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			client.shutdown();
		}
		return true;
	}

	/**
	 * 流试下载 (待测)
	 * 
	 * @param bucketName
	 * @param objectName
	 * @param filepath
	 * @return
	 */
	public boolean getObjectsByStrem(String bucketName, String objectName) {
		OSSClient client = new OSSClient(this.endpoint, this.accessKeyId, this.accessKeySecret);
		try {
			// 下载文件 名字文件都是自己取
			OSSObject ossObject = client.getObject(new GetObjectRequest(bucketName, objectName));
			BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				System.out.println("\n" + line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			client.shutdown();
		}
		return true;
	}

	/**
	 * 删除多个Object
	 *
	 * @param bucketName
	 * @param bucketUrls
	 * @return
	 */
	public boolean deleteObjects(String bucketName, List<String> bucketUrls) {
		OSSClient client = new OSSClient(this.endpoint, this.accessKeyId, this.accessKeySecret);
		try {
			// 删除Object.
			DeleteObjectsResult deleteObjectsResult = client
					.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(bucketUrls));
			deleteObjectsResult.getDeletedObjects();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			client.shutdown();
		}
		return true;
	}
}
