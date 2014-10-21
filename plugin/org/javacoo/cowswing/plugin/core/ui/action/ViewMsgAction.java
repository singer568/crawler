package org.javacoo.cowswing.plugin.core.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JTable;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.core.ui.model.MsgTabelModel;
import org.javacoo.cowswing.plugin.core.ui.view.panel.MsgListPanel;
import org.javacoo.cowswing.ui.view.dialog.ViewDialog;
import org.javacoo.webservice.manager.beans.MsgBean;
import org.springframework.stereotype.Component;

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
