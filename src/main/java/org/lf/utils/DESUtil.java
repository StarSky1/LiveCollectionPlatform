package org.lf.utils;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * DES加密工具类
 * 
 * @author sunwill
 *
 */
public class DESUtil {
	/** 安全密钥 */
	private static final String keyData = "60968A353F23B816D7C5E0568DF371087166041685854e8b97aa93447021e2ed";

	/**
	 * 功能：加密 (UTF-8)
	 * 
	 * @param source
	 *            源字符串
	 * @param charSet
	 *            编码
	 * @return String
	 * @throws UnsupportedEncodingException
	 *             编码异常
	 */
	public static String encrypt(String source) throws UnsupportedEncodingException {
		return encrypt(source, "UTF-8");
	}

	/**
	 * 
	 * 功能：解密 (UTF-8)
	 * 
	 * @param encryptedData
	 *            被加密后的字符串
	 * @return String
	 * @throws UnsupportedEncodingException
	 *             编码异常
	 */
	public static String decrypt(String encryptedData) throws UnsupportedEncodingException {
		return decrypt(encryptedData, "UTF-8");
	}

	/**
	 * 功能：加密
	 * 
	 * @param source
	 *            源字符串
	 * @param charSet
	 *            编码
	 * @return String
	 * @throws UnsupportedEncodingException
	 *             编码异常
	 */
	public static String encrypt(String source, String charSet) throws UnsupportedEncodingException {
		String encrypt = null;
		byte[] ret = encrypt(source.getBytes(charSet));
		encrypt = new String(Base64.encodeBase64(ret));
		return encrypt;
	}

	/**
	 * 
	 * 功能：解密
	 * 
	 * @param encryptedData
	 *            被加密后的字符串
	 * @param charSet
	 *            编码
	 * @return String
	 * @throws UnsupportedEncodingException
	 *             编码异常
	 */
	public static String decrypt(String encryptedData, String charSet) throws UnsupportedEncodingException {
		String descryptedData = null;
		byte[] ret = descrypt(Base64.decodeBase64(encryptedData));
		descryptedData = new String(ret, charSet);
		return descryptedData;
	}

	/**
	 * 加密数据 用生成的密钥加密原始数据
	 * 
	 * @param primaryData
	 *            原始数据
	 * @return byte[]
	 */
	private static byte[] encrypt(byte[] primaryData) {

		/** DES算法要求有一个可信任的随机数源 */
		SecureRandom sr = new SecureRandom();

		/** 正式执行加密操作 */
		byte encryptedData[] = null;
		try {
			/** 使用原始密钥数据创建DESKeySpec对象 */
			DESKeySpec dks = new DESKeySpec(keyData.getBytes());

			/** 创建一个密钥工厂 */
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			/** 用密钥工厂把DESKeySpec转换成一个SecretKey对象 */
			SecretKey key = keyFactory.generateSecret(dks);

			/** Cipher对象实际完成加密操作 */
			Cipher cipher = Cipher.getInstance("DES");

			/** 用密钥初始化Cipher对象 */
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			encryptedData = cipher.doFinal(primaryData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/** 返回加密数据 */
		return encryptedData;
	}

	/**
	 * 用密钥解密数据
	 * 
	 * @param encryptedData
	 *            加密后的数据
	 * @return byte[]
	 */
	private static byte[] descrypt(byte[] encryptedData) {

		/** DES算法要求有一个可信任的随机数源 */
		SecureRandom sr = new SecureRandom();

		/** 正式执行解密操作 */
		byte decryptedData[] = null;
		try {
			/** 使用原始密钥数据创建DESKeySpec对象 */
			DESKeySpec dks = new DESKeySpec(keyData.getBytes());

			/** 创建一个密钥工厂 */
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			/** 用密钥工厂把DESKeySpec转换成一个SecretKey对象 */
			SecretKey key = keyFactory.generateSecret(dks);

			/** Cipher对象实际完成加密操作 */
			Cipher cipher = Cipher.getInstance("DES");

			/** 用密钥初始化Cipher对象 */
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			decryptedData = cipher.doFinal(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedData;
	}

}
