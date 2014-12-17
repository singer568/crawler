/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.base.loader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.base.utils.PropertiesHelper;
import com.bjm.pms.crawler.view.core.context.CowSwingContextData;

/**
 * 加载系统插件 将插件的配置信息加载在内存，同时将插件需要的jar包加到classpath中
 * 
 * @author DuanYong
 * @since 2013-3-5上午11:37:19
 * @version 1.0
 */
public class PluginsLoader {
	private static Logger log = Logger.getLogger(PluginsLoader.class);
	/** 爬虫配置参数文件路径 */
	private static String CRAWLER_PLUGINS_PATH = "/plugins";

	public static void loadPlugins() {
		log.info("开始加载系统插件");
		// key=插件目录名称
		// value=插件的绝对路径
		Map<String, String> pluginMap = new HashMap<String, String>();
		findPlugins(pluginMap);

		log.debug("===获取的插件为：" + pluginMap);

		Iterator<String> pluginNames = pluginMap.keySet().iterator();
		PropertiesHelper propertiesHelper = new PropertiesHelper();
		String key = null;
		String value = null;

		Map<String, Map<String, String>> plugins = new HashMap<String, Map<String, String>>();
		while (pluginNames.hasNext()) {
			key = (String) pluginNames.next();
			value = pluginMap.get(key);
			log.debug("加载插件：" + key + ",插件路径为：" + value);

			plugins.put(
					key,
					propertiesHelper.propertiesToMap(CRAWLER_PLUGINS_PATH + "/"
							+ key + "/Plugin.properties"));
		}

		CowSwingContextData.getInstance().setPlugins(plugins);

		log.debug("===加载的插件配置为：" + plugins);

		loadPluginLib2ClassPath(pluginMap);

		log.info("加载系统插件置文件加载完成");
	}

	/**
	 * 将plugins/lib目录下所有jar全部加载到classpath中
	 * 
	 * @param map
	 */
	private static void loadPluginLib2ClassPath(Map<String, String> map) {
		log.debug("===加载插件相关jar包");
		List<URL> jarPath = new ArrayList<URL>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String libPath = map.get(it.next()) + "/lib";
			File file = new File(libPath);
			if (file.exists() && file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].getName().endsWith(".jar")) {
						try {
							jarPath.add(files[i].toURI().toURL());
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		log.debug("===加载到的jar包为：" + jarPath.toString());

		URLClassLoader sysloader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		Class<? extends URLClassLoader> sysclass = URLClassLoader.class;
		try {
			Method method = sysclass.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);

			for (URL url : jarPath) {
				method.invoke(sysloader, url);
			}
		} catch (Throwable t) {
			log.error("===加载插件jar包出现异常，", t);
			t.printStackTrace();
		}
	}

	private static void findPlugins(Map<String, String> map) {
		URL url = new PluginsLoader().getClass().getResource(
				CRAWLER_PLUGINS_PATH);

		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if (!file.exists() || !file.isDirectory()) {
			return;
		}
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				map.put(files[i].getName(), files[i].getPath());
			}
		}

	}

}
