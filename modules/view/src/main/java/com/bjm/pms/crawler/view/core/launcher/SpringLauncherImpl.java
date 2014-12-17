/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.launcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bjm.pms.crawler.view.core.context.CowSwingContextData;
import com.bjm.pms.crawler.view.plugin.core.constant.CoreConstant;

/**
 * ILauncher接口Spring实现类,负责加载Spring配置文件
 * 
 * @author DuanYong
 * @since 2012-11-30上午9:27:56
 * @version 1.0
 */
public class SpringLauncherImpl implements ILauncher {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.icrawler.launcher.ILauncher#launch()
	 */
	@Override
	public void launch() {
		Map<String, Map<String, String>> plugins = CowSwingContextData
				.getInstance().getPlugins();
		List<String> activePlugins = new ArrayList<String>();
		activePlugins.add("resources/spring/applicationContext.xml");
		String key = "";
		String[] tempPaths = null;
		if (null != plugins) {
			for (Iterator<String> it = plugins.keySet().iterator(); it
					.hasNext();) {
				key = it.next();
				if (Boolean.valueOf(plugins.get(key).get(
						CoreConstant.PLUGIN_PROPERTIES_KY_ACTIVE))) {
					if (StringUtils.isNotBlank(plugins.get(key).get(
							CoreConstant.PLUGIN_PROPERTIES_KY_BEANXMLPATH))) {
						tempPaths = plugins
								.get(key)
								.get(CoreConstant.PLUGIN_PROPERTIES_KY_BEANXMLPATH)
								.split(",");
						for (String path : tempPaths) {
							activePlugins.add(path);
						}
					}
				}
			}
		}

		int i = 0;
		String[] contextPaths = new String[activePlugins.size()];
		for (String xmlPath : activePlugins) {
			contextPaths[i] = xmlPath;
			i++;
		}
		CowSwingContextData.getInstance().setApplicationContext(
				new ClassPathXmlApplicationContext(contextPaths));
	}

}
