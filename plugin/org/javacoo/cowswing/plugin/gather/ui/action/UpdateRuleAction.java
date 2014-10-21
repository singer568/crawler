package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.main.CowSwingMainFrame;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.RuleListPage;
import org.springframework.stereotype.Component;

/**
 * 更新采集规则
 *@author DuanYong
 *@since 2012-11-5下午8:59:55
 *@version 1.0
 */
@Component("updateRuleAction")
public class UpdateRuleAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	/**
	 * 采集规则列表页面
	 */
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;
    public UpdateRuleAction(){
    	super(LanguageLoader.getString("RuleList.edit"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleEdit"));
    	this.setEnabled(false);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		ruleListPage.getRuleSettingDialog().init(crawlerMainFrame, Constant.OPTION_TYPE_MODIFY, LanguageLoader.getString("RuleList.edit"));
		ruleListPage.getRuleSettingDialog().setVisible(true);
	}

}
