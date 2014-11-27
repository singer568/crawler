package com.bjm.pms.crawler.plugin.tool.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.tool.ui.view.panel.ImageListPanel;
import com.bjm.pms.crawler.plugin.tool.ui.view.panel.ImageSettingPanel;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;

/**
 * 处理图片
 *@author DuanYong
 *@since 2013-1-25下午9:31:28
 *@version 1.0
 */
@Component("dealWithImageAction")
public class DealWithImageAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	@Resource(name="imageListPanel")
	private ImageListPanel imageListPanel;
	
    private ImageSettingPanel imageSettingPanel;
	
	public DealWithImageAction(){
		super(LanguageLoader.getString("ToolImage.imageListDealWith"),ImageLoader.getImageIcon("CrawlerResource.toolImageListDealWith"));
		this.setEnabled(false);
	}
    
	/**
	 * @param imageSettingPanel the imageSettingPanel to set
	 */
	public void setImageSettingPanel(ImageSettingPanel imageSettingPanel) {
		this.imageSettingPanel = imageSettingPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		imageListPanel.dealWith(this.imageSettingPanel.getImageSettingBean());
	}


}
