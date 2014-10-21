/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.config.GatherConfigSettingPanel;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.springframework.stereotype.Component;

/**
 * 显示采集参数设置
 *@author DuanYong
 *@since 2013-3-12上午10:56:23
 *@version 1.0
 */
@Component("showGatherConfigSettingAction")
public class ShowGatherConfigSettingAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	@Resource(name="gatherConfigSettingPanel")
    private GatherConfigSettingPanel gatherConfigSettingPanel;

	public ShowGatherConfigSettingAction(){
		super(LanguageLoader.getString("Gather.config"),ImageLoader.getImageIcon("CrawlerResource.navigatorSettingCore"));
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(gatherConfigSettingPanel, gatherConfigSettingPanel.getPageId());
		gatherConfigSettingPanel.init();
	}
	

}
