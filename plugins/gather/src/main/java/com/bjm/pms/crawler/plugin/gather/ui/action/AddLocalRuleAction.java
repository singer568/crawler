package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.Action;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.LocalRuleListPage;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;


/**
 * 添加本地采集规则
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-22 下午5:49:36
 * @version 1.0
 */
@Component("addLocalRuleAction")
public class AddLocalRuleAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	/**
	 * 本地采集规则列表页面
	 */
	@Resource(name="localRuleListPage")
	private LocalRuleListPage ruleListPage;
	public AddLocalRuleAction(){
		super(LanguageLoader.getString("Local.add"),ImageLoader.getImageIcon("CrawlerResource.drive_add"));
		//快捷键
		putValue(Action.MNEMONIC_KEY, new Integer('L'));
		putValue(Action.SHORT_DESCRIPTION, LanguageLoader.getString("Local.add"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ruleListPage.getRuleSettingDialog().init(crawlerMainFrame, Constant.OPTION_TYPE_ADD, LanguageLoader.getString("Local.add"));
		ruleListPage.getRuleSettingDialog().setVisible(true);
	}

}
