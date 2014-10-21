package org.javacoo.cowswing.plugin.tool.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.tool.ui.bean.ImageBean;
import org.javacoo.cowswing.plugin.tool.ui.model.ImageTabelModel;
import org.javacoo.cowswing.plugin.tool.ui.view.panel.ImageListPanel;
import org.springframework.stereotype.Component;

/**
 * 删除图片
 *@author DuanYong
 *@since 2012-12-14下午11:02:18
 *@version 1.0
 */
@Component("deleteImageAction")
public class DeleteImageAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private JTable imageTable;
	
	private ImageTabelModel imageTabelModel;
	
	@Resource(name="imageListPanel")
	private ImageListPanel imageListPanel;
	
	public DeleteImageAction(){
		super(LanguageLoader.getString("ToolImage.imageListDelete"),ImageLoader.getImageIcon("CrawlerResource.toolImageListDelete"));
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		imageTable = imageListPanel.getImageTable();
		if(imageTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				imageTabelModel = (ImageTabelModel) imageTable.getModel();
				ImageBean bean = null;
				for(Integer selectRow : imageTable.getSelectedRows()){
					bean = imageTabelModel.getRowObject(selectRow);
					imageTabelModel.removeRow(selectRow,bean.getName());
				}
				imageListPanel.changeState();
			}
		}
	}


}
