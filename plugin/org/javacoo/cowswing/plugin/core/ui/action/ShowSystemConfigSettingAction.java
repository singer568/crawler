/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.core.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.core.ui.view.panel.SystemConfigSettingPanel;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.javacoo.cowswing.ui.view.panel.SystemTabPanel;
import org.springframework.stereotype.Component;

/**
 * 显示系统参数设置
 *@author DuanYong
 *@since 2013-3-11下午6:58:43
 *@version 1.0
 */
@Component("showSystemConfigSettingAction")
public class ShowSystemConfigSettingAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="pageContainer")
	private PageContainer pageContainer;
	/**系统TabPanel*/
	@Resource(name="systemTabPanel")
    private SystemTabPanel systemTabPanel;
	@Resource(name="systemConfigSettingPanel")
    private SystemConfigSettingPanel systemConfigSettingPanel;
	
	public ShowSystemConfigSettingAction(){
		super(LanguageLoader.getString("Home.systemInfo"),ImageLoader.getImageIcon("CrawlerResource.toolbarHouse"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(systemConfigSettingPanel, systemConfigSettingPanel.getPageId());
		systemConfigSettingPanel.init();
		systemTabPanel.getTabbedPane().setSelectedIndex(0);
	}
}
