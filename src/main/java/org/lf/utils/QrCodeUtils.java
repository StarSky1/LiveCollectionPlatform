package org.lf.utils;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类
 * 
 * @author sunwill
 *
 */
public class QrCodeUtils {
	private static final Logger logger = LoggerFactory.getLogger(QrCodeUtils.class);
	/**
	 * 生成二维码参数
	 */
	private static final Map<EncodeHintType, Object> HINTS_FOR_ENCODE = new HashMap<EncodeHintType, Object>();
	/**
	 * 允许生成的二维码图片文件类型
	 */
	private static final String[] ALLOW_IMG_TYPES = new String[] { ".png", ".jpg", ".gif" };

	/**
	 * 二维码默认图片类型
	 */
	private static final String DEFAULT_IMG_FORMAT = "png";
	static {
		HINTS_FOR_ENCODE.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		HINTS_FOR_ENCODE.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
	}

	/**
	 * 生成二维码，并以流的形式输出
	 * 
	 * @param content
	 *            二维码内容
	 * @param width
	 *            二维码宽度
	 * @param height
	 *            二维码高度
	 * @param os
	 *            输出流
	 */
	public static void generateQrCodeStream(String content, int width, int height, OutputStream os) {
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, HINTS_FOR_ENCODE);// 生成矩阵
			MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_IMG_FORMAT, os);
			os.flush();
		} catch (Exception e) {
			logger.error("生成二维码出错", e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 
	 * 生成二维码图片，并储存至指定磁盘路径
	 * 
	 * @param content
	 *            二维码内容
	 * @param width
	 *            二维码宽度
	 * @param height
	 *            二维码高度
	 * @param directoryPath
	 *            存储目录文件夹路径(不包含文件名)
	 * @param fileName
	 *            文件名（包含后缀）
	 */
	public static void generateQrCodeFile(String content, int width, int height, String directoryPath, String fileName) {
		try {
			if (StringUtils.isEmpty(directoryPath) || StringUtils.isEmpty(fileName)) {
				throw new IllegalArgumentException("路径不能为空");
			}
			if (!isValidType(fileName)) {
				throw new IllegalArgumentException("文件类型错误");
			}
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, HINTS_FOR_ENCODE);// 生成矩阵
			File imgFile = new File(directoryPath);
			if (!imgFile.exists()) {
				imgFile.mkdirs();
			}
			Path path = FileSystems.getDefault().getPath(directoryPath, fileName);
			MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_IMG_FORMAT, path);
		} catch (Exception e) {
			logger.error("生成二维码出错", e);
		}
	}

	/**
	 * 检查文件后缀名是否合法
	 * 
	 * @param contentType
	 * @return
	 */
	private static boolean isValidType(String contentType) {
		if (StringUtils.isEmpty(contentType)) {
			return false;
		}
		for (String type : ALLOW_IMG_TYPES) {
			if (contentType.toUpperCase().endsWith(type.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
}
