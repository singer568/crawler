package com.bjm.pms.crawler.view.ui.view.bar;


import java.util.List;

import javax.swing.JToolBar;
import javax.swing.Action;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.base.loader.LanguageLoader;

/**
 * 工具栏
 *@author DuanYong
 *@since 2012-11-4下午4:24:32
 *@version 1.0
 */
@Component("appToolBar")
public class AppToolBar extends JToolBar{
	private static final long serialVersionUID = 1L;
	
	
	public AppToolBar() {
		super(LanguageLoader.getString("CrawlerMainFrame.Tool"));
	}

	
	/**
	 * 加载插件工具栏
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-13 下午5:16:22
	 * @param toolBarList
	 * @return void
	 */
	public void loadPluginToolBar(List<Action> toolBarList){
		if(CollectionUtils.isNotEmpty(toolBarList)){
			for(Action action : toolBarList){
				this.add(action);
			}
			addSeparator();
		}
	}

}
