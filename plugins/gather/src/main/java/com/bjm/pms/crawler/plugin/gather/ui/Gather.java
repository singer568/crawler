/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui;

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

import com.bjm.pms.crawler.plugin.gather.ui.action.AddDataBaseAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.AddDiyDataAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.AddFtpAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.AddLocalRuleAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.AddRuleAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowContentListAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowDataBaseListAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowDealWithImageMonitorAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowDiyDataListAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowFtpListAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowFtpMonitorAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowGatherConfigSettingAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowHistoryListAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowLocalRuleListAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowMonitorAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowRuleListAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ShowSaveMonitorAction;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.plugin.AbstractUiPlugin;
import com.bjm.pms.crawler.view.ui.util.MenuUtil;
import com.bjm.pms.crawler.view.ui.view.navigator.ListPane;
import com.bjm.pms.crawler.view.ui.view.navigator.NavigatorItem;
import com.bjm.pms.crawler.view.ui.view.panel.IPage;

/**
 * 数据采集插件
 *@author DuanYong
 *@since 2012-12-13下午4:18:48
 *@version 1.0
 */
@Component("gather")
public class Gather extends AbstractUiPlugin{
	@Resource(name="addRuleAction")
	private AddRuleAction addRuleAction;
	@Resource(name="addLocalRuleAction")
	private AddLocalRuleAction addLocalRuleAction;
	@Resource(name="showRuleListAction")
	private ShowRuleListAction showRuleListAction;
	@Resource(name="showLocalRuleListAction")
	private ShowLocalRuleListAction showLocalRuleListAction;
	@Resource(name="showHistoryListAction")
	private ShowHistoryListAction showHistoryListAction;
	@Resource(name="showContentListAction")
	private ShowContentListAction showContentListAction;
	@Resource(name="showMonitorAction")
	private ShowMonitorAction showMonitorAction;
	@Resource(name="showFtpMonitorAction")
	private ShowFtpMonitorAction showFtpMonitorAction;
	@Resource(name="showSaveMonitorAction")
	private ShowSaveMonitorAction showSaveMonitorAction;
	@Resource(name="showDealWithImageMonitorAction")
	private ShowDealWithImageMonitorAction showDealWithImageMonitorAction;
	@Resource(name="showGatherConfigSettingAction")
	private ShowGatherConfigSettingAction showGatherConfigSettingAction;
	@Resource(name="showDataBaseListAction")
	private ShowDataBaseListAction showDataBaseListAction;
	/**
	 * 添加数据库信息Action
	 */
	@Resource(name="addDataBaseAction")
	private AddDataBaseAction addDataBaseAction;
	@Resource(name="showFtpListAction")
	private ShowFtpListAction showFtpListAction;
	@Resource(name="showDiyDataListAction")
	private ShowDiyDataListAction showDiyDataListAction;
	/**
	 * 添加FTP配置信息Action
	 */
	@Resource(name="addFtpAction")
	private AddFtpAction addFtpAction;
	/**
	 * 添加自定义数据信息Action
	 */
	@Resource(name="addDiyDataAction")
	private AddDiyDataAction addDiyDataAction;

	protected List<JMenu> populateMenuBarList() {
		List<JMenu> menuList = new ArrayList<JMenu>();
		// 加速器快捷键
		KeyStroke ruleNewItemKeyStroke = KeyStroke.getKeyStroke(new Integer('N'), InputEvent.CTRL_MASK);
		// 组装
		Map<Action, KeyStroke> fileActionMap = new HashMap<Action, KeyStroke>();
		// 加速器快捷键
		KeyStroke fileNewItemKeyStroke = KeyStroke.getKeyStroke(new Integer('B'), InputEvent.CTRL_MASK);
		// 加速器快捷键
		KeyStroke ftpItemKeyStroke = KeyStroke.getKeyStroke(new Integer('F'), InputEvent.CTRL_MASK);
		// 加速器快捷键
		KeyStroke diyItemKeyStroke = KeyStroke.getKeyStroke(new Integer('D'), InputEvent.CTRL_MASK);
		// 加速器快捷键
		KeyStroke localRuleNewItemKeyStroke = KeyStroke.getKeyStroke(new Integer('L'), InputEvent.CTRL_MASK);
		// 组装
		fileActionMap.put(addDataBaseAction,fileNewItemKeyStroke);
		fileActionMap.put(addFtpAction,ftpItemKeyStroke);
		fileActionMap.put(addRuleAction,ruleNewItemKeyStroke);
		fileActionMap.put(addDiyDataAction,diyItemKeyStroke);
		fileActionMap.put(addLocalRuleAction,localRuleNewItemKeyStroke);
		menuList.add(MenuUtil.createMenu(LanguageLoader.getString("CrawlerMainFrame.CrawlManager"),"G", fileActionMap));
		return menuList;
	}

	
	protected List<Action> populateToolBarList() {
		List<Action> actionList = new ArrayList<Action>();
		actionList.add(addRuleAction);
		actionList.add(addLocalRuleAction);
		actionList.add(addDataBaseAction);
		actionList.add(addFtpAction);
		actionList.add(addDiyDataAction);
		return actionList;
	}

	
	protected List<NavigatorItem> populateNavigatorItemList() {
		List<NavigatorItem> navigatorList = new ArrayList<NavigatorItem>();
		navigatorList.add(new NavigatorItem(LanguageLoader.getString("CrawlerMainFrame.Setting"), ImageLoader.getImageIcon("CrawlerResource.navigatorSetting"),getSettingPane()));
		navigatorList.add(new NavigatorItem(LanguageLoader.getString("CrawlerMainFrame.CrawlManager"), ImageLoader.getImageIcon("CrawlerResource.navigatorCatch"),getFileFolderPane()));
		navigatorList.add(new NavigatorItem(LanguageLoader.getString("CrawlerMainFrame.Monitor"), ImageLoader.getImageIcon("CrawlerResource.toolbarRuleQuery"),getMonitorPane()));
		return navigatorList;
	}
	
	private ListPane getSettingPane() {
		ListPane p = new ListPane();
		p.addItem(LanguageLoader.getString("System.DataBaseList"),ImageLoader.getImageIcon("CrawlerResource.systemDataBase"),showDataBaseListAction);
		p.addItem(LanguageLoader.getString("System.FtpSettingList"),ImageLoader.getImageIcon("CrawlerResource.ftp"),showFtpListAction);
		p.addItem(LanguageLoader.getString("System.DiyDataSettingList"),ImageLoader.getImageIcon("CrawlerResource.toolbarDiydataList"),showDiyDataListAction);
		p.setSize(185, 86);
		return p;
	}
	
	private ListPane getFileFolderPane() {
		ListPane p = new ListPane();
		p.addItem(LanguageLoader.getString("Gather.config"), ImageLoader.getImageIcon("CrawlerResource.navigatorSettingCore"),showGatherConfigSettingAction);
		p.addItem(LanguageLoader.getString("CrawlerMainFrame.CrawlRuleList"), ImageLoader.getImageIcon("CrawlerResource.navigatorList"),showRuleListAction);
		p.addItem(LanguageLoader.getString("Local.driveList"), ImageLoader.getImageIcon("CrawlerResource.drive"),showLocalRuleListAction);
		p.addItem(LanguageLoader.getString("CrawlerMainFrame.CrawlHistoryList"), ImageLoader.getImageIcon("CrawlerResource.toolbarView"),showHistoryListAction);
		p.addItem(LanguageLoader.getString("CrawlerMainFrame.CrawlContentList"), ImageLoader.getImageIcon("CrawlerResource.navigatorItem"),showContentListAction);
		p.setSize(185, 86);
		return p;
	}
	
	private ListPane getMonitorPane() {
		ListPane p = new ListPane();
		p.addItem(LanguageLoader.getString("CrawlerMainFrame.CrawlMonitor"), ImageLoader.getImageIcon("CrawlerResource.monitor"),showMonitorAction);
		p.addItem(LanguageLoader.getString("CrawlerMainFrame.CrawlSaveMonitor"), ImageLoader.getImageIcon("CrawlerResource.monitor"),showSaveMonitorAction);
		p.addItem(LanguageLoader.getString("CrawlerMainFrame.CrawlDealWithImageMonitor"), ImageLoader.getImageIcon("CrawlerResource.monitor"),showDealWithImageMonitorAction);
		p.addItem(LanguageLoader.getString("CrawlerMainFrame.CrawlFtpMonitor"), ImageLoader.getImageIcon("CrawlerResource.monitor"),showFtpMonitorAction);
		p.setSize(185, 86);
		return p;
	}
	
	protected int populatePluginLevel(){
		return 1;
	}


	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.plugin.UiPlugin#getDefaultPage()
	 */
	@Override
	public IPage getDefaultPage() {
		return showRuleListAction.getRuleListPage();
	}


	

}
