package com.zcf.live.video;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public final class ImageUtils {

	// 缩略图默认长宽限制
	private static final int THUMBNAIL_DEFAULT_LIMIT = 400;

	private ImageUtils() {
	}

	/**
	 * 根据长宽值缩放
	 * 
	 * @author chenkang
	 */
	public static BufferedImage scaleByWh(BufferedImage source, int width, int height) {
		return getBufferedImageLocal(source.getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	/**
	 * 根据长宽限制缩放
	 * <p>
	 * 限制较大的一边
	 * 
	 * @author chenkang
	 */
	public static BufferedImage scaleByWhLimit(BufferedImage source, int limit) {
		int scaleW = -1;
		int scaleH = -1;
		if (source.getWidth() > source.getHeight()) {
			scaleW = limit;
		} else {
			scaleH = limit;
		}
		return scaleByWh(source, scaleW, scaleH);
	}

	/**
	 * 根据比例缩放
	 * 
	 * @author chenkang
	 */
	public static BufferedImage scaleByRatio(BufferedImage source, double ratio) {
		int w = (int) (source.getWidth() * ratio);
		int h = (int) (source.getHeight() * ratio);
		return scaleByWh(source, w, h);
	}

	/**
	 * 将 Image 转为 BufferedImage
	 * 
	 * @author chenkang
	 */
	public static BufferedImage getBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		BufferedImage bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufImage.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();
		return bufImage;
	}

	/**
	 * 将 Image 转为 BufferedImage
	 * 
	 * @author chenkang
	 */
	public static BufferedImage getBufferedImageLocal(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage bufImage = gc.createCompatibleImage(img.getWidth(null), img.getHeight(null));

		Graphics2D g2d = bufImage.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();

		return bufImage;
	}

	/**
	 * 获取缩略图
	 * 
	 * @author chenkang
	 */
	public static ByteArrayOutputStream getThumbnail(BufferedImage sourceBi, int limit) throws IOException {
		// 缩放
		BufferedImage scaledBi = scaleByWhLimit(sourceBi, limit);
		// 压缩
		ByteArrayOutputStream compressedOs = new ByteArrayOutputStream();
		ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
		ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
		jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		jpgWriteParam.setCompressionQuality(0.5f);

		jpgWriter.setOutput(ImageIO.createImageOutputStream(compressedOs));
		jpgWriter.write(null, new IIOImage(scaledBi, null, null), jpgWriteParam);
		return compressedOs;
	}

	/**
	 * 获取缩略图
	 * 
	 * @author chenkang
	 */
	public static ByteArrayOutputStream getThumbnail(BufferedImage sourceBi) throws IOException {
		return getThumbnail(sourceBi, THUMBNAIL_DEFAULT_LIMIT);
	}

	/**
	 * 获取缩略图
	 * 
	 * @author chenkang
	 */
	public static ByteArrayOutputStream getThumbnail(File sourceFile, int limit) throws IOException {

		return getThumbnail(ImageIO.read(sourceFile), limit);
	}

	/**
	 * 获取缩略图
	 * 
	 * @author chenkang
	 */
	public static ByteArrayOutputStream getThumbnail(File source) throws IOException {

		return getThumbnail(ImageIO.read(source));
	}
}
