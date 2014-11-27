package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.RuleListPage;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;

/**
 * 另存采集规则
 *@author DuanYong
 *@since 2012-11-5下午8:59:55
 *@version 1.0
 */
@Component("saveAsRuleAction")
public class SaveAsRuleAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	/**
	 * 采集规则列表页面
	 */
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;
    public SaveAsRuleAction(){
    	super(LanguageLoader.getString("RuleList.saveAs"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleSaveAs"));
    	this.setEnabled(false);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		ruleListPage.getRuleSettingDialog().init(crawlerMainFrame, Constant.OPTION_TYPE_SAVEAS, LanguageLoader.getString("RuleList.saveAs"));
		ruleListPage.getRuleSettingDialog().setVisible(true);
	}

}
