package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JTable;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentCommentBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerContentCommentTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.content.ContentCommentListPanel;
import org.javacoo.cowswing.ui.view.dialog.ViewDialog;
import org.springframework.stereotype.Component;


/**
 * 查看内容
 *@author DuanYong
 *@since 2012-11-4下午9:19:12
 *@version 1.0
 */
@Component("viewContentCommentAction")
public class ViewContentCommentAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="contentCommentListPanel")
	private ContentCommentListPanel contentCommentListPanel;
	/**
	 * 详细信息页面
	 */
	@Resource(name="viewDialog")
	private ViewDialog viewDialog;
	public ViewContentCommentAction(){
		super(LanguageLoader.getString("ContentList.view"),ImageLoader.getImageIcon("CrawlerResource.toolbarView"));
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTable contentCommentTable = contentCommentListPanel.getCrawlerContentCommentTable();
		if(contentCommentTable.getSelectedRow() != -1){
			CrawlerContentCommentTabelModel dataModel = (CrawlerContentCommentTabelModel) contentCommentTable.getModel();
			CrawlerContentCommentBean crawlerContentCommentBean = dataModel.getRowObject(contentCommentTable.getSelectedRow());
			viewDialog.showContent(crawlerContentCommentBean.getContent());
			viewDialog.setVisible(true);
		}
	}

}
