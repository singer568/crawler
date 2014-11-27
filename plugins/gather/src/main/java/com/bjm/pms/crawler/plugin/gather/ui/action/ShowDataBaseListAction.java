/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.DataBaseListPage;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.panel.PageContainer;

/**
 * 显示数据库配置列表
 *@author DuanYong
 *@since 2013-2-13下午8:29:10
 *@version 1.0
 */
@Component("showDataBaseListAction")
public class ShowDataBaseListAction extends AbstractAction{
	
	@Resource(name="dataBaseListPage")
    private DataBaseListPage dataBaseListPage;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;

	private static final long serialVersionUID = 1L;
	public ShowDataBaseListAction(){
		super(LanguageLoader.getString("System.DataBaseList"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleAdd"));
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		pageContainer.addPage(dataBaseListPage, dataBaseListPage.getPageId());
		dataBaseListPage.init();
	}
	/**
	 * @return the dataBaseListPage
	 */
	public DataBaseListPage getDataBaseListPage() {
		return dataBaseListPage;
	}

	
}
