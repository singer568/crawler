/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.plugin;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JMenu;

import com.bjm.pms.crawler.view.ui.view.navigator.NavigatorItem;


/**
 * 界面插件抽象实现类
 *@author DuanYong
 *@since 2012-12-13下午10:44:22
 *@version 1.0
 */
public abstract class AbstractUiPlugin implements UiPlugin{

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.plugin.UiPlugin#getMenuBarList()
	 */
	@Override
	public final List<JMenu> getMenuBarList() {
		return populateMenuBarList();
	}
	/**
	 * 组装插件菜单栏
	 * <p>方法说明:</p>
	 * 默认为空实现,子类可覆盖此方法
	 * @auther DuanYong
	 * @since 2012-12-13 下午10:51:12
	 * @return
	 * @return List<JMenu>
	 */
	protected List<JMenu> populateMenuBarList(){
		return new ArrayList<JMenu>();
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.plugin.UiPlugin#getToolBarList()
	 */
	@Override
	public final List<Action> getToolBarList() {
		return populateToolBarList();
	}
	/**
	 * 组装插件工具栏
	 * <p>方法说明:</p>
	 * 默认为空实现,子类可覆盖此方法
	 * @auther DuanYong
	 * @since 2012-12-13 下午10:52:30
	 * @return
	 * @return List<Action>
	 */
	protected List<Action> populateToolBarList(){
		return new ArrayList<Action>();
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.plugin.UiPlugin#getNavigatorItemList()
	 */
	@Override
	public final List<NavigatorItem> getNavigatorItemList() {
		return populateNavigatorItemList();
	}
	/**
	 * 组装导航栏
	 * <p>方法说明:</p>
	 * 默认为空实现,子类可覆盖此方法
	 * @auther DuanYong
	 * @since 2012-12-13 下午10:54:23
	 * @return
	 * @return List<NavigatorItem>
	 */
	protected List<NavigatorItem> populateNavigatorItemList(){
		return new ArrayList<NavigatorItem>();
	}
	

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.plugin.UiPlugin#getPluginLevel()
	 */
	@Override
	public final int getPluginLevel() {
		return populatePluginLevel();
	}
	/**
	 * 设置插件级别
	 * <p>方法说明:</p>
	 * 默认为100,值越小,排位越靠前,子类可覆盖此方法
	 * @auther DuanYong
	 * @since 2012-12-13 下午10:56:50
	 * @return
	 * @return int
	 */
	protected int populatePluginLevel(){
		return 100;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(UiPlugin o) {
		int level = getPluginLevel();
		if(level > o.getPluginLevel()){
			return 1;
		}else if(level < o.getPluginLevel()){
			return -1;
		}
		return 0;
	}

}
