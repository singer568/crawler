package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.ContentListPage;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.springframework.stereotype.Component;

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
