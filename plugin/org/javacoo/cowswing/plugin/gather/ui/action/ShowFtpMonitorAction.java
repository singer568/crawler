package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.RuleListPage;
import org.springframework.stereotype.Component;

/**
 * 显示FTP上传监控
 *@author DuanYong
 *@since 2012-11-23上午9:15:43
 *@version 1.0
 */
@Component("showFtpMonitorAction")
public class ShowFtpMonitorAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;
	public ShowFtpMonitorAction(){
		super(LanguageLoader.getString("RuleList.execute"),ImageLoader.getImageIcon("CrawlerResource.navigatorList"));
		
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ruleListPage.showTabPanel(Constant.SYSTEM_TABPANEL_INDEX_FTP);
	}

}
