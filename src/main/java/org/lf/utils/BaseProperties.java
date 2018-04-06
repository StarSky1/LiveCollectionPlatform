package org.lf.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseProperties {
	private static final Logger logger = LoggerFactory.getLogger(BaseProperties.class);
	private static Properties p;

	static {
		InputStream inputStream = BaseProperties.class.getClassLoader().getResourceAsStream("application.properties");
		p = new Properties();
		try {
			p.load(inputStream);
		} catch (Exception e) {
			logger.error("读取配置文件出错", e);
			throw new RuntimeException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("读取配置文件出错", e);
				}
			}
		}
	}

	public static String getProperty(String key) {
		String value = p.getProperty(key);
		if (value == null) {
			return null;
		} else {
			return value.trim();
		}
	}
}
