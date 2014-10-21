package org.javacoo.cowswing.plugin.core.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.core.ui.view.panel.MsgListPanel;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.springframework.stereotype.Component;

/**
 * 展示消息列表
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-10-1 下午4:59:05
 * @version 1.0
 */
@Component("showMsgListAction")
public class ShowMsgListAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="msgListPanel")
    private MsgListPanel msgListPanel;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	
	public ShowMsgListAction(){
		super(LanguageLoader.getString("Core.msg.list"),ImageLoader.getImageIcon("CrawlerResource.msgList"));
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(msgListPanel, msgListPanel.getPageId());
		msgListPanel.init();
	}


}
