/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.ui.plugin;
import java.util.List;

import javax.swing.Action;
import javax.swing.JMenu;

import org.javacoo.cowswing.ui.view.navigator.NavigatorItem;
import org.javacoo.cowswing.ui.view.panel.IPage;

/**
 * UI插件接口
 *@author DuanYong
 *@since 2012-12-13下午4:00:08
 *@version 1.0
 */
public interface UiPlugin extends Comparable<UiPlugin>{
	/**
	 * 取得菜单栏列表
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-13 下午4:06:53
	 * @return List<JMenu>
	 */
	List<JMenu> getMenuBarList();
	/**
	 * 取得工具论列表
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-13 下午4:07:02
	 * @return List<Action>
	 */
	List<Action> getToolBarList();
	/**
	 * 导航列表项
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-13 下午4:14:07
	 * @return List<NavigatorItem>
	 */
	List<NavigatorItem> getNavigatorItemList();
	/**
	 * 取得插件级别
	 * 
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-13 下午10:42:29
	 * @return
	 * @return int
	 */
	int getPluginLevel();
	/**
	 * 取得默认页面
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-10-8 下午11:53:47
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	IPage getDefaultPage();
	
//	List<NavigatorItem> getSystemParamNavigatorItemList();
	
}
