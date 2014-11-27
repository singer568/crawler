package com.bjm.pms.crawler.plugin.tool.ui.action;

import java.awt.event.ActionEvent;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.tool.ui.bean.ImageBean;
import com.bjm.pms.crawler.plugin.tool.ui.model.ImageTabelModel;
import com.bjm.pms.crawler.plugin.tool.ui.view.panel.ImageListPanel;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;

/**
 * 删除所有图片
 *@author DuanYong
 *@since 2012-12-14下午11:02:18
 *@version 1.0
 */
@Component("deleteAllImageAction")
public class DeleteAllImageAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private JTable imageTable;
	
	private ImageTabelModel imageTabelModel;
	
	@Resource(name="imageListPanel")
	private ImageListPanel imageListPanel;
	
	public DeleteAllImageAction(){
		super(LanguageLoader.getString("ToolImage.imageListDeleteAll"),ImageLoader.getImageIcon("CrawlerResource.toolImageListDeleteAll"));
	    this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		imageTable = imageListPanel.getImageTable();
		int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("ToolImage.imageListDeleteAllConfirm"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
		if(result == 0){
			imageTabelModel = (ImageTabelModel) imageTable.getModel();
			imageTabelModel.setData(new CopyOnWriteArrayList<ImageBean>());
			imageTabelModel.getImagesCache().clear();
			imageListPanel.changeState();
		}
	}


}
