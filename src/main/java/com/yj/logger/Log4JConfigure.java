/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yj.logger;

import com.yj.util.PathUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.FileInputStream;

/**
 * log4j2的配置文件格式为xml和json格式
 * 配置log4j2需要的jar包为：log4j-api-2.11.1.jar log4j-core-2.11.1.jar
 *
 */
public class Log4JConfigure {

    public static Logger LOGINFO = null;
    public static Logger LOGERROR = null;

    static {
        //初始化log4j配置文件
        //Log4JConfigure.initLog4JConfigure();
        //获得log4j对象
        LOGINFO = (Logger) LogManager.getLogger("LogInfo");
        LOGERROR = (Logger) LogManager.getLogger("LogError");
    }

    public static void initLog4JConfigure() {
        try {
            //String log4j2_xml_path = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "log4j2.xml";
            String log4j2_xml_path = PathUtil.getWebClassPath() + "log4j2.xml";
            System.out.println("==>"+log4j2_xml_path);
            File file = new File(log4j2_xml_path);
            if (file.exists()) {
                System.out.println("log4j2.xml配置文件存在，其路径为：" + file.getCanonicalPath());
                ConfigurationSource source = new ConfigurationSource(new FileInputStream(file), file);
                Configurator.initialize(null, source);
            } else {
                System.out.println("log4j2.xml配置文件不存在，log4j2初始化失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
