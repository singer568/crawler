/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.tool.ui;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.tool.ui.action.ShowImageListAction;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.plugin.AbstractUiPlugin;
import com.bjm.pms.crawler.view.ui.util.MenuUtil;
import com.bjm.pms.crawler.view.ui.view.navigator.ListPane;
import com.bjm.pms.crawler.view.ui.view.navigator.NavigatorItem;
import com.bjm.pms.crawler.view.ui.view.panel.IPage;

/**
 * 图片处理插件
 * 
 * @author DuanYong
 * @since 2012-12-14下午4:44:12
 * @version 1.0
 */
@Component("image")
public class Image extends AbstractUiPlugin {
	@Resource(name="showImageListAction")
	private ShowImageListAction showImageListAction;
	
	protected List<JMenu> populateMenuBarList() {
		List<JMenu> menuList = new ArrayList<JMenu>();
		// 加速器快捷键
		KeyStroke fileNewItemKeyStroke = KeyStroke.getKeyStroke(new Integer('I'), InputEvent.CTRL_MASK);
		// 组装
		Map<Action, KeyStroke> fileActionMap = new HashMap<Action, KeyStroke>();
		fileActionMap.put(showImageListAction, fileNewItemKeyStroke);
		menuList.add(MenuUtil.createMenu(LanguageLoader.getString("CrawlerMainFrame.tool"), "T",fileActionMap));
		return menuList;
	}
	protected List<Action> populateToolBarList() {
		List<Action> actionList = new ArrayList<Action>();
		actionList.add(showImageListAction);
		return actionList;
	}
	
	protected List<NavigatorItem> populateNavigatorItemList() {
		List<NavigatorItem> navigatorList = new ArrayList<NavigatorItem>();
		navigatorList.add(new NavigatorItem(LanguageLoader.getString("CrawlerMainFrame.tool"), ImageLoader.getImageIcon("CrawlerResource.navigatorTool"),getFileFolderPane()));
		return navigatorList;
	}
	
	private ListPane getFileFolderPane() {
		ListPane p = new ListPane();
		p.addItem(LanguageLoader.getString("ToolImage.imageList"), ImageLoader.getImageIcon("CrawlerResource.navigatorItem"),showImageListAction);
		p.setSize(185, 86);
		return p;
	}

	protected int populatePluginLevel() {
		return 4;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.plugin.UiPlugin#getDefaultPage()
	 */
	@Override
	public IPage getDefaultPage() {
		return showImageListAction.getImageListPanel();
	}
}
