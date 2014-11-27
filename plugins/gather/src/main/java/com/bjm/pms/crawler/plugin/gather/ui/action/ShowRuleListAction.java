package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.RuleListPage;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.panel.PageContainer;

/**
 * 展示规则列表
 *@author DuanYong
 *@since 2012-11-4下午9:19:12
 *@version 1.0
 */
@Component("showRuleListAction")
public class ShowRuleListAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="ruleListPage")
    private RuleListPage ruleListPage;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	
	public ShowRuleListAction(){
		super(LanguageLoader.getString("CrawlerMainFrame.CrawlRuleList"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleAdd"));
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(ruleListPage, ruleListPage.getPageId());
		ruleListPage.init();
	}

	/**
	 * @return the ruleListPage
	 */
	public RuleListPage getRuleListPage() {
		return ruleListPage;
	}
	

}
