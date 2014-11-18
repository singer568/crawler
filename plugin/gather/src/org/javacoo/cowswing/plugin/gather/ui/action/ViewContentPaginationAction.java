package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JTable;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentPaginationBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerContentPaginationTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.content.ContentPaginationListPanel;
import org.javacoo.cowswing.ui.view.dialog.ViewDialog;
import org.springframework.stereotype.Component;


/**
 * 查看内容
 *@author DuanYong
 *@since 2012-11-4下午9:19:12
 *@version 1.0
 */
@Component("viewContentPaginationAction")
public class ViewContentPaginationAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="contentPaginationListPanel")
	private ContentPaginationListPanel contentPaginationListPanel;
	/**
	 * 详细信息页面
	 */
	@Resource(name="viewDialog")
	private ViewDialog viewDialog;
	public ViewContentPaginationAction(){
		super(LanguageLoader.getString("ContentList.view"),ImageLoader.getImageIcon("CrawlerResource.toolbarView"));
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTable contentPaginationTable = contentPaginationListPanel.getCrawlerContentPaginationTable();
		if(contentPaginationTable.getSelectedRow() != -1){
			CrawlerContentPaginationTabelModel dataModel = (CrawlerContentPaginationTabelModel) contentPaginationTable.getModel();
			CrawlerContentPaginationBean crawlerContentPaginationBean = dataModel.getRowObject(contentPaginationTable.getSelectedRow());
			viewDialog.showContent(crawlerContentPaginationBean.getContent());
			viewDialog.setVisible(true);
		}
	}

}
