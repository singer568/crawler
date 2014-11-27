package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.ContentListPage;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.panel.PageContainer;

/**
 * 展示采集内容列表
 *@author DuanYong
 *@since 2012-11-4下午9:19:12
 *@version 1.0
 */
@Component("showContentListAction")
public class ShowContentListAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="contentListPage")
    private ContentListPage contentListPage;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	
	public ShowContentListAction(){
		super(LanguageLoader.getString("CrawlerMainFrame.CrawlContentList"),ImageLoader.getImageIcon("CrawlerResource.navigatorList"));
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(contentListPage, contentListPage.getPageId());
		contentListPage.init();
	}

}
