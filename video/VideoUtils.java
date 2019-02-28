package com.zcf.live.video;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

/**
 * @author chenkang 获取视频某帧截图
 *
 */
public final class VideoUtils {

	private static final int THUMB_FRAME = 5;

	private VideoUtils() {
	}

	/**
	 * 获取视频指定帧
	 * 
	 * @author chenkang
	 */
	public static BufferedImage getFrame(File file, int frameNumber) throws IOException, JCodecException {
		Picture picture = FrameGrab.getFrameFromFile(file, frameNumber);
		return AWTUtil.toBufferedImage(picture);
	}

	/**
	 * 获取缩略图
	 * 
	 * @author chenkang
	 */
	public static ByteArrayOutputStream getThumbnail(File file, int limit) throws IOException, JCodecException {
		BufferedImage frameBi = getFrame(file, THUMB_FRAME);
		return ImageUtils.getThumbnail(frameBi, limit);
	}

	/**
	 * 获取缩略图
	 * 
	 * @author chenkang
	 */
	public static ByteArrayOutputStream getThumbnail(File file) throws IOException, JCodecException {
		BufferedImage frameBi = getFrame(file, THUMB_FRAME);
		return ImageUtils.getThumbnail(frameBi);
	}

	public static void main(String[] args) {
		try {
			BufferedImage bufferimage = getFrame(new File("E:/ceshi.mp4"), 10);
			ImageIO.write(bufferimage, "jpg", new File("E:/test.jpg"));
		} catch (IOException | JCodecException e) {
			e.printStackTrace();
			System.out.println("读取异常");
		}
	}
}
