package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCommentBean;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerContentCommentTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.content.ContentCommentListPanel;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.dialog.ViewDialog;


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
