package org.lf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.yj.logger.Log4JConfigure.LOGERROR;
import static com.yj.logger.Log4JConfigure.LOGINFO;


public class BaseProperties {
	private static Properties p;

	static {
		InputStream inputStream = BaseProperties.class.getClassLoader().getResourceAsStream("config.properties");
		p = new Properties();
		try {
			p.load(inputStream);
		} catch (Exception e) {
			LOGINFO.error("读取配置文件出错", e);
			LOGERROR.error("读取配置文件出错", e);
			throw new RuntimeException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGINFO.error("读取配置文件出错", e);
					LOGERROR.error("读取配置文件出错", e);
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
