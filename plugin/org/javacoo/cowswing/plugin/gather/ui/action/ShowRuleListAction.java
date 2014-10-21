package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.RuleListPage;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.springframework.stereotype.Component;

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
