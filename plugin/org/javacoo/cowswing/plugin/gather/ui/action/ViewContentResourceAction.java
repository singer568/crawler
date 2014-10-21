package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JTable;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentResourceBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerContentResourceTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.content.ContentResourceListPanel;
import org.javacoo.cowswing.ui.view.dialog.ViewDialog;
import org.springframework.stereotype.Component;


/**
 * 查看内容
 *@author DuanYong
 *@since 2012-11-4下午9:19:12
 *@version 1.0
 */
@Component("viewContentResourceAction")
public class ViewContentResourceAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="contentResourceListPanel")
	private ContentResourceListPanel contentResourceListPanel;
	/**
	 * 详细信息页面
	 */
	@Resource(name="viewDialog")
	private ViewDialog viewDialog;
	public ViewContentResourceAction(){
		super(LanguageLoader.getString("ContentList.view"),ImageLoader.getImageIcon("CrawlerResource.toolbarView"));
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTable contentResourceTable = contentResourceListPanel.getCrawlerContentResourceTable();
		if(contentResourceTable.getSelectedRow() != -1){
			int row = contentResourceTable.getSelectedRow();
			int col = contentResourceTable.getSelectedColumn();
			contentResourceTable.getValueAt(row, col);
			CrawlerContentResourceTabelModel dataModel = (CrawlerContentResourceTabelModel) contentResourceTable.getModel();
			CrawlerContentResourceBean crawlerContentResourceBean = dataModel.getRowObject(contentResourceTable.getSelectedRow());
			viewDialog.showImage(crawlerContentResourceBean.getPath());
			viewDialog.setVisible(true);
		}
	}

}
