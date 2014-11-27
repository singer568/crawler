package com.bjm.pms.crawler.plugin.core.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.core.ui.model.MsgTabelModel;
import com.bjm.pms.crawler.plugin.core.ui.view.panel.MsgListPanel;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.dialog.ViewDialog;
import com.bjm.pms.crawler.webservice.manager.beans.MsgBean;

/**
 * 查看消息
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-10-1 下午4:54:32
 * @version 1.0
 */
@Component("viewMsgAction")
public class ViewMsgAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	/**
	 * 详细信息页面
	 */
	@Resource(name="viewDialog")
	private ViewDialog viewDialog;
	@Resource(name="msgListPanel")
	private MsgListPanel msgListPanel;
	private JTable contentTable;
	private MsgTabelModel dataModel;
	
	public ViewMsgAction(){
		super(LanguageLoader.getString("Core.msg.view"),ImageLoader.getImageIcon("CrawlerResource.msgView"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		contentTable = msgListPanel.getMsgTable();
		if(contentTable.getSelectedRow() != -1){
			dataModel = (MsgTabelModel)contentTable.getModel();
			MsgBean fBean = dataModel.getRowObject(contentTable.getSelectedRow());
			viewDialog.setViewSize(600,400);
			viewDialog.showContent(fBean.getMsgContent());
			viewDialog.setVisible(true);
		}
	}

}
