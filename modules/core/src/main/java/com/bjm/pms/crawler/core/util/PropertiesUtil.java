package com.bjm.pms.crawler.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesUtil {
	private static Log log = LogFactory.getLog(PropertiesUtil.class);
	/**
	 * 将文件读取成Properties
	 * @param filePath
	 * @return
	 */
	public static Properties getProFromFilePath(String filePath) {
		File file = new File(filePath);
		Properties prop = new Properties();
		FileInputStream istream = null;
		try {
			istream = new FileInputStream(file.getAbsolutePath());
			prop.load(istream);
		} catch (IOException e) {
			log.error("初始化爬虫配置文件失败： [文件名：" + file.getName() + " ]", e);
			return null;
		} finally {
			try {
				if (istream != null)
					istream.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} 
		return prop;
	}
}
