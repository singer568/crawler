/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.config.GatherConfigSettingPanel;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.panel.PageContainer;

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
