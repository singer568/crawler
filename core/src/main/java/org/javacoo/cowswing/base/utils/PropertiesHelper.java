package org.javacoo.cowswing.base.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.plugin.core.constant.CoreConstant;

/**
 * 属性文件帮助类
 * 
 * @author javacoo
 * @since 2012-03-26
 */
public class PropertiesHelper {
	private static Logger log = Logger.getLogger(PropertiesHelper.class);
    /**
     * 加载属性配置文件
     * <p>方法说明:</>
     * <li></li>
     * @author DuanYong
     * @since 2013-6-2 上午9:58:36
     * @version 1.0
     * @exception 
     * @param propertiesPath
     * @return
     */
	public Properties loadProperties(String propertiesPath) {
		InputStream is = null;
		Properties properties = new Properties();
		try {
			is = getClass().getResourceAsStream(propertiesPath);
			properties.load(is);
			return properties;
		} catch (IOException e) {
			log.error("加载配置文件失败： [文件名：" + propertiesPath + " ]", e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将制定路径的属性文件转换为map
	 * 
	 * @param propertiesPath
	 *            属性文件路径
	 * @return map
	 */
	public Map<String, String> propertiesToMap(String propertiesPath) {
		Properties properties = loadProperties(propertiesPath);
		return propertiesToMap(propertiesPath,properties);
	}

	/**
	 * 将属性配置转换为map
	 * 
	 * @return map
	 */
	public Map<String, String> propertiesToMap(String path,Properties properties) {
		Map<String, String> propertiesMap = new HashMap<String, String>();
		Enumeration<?> propNames = properties.propertyNames();
		String propKey = null;
		String propValue = null;
		propertiesMap.put(CoreConstant.PLUGIN_PROPERTIES_KY_PATH, path);
		while (propNames.hasMoreElements()) {
			propKey = (String) propNames.nextElement();
			propValue = properties.getProperty(propKey);
			if (!isBlank(propKey)) {
				log.info("=====加载配置项：" + propKey + ",值为：" + propValue);
				propertiesMap.put(propKey, properties.getProperty(propKey));
			}
		}
		return propertiesMap;
	}

	/**
	 * 判断配置参数值是否为空
	 * 
	 * @param value
	 *            配置参数值
	 * @return
	 */
	private boolean isBlank(String value) {
		return StringUtils.isBlank(StringUtils.trim(value));
	}

	/**
	 * 写入属性值
	 * <p>
	 * 方法说明:
	 * </p>
	 * <li></li>
	 * 
	 * @auther DuanYong
	 * @since 2013-3-11 下午2:41:59
	 * @param filePath
	 * @param parameterName
	 * @param parameterValue
	 * @param parameterComments
	 * @return void
	 */
	public static void writeProperties(String filePath, String parameterName,
			String parameterValue, String parameterComments) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, parameterComments);
			log.info("写入属性配置文件:" + filePath + "成功,属性名称：" + parameterName
					+ ",属性值：" + parameterValue + ",属性注释：" + parameterComments);
		} catch (IOException e) {
			log.info("写入属性配置文件:" + filePath + "失败,属性名称：" + parameterName
					+ ",属性值：" + parameterValue + ",属性注释：" + parameterComments);
			e.printStackTrace();
		}
	}
	/**
	 * 写入属性
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-3-11 下午8:22:20
	 * @param filePath
	 * @param propertiesMap
	 * @param parameterComments
	 * @return void
	 */
	public static void writeMapProperties(String filePath,Map<String, String> propertiesMap, String parameterComments){
		Properties prop = new Properties();
		String propertiesPath = Constant.SYSTEM_ROOT_PATH+filePath;
		try {
			InputStream fis = new FileInputStream(propertiesPath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(propertiesPath);
			String key = "";
			for(Iterator<String> it = propertiesMap.keySet().iterator();it.hasNext();){
				key = it.next();
				prop.setProperty(key, propertiesMap.get(key));
				log.info("写入属性配置文件:" + propertiesPath + "成功,属性名称：" + key
						+ ",属性值：" + propertiesMap.get(key));
			}
			prop.store(fos, parameterComments);
		} catch (IOException e) {
			log.info("写入属性配置文件:" + propertiesPath + "失败");
			e.printStackTrace();
		}
	}

	public static Map<String, String> readProperties(String filePath) {
		Map<String, String> propertiesMap = new HashMap<String, String>();
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration<?> en = props.propertyNames();
			String key = null;
			String value = null;
			while (en.hasMoreElements()) {
				key = (String) en.nextElement();
				value = props.getProperty(key);
				if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)){
					propertiesMap.put(key, value);
				}
			}
		} catch (Exception e) {
			log.info("读取属性配置文件:" + filePath + "失败");
			e.printStackTrace();
		}
		return propertiesMap;
	}

	public static void main(String[] args) {
		Map<String, String> propertiesMap =PropertiesHelper.readProperties("F:/CrawlerAppCore.properties");
		System.out.println(propertiesMap.toString());
		PropertiesHelper.writeProperties("F:/CrawlerAppCore.properties", "extractMediaResType", "swf", "说明111");
	}

}
