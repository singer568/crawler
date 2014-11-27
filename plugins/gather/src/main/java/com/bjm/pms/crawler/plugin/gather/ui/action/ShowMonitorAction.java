package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.RuleListPage;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;

/**
 * 显示采集监控
 *@author DuanYong
 *@since 2012-11-23上午9:15:43
 *@version 1.0
 */
@Component("showMonitorAction")
public class ShowMonitorAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;
	public ShowMonitorAction(){
		super(LanguageLoader.getString("RuleList.execute"),ImageLoader.getImageIcon("CrawlerResource.navigatorList"));
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ruleListPage.showTabPanel(Constant.SYSTEM_TABPANEL_INDEX_GATHER);
	}

}
