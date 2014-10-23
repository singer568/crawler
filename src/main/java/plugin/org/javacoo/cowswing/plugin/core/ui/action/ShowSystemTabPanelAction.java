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
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.javacoo.cowswing.ui.view.panel.SystemTabPanel;
import org.springframework.stereotype.Component;


/**
 * 显示系统TabPanel
 *@author DuanYong
 *@since 2013-3-6上午10:03:17
 *@version 1.0
 */
@Component("showSystemTabPanelAction")
public class ShowSystemTabPanelAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="pageContainer")
	private PageContainer pageContainer;
	@Resource(name="systemTabPanel")
    private SystemTabPanel systemTabPanel;
	
	public ShowSystemTabPanelAction(){
		super(LanguageLoader.getString("Home.systemInfo"),ImageLoader.getImageIcon("CrawlerResource.toolbarHouse"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(systemTabPanel, systemTabPanel.getPageId());
		//systemTabPanel.init();
	}

	/**
	 * @return the systemTabPanel
	 */
	public SystemTabPanel getSystemTabPanel() {
		return systemTabPanel;
	}
	
}
