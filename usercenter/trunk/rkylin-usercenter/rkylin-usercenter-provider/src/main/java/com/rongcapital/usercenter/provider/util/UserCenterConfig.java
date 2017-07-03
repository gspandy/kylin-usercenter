package com.rongcapital.usercenter.provider.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.apache.log4j.PropertyConfigurator;


import java.io.InputStream;
import java.util.Properties;


/**
 * 
 * Description:
 * @author: bihf
 * @CreateDate: 2016年1月11日
 * @version: V1.0
 */
public class UserCenterConfig extends Thread {
    private static final Log logger = LogFactory.getLog(UserCenterConfig.class.getName());

    private static Properties UserCenterConfig = null;
    private static UserCenterConfig instance = new UserCenterConfig();
//	private static DBCon dbcon = null;

    public static UserCenterConfig getInstance() {
        return instance;
    }

    private UserCenterConfig() {
        UserCenterConfig = new Properties();
        loadConfig();
//		start();  //TODO 上线时需取消注释，启动线程
    }

    public void loadConfig() {
        logger.debug("Reading config file.");
        synchronized (UserCenterConfig) {
            InputStream conf = null;
            try {
                conf = getClass().getResourceAsStream("/usercenterConf.properties");
                UserCenterConfig.load(conf);
               // PropertyConfigurator.configure(UserCenterConfig);
            } catch (Exception ex2) {
                logger.fatal("Connot load configuration file. [server.properties], see the following errors...");
                logger.fatal(ex2);
            } finally {
                try {
                    conf.close();
                } catch (Exception ex1) {
                    logger.fatal(ex1);
                }
            }
        }
        logger.debug("Reading config file done.");
    }

    public void run() {
        while (!UserCenterConfig.getProperty("ServerStop").equals("0")) {
            try {
                sleep(300000);//隔5分钟重读配置文件
            } catch (Exception ex1) {
                logger.fatal(ex1);
            }
            loadConfig();
        }
        logger.debug("UserCenterConfig thread is exiting...");
    }


    public static String getProperty(String propName) {
        synchronized (UserCenterConfig) {
            if (UserCenterConfig == null)
                return null;

            return UserCenterConfig.getProperty(propName);
        }
    }

    public static String getProperty(String propName, String defaultValue) {
        String value = getProperty(propName);
        if(value == null) {
            value = defaultValue;
        }
        
        return value;
    }

    public static boolean getBoolean(String property) {
        try {
            return Boolean.valueOf(getProperty(property)).booleanValue();
        } catch (Exception e) {
            logger.fatal(e);
            return false;
        }
    }

    public static float getFloat(String property) {
        try {
            return Float.valueOf(getProperty(property)).floatValue();
        } catch (Exception e) {
            logger.fatal(e);
            return 0f;
        }
    }

    public static int getInt(String property) {
        try {
            return Integer.valueOf(getProperty(property)).intValue();
        } catch (Exception e) {
            logger.fatal(e);
            return 0;
        }
    }
    
    public static void main(String[] args) {
        System.out.println(UserCenterConfig.get("cfca.seal.http.path"));
    }
}
