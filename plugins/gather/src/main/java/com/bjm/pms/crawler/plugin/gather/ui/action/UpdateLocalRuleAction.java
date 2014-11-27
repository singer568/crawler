package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.LocalRuleListPage;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;

/**
 * 更新采集规则
 *@author DuanYong
 *@since 2012-11-5下午8:59:55
 *@version 1.0
 */
@Component("updateLocalRuleAction")
public class UpdateLocalRuleAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	/**
	 * 采集规则列表页面
	 */
	@Resource(name="localRuleListPage")
	private LocalRuleListPage ruleListPage;
    public UpdateLocalRuleAction(){
    	super(LanguageLoader.getString("RuleList.edit"),ImageLoader.getImageIcon("CrawlerResource.drive_edit"));
    	this.setEnabled(false);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		ruleListPage.getRuleSettingDialog().init(crawlerMainFrame, Constant.OPTION_TYPE_MODIFY, LanguageLoader.getString("RuleList.edit"));
		ruleListPage.getRuleSettingDialog().setVisible(true);
	}

}
