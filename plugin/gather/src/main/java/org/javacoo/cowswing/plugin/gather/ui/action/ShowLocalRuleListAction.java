package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.LocalRuleListPage;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.springframework.stereotype.Component;

/**
 * 展示本地规则列表
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-22 下午5:24:14
 * @version 1.0
 */
@Component("showLocalRuleListAction")
public class ShowLocalRuleListAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="localRuleListPage")
    private LocalRuleListPage ruleListPage;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	
	public ShowLocalRuleListAction(){
		super(LanguageLoader.getString("Local.driveList"),ImageLoader.getImageIcon("CrawlerResource.drive"));
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(ruleListPage, ruleListPage.getPageId());
		ruleListPage.init();
	}

	/**
	 * @return the ruleListPage
	 */
	public LocalRuleListPage getRuleListPage() {
		return ruleListPage;
	}
	

}
