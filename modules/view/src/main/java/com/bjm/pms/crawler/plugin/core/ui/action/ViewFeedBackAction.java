package com.bjm.pms.crawler.plugin.core.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.core.ui.model.FeedBackTabelModel;
import com.bjm.pms.crawler.plugin.core.ui.view.panel.FeedBackListPanel;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.dialog.ViewDialog;
import com.bjm.pms.crawler.webservice.manager.beans.FeedBackBean;

/**
 * 查看回复
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-30 下午5:01:07
 * @version 1.0
 */
@Component("viewFeedBackAction")
public class ViewFeedBackAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	/**
	 * 详细信息页面
	 */
	@Resource(name="viewDialog")
	private ViewDialog viewDialog;
	@Resource(name="feedBackListPanel")
	private FeedBackListPanel feedBackListPanel;
	private JTable contentTable;
	private FeedBackTabelModel dataModel;
	
	public ViewFeedBackAction(){
		super(LanguageLoader.getString("Core.feedBack.view"),ImageLoader.getImageIcon("CrawlerResource.feedBack"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		contentTable = feedBackListPanel.getFeedBackTable();
		if(contentTable.getSelectedRow() != -1){
			dataModel = (FeedBackTabelModel)contentTable.getModel();
			FeedBackBean fBean = dataModel.getRowObject(contentTable.getSelectedRow());
			StringBuilder msg = new StringBuilder();
			msg.append(LanguageLoader.getString("Core.feedBack.content")).append("<br>");
			msg.append(fBean.getContent()).append("<br>");
			msg.append(LanguageLoader.getString("Core.feedBack.list.reply")).append("<br>");
			msg.append(fBean.getReply());
			viewDialog.setViewSize(600,400);
			viewDialog.showContent(msg.toString());
			viewDialog.setVisible(true);
		}
	}

}
