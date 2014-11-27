package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.Action;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.RuleListPage;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;


/**
 * 测试采集规则
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-29 上午10:47:36
 * @version 1.0
 */
@Component("testRuleAction")
public class TestRuleAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	/**
	 * 采集规则列表页面
	 */
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;
	public TestRuleAction(){
		super(LanguageLoader.getString("RuleContentSetting.testRule"),ImageLoader.getImageIcon("CrawlerResource.zoom"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ruleListPage.getRuleSettingDialog().init(crawlerMainFrame, Constant.OPTION_TYPE_ADD, LanguageLoader.getString("RuleList.add"));
		ruleListPage.getRuleSettingDialog().setVisible(true);
	}

}
