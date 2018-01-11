package org.lf.utils;

import java.util.Random;

public class NumberUtils {
	private static Random r = new Random();
	
	/**
	 * 随机生成一个正整数数，最大值为max。
	 * NOTICE: max的位数最好超过6位，否则重复的概率较大。
	 */
	public final static int getRandomNum(int max) {
		if (max <= 0) {
			throw new IllegalArgumentException("max必须是一个正整数");
		}
		
		return r.nextInt(max);
	}
	
	/**
	 * 随机生成一个数，取值在min - max之间
	 */
	public final static int getRandomNum(int min, int max) {
		if (min < 0) {
			throw new IllegalArgumentException("min必须是一个正整数");
		}
		if (max <= min) {
			throw new IllegalArgumentException("max必须大于min");
		}
		
		int range = max + 1 - min;
		int random = getRandomNum(range);
		return random + min;
	}
}
