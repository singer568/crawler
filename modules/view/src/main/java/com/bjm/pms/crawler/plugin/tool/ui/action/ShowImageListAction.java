package com.bjm.pms.crawler.plugin.tool.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.tool.ui.view.panel.ImageListPanel;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.panel.PageContainer;

/**
 * 待处理图片列表
 *@author DuanYong
 *@since 2012-12-14下午10:35:21
 *@version 1.0
 */
@Component("showImageListAction")
public class ShowImageListAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="imageListPanel")
    private ImageListPanel imageListPanel;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	
	public ShowImageListAction(){
		super(LanguageLoader.getString("ToolImage.imageList"),ImageLoader.getImageIcon("CrawlerResource.toolImageListSetting"));
		putValue(SHORT_DESCRIPTION, LanguageLoader.getString("ToolImage.imageList"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(imageListPanel, imageListPanel.getPageId());
		imageListPanel.init();
	}

	/**
	 * @return the imageListPanel
	 */
	public ImageListPanel getImageListPanel() {
		return imageListPanel;
	}
	

}
