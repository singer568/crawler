package org.javacoo.cowswing.plugin.tool.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.tool.ui.model.ImageTabelModel;
import org.javacoo.cowswing.plugin.tool.ui.view.panel.ImageListPanel;
import org.javacoo.cowswing.plugin.tool.ui.view.panel.ImageSettingPanel;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.springframework.stereotype.Component;

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
