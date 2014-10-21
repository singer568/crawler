/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.base.loader;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.javacoo.cowswing.base.constant.Config;
import org.javacoo.cowswing.base.utils.PropertiesHelper;

/**
 * 加载配置参数
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-2 下午12:53:35
 * @version 1.0
 */
public class ConfigLoader {
	protected static Logger log = Logger.getLogger(ConfigLoader.class);
	/**爬虫配置参数文件路径*/
	private static String CRAWLER_APP_PROPERTIES_PATH = "/CowSwingApp.properties";
	private static String classLoaderPath = ConfigLoader.class.getClassLoader().getResource("").getPath();
	
	public static void loadConfig() {
		log.info("开始加载爬虫参数配置文件:" +CRAWLER_APP_PROPERTIES_PATH);
		PropertiesHelper propertiesHelper = new PropertiesHelper();
		Properties properties = propertiesHelper.loadProperties(CRAWLER_APP_PROPERTIES_PATH);
		Enumeration<?> propNames = properties.propertyNames();
		String propKey = null;
		String propValue = null;
		while (propNames.hasMoreElements()) {
			propKey = (String) propNames.nextElement();
			propValue = properties.getProperty(propKey);
			log.info("加载配置："+propKey+",路径为："+propValue);
			if(!isBlank(propKey) && !isBlank(propValue)){
				Config.CRAWLER_CONFIG_RESOURCES_PATH_MAP.put(propKey, propValue);
				Config.CRAWLER_CONFIG_MAP.put(propKey, propertiesHelper.propertiesToMap(propValue));
			}
		}
		Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_CLASS_LOADER_PATH, classLoaderPath);
		
		log.info("爬虫参数配置文件加载完成");
	}
	/**
	 * 判断配置参数值是否为空
	 * @param value 配置参数值
	 * @return
	 */
	private static boolean isBlank(String value){
		return StringUtils.isBlank(StringUtils.trim(value));
	}
}
