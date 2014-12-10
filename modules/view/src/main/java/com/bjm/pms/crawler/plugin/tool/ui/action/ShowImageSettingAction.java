package com.bjm.pms.crawler.plugin.tool.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.tool.ui.model.ImageTabelModel;
import com.bjm.pms.crawler.plugin.tool.ui.view.panel.ImageListPanel;
import com.bjm.pms.crawler.plugin.tool.ui.view.panel.ImageSettingPanel;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.panel.PageContainer;

/**
 * 图片预处理
 *@author DuanYong
 *@since 2012-12-14下午10:35:21
 *@version 1.0
 */
@Component("showImageSettingAction")
public class ShowImageSettingAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
    private ImageSettingPanel imageSettingPanel;
	@Resource(name="imageListPanel")
	private ImageListPanel imageListPanel;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	
	public ShowImageSettingAction(){
		super(LanguageLoader.getString("ToolImage.imageListSetting"),ImageLoader.getImageIcon("CrawlerResource.toolImageListSetting"));
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
		imageSettingPanel.init();
		ImageTabelModel imageTabelModel = (ImageTabelModel) imageListPanel.getImageTable().getModel();
		imageSettingPanel.getImageSettingBean().setExampleImagePath(imageTabelModel.getData().get(0).getPath());
		imageSettingPanel.fillComponentData(imageSettingPanel.getImageSettingBean());
		pageContainer.addPage(imageSettingPanel, imageSettingPanel.getPageId());
	}

}
