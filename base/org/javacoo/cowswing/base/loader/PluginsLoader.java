/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.base.loader;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.javacoo.cowswing.base.utils.PropertiesHelper;
import org.javacoo.cowswing.core.context.CowSwingContextData;

/**
 * 加载系统插件
 *@author DuanYong
 *@since 2013-3-5上午11:37:19
 *@version 1.0
 */
public class PluginsLoader {
	private static Logger log = Logger.getLogger(PluginsLoader.class);
	/**爬虫配置参数文件路径*/
	private static String CRAWLER_PLUGINS_PATH = "/plugins/CowSwingPlugins.properties";
	public static void loadPlugins(){
		log.info("开始加载系统插件配置文件:" +CRAWLER_PLUGINS_PATH);
		PropertiesHelper propertiesHelper = new PropertiesHelper();
		Properties properties = propertiesHelper.loadProperties(CRAWLER_PLUGINS_PATH);
		Enumeration<?> propNames = properties.propertyNames();
		String propKey = null;
		String propValue = null;
		Map<String, Map<String, String>> plugins = new HashMap<String, Map<String, String>>();
		while (propNames.hasMoreElements()) {
			propKey = (String) propNames.nextElement();
			propValue = properties.getProperty(propKey);
			log.info("加载插件："+propKey+",插件路径为："+propValue);
			if(StringUtils.isNotBlank(propKey) && StringUtils.isNotBlank(propValue)){
				plugins.put(propKey, propertiesHelper.propertiesToMap(propValue));
			}
		}
		CowSwingContextData.getInstance().setPlugins(plugins);
		log.info("加载系统插件置文件加载完成");
	}
	
}
