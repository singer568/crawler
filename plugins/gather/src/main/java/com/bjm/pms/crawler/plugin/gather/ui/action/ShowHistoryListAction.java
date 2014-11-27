package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.HistoryListPage;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.panel.PageContainer;

/**
 * 展示采集历史列表
 *@author DuanYong
 *@since 2012-11-4下午9:19:12
 *@version 1.0
 */
@Component("showHistoryListAction")
public class ShowHistoryListAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="historyListPage")
    private HistoryListPage historyListPage;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	
	public ShowHistoryListAction(){
		super(LanguageLoader.getString("CrawlerMainFrame.CrawlHistoryList"),ImageLoader.getImageIcon("CrawlerResource.navigatorList"));
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(historyListPage, historyListPage.getPageId());
		historyListPage.init();
	}

}
