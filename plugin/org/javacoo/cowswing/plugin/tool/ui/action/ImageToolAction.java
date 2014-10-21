/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.tool.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.Action;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.tool.ui.view.panel.ImageListPanel;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.springframework.stereotype.Component;


/**
 * 图片处理工具
 *@author DuanYong
 *@since 2012-12-14下午4:51:13
 *@version 1.0
 */
@Component("imageToolAction")
public class ImageToolAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	@Resource(name="imageListPanel")
    private ImageListPanel imageListPanel;
	public ImageToolAction(){
		super(LanguageLoader.getString("CrawlerMainFrame.toolImage"),ImageLoader.getImageIcon("CrawlerResource.toolbarImage"));
		//快捷键
		putValue(Action.MNEMONIC_KEY, new Integer('I'));
		putValue(Action.SHORT_DESCRIPTION, LanguageLoader.getString("CrawlerMainFrame.toolImage"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(imageListPanel, imageListPanel.getPageId());
	}

}
