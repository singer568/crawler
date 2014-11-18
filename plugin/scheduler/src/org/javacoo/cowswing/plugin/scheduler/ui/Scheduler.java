/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.scheduler.ui;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.scheduler.ui.action.AddSchedulerAction;
import org.javacoo.cowswing.plugin.scheduler.ui.action.ShowSchedulerListAction;
import org.javacoo.cowswing.ui.plugin.AbstractUiPlugin;
import org.javacoo.cowswing.ui.util.MenuUtil;
import org.javacoo.cowswing.ui.view.navigator.ListPane;
import org.javacoo.cowswing.ui.view.navigator.NavigatorItem;
import org.javacoo.cowswing.ui.view.panel.IPage;
import org.springframework.stereotype.Component;

/**
 * 定时任务插件
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 下午1:21:50
 * @version 1.0
 */
@Component("scheduler")
public class Scheduler extends AbstractUiPlugin{
	
	@Resource(name="showSchedulerListAction")
	private ShowSchedulerListAction showSchedulerListAction;
	/**
	 * 添加定时任务配置信息Action
	 */
	@Resource(name="addSchedulerAction")
	private AddSchedulerAction addSchedulerAction;
	
	protected List<JMenu> populateMenuBarList() {
		List<JMenu> menuList = new ArrayList<JMenu>();
		// 加速器快捷键
		KeyStroke fileNewItemKeyStroke = KeyStroke.getKeyStroke(new Integer('T'), InputEvent.CTRL_MASK);
		// 组装
		Map<Action, KeyStroke> fileActionMap = new HashMap<Action, KeyStroke>();
		fileActionMap.put(addSchedulerAction,fileNewItemKeyStroke);
		menuList.add(MenuUtil.createMenu(LanguageLoader.getString("Scheduler.scheduler"),"G", fileActionMap));
		return menuList;
	}
	
	protected List<Action> populateToolBarList() {
		List<Action> actionList = new ArrayList<Action>();
		actionList.add(addSchedulerAction);
		return actionList;
	}
	

	public List<NavigatorItem> populateNavigatorItemList() {
		List<NavigatorItem> navigatorList = new ArrayList<NavigatorItem>();
		navigatorList.add(new NavigatorItem(LanguageLoader
				.getString("Scheduler.scheduler"), ImageLoader
				.getImageIcon("CrawlerResource.navigatorTime"),
				getSystemPane()));
		return navigatorList;
	}

	private ListPane getSystemPane() {
		ListPane p = new ListPane();
		p.addItem(LanguageLoader.getString("Scheduler.schedulerList"),ImageLoader.getImageIcon("CrawlerResource.clockGo"),showSchedulerListAction);
		p.setSize(185, 117);
		return p;
	}

	protected int populatePluginLevel() {
		return 3;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.plugin.UiPlugin#getDefaultPage()
	 */
	@Override
	public IPage getDefaultPage() {
		return showSchedulerListAction.getSchedulerListPage();
	}
}
