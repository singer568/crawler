package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.Action;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.main.CowSwingMainFrame;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.RuleListPage;
import org.springframework.stereotype.Component;


/**
 * 添加采集规则
 *@author DuanYong
 *@since 2012-11-4下午9:19:12
 *@version 1.0
 */
@Component("addRuleAction")
public class AddRuleAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	/**
	 * 采集规则列表页面
	 */
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;
	public AddRuleAction(){
		super(LanguageLoader.getString("RuleList.add"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleAdd"));
		//快捷键
		putValue(Action.MNEMONIC_KEY, new Integer('O'));
		putValue(Action.SHORT_DESCRIPTION, LanguageLoader.getString("CrawlerMainFrame.AddRuleShortDesc"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ruleListPage.getRuleSettingDialog().init(crawlerMainFrame, Constant.OPTION_TYPE_ADD, LanguageLoader.getString("RuleList.add"));
		ruleListPage.getRuleSettingDialog().setVisible(true);
	}

}
