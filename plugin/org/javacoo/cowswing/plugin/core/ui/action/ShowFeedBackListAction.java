package org.javacoo.cowswing.plugin.core.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.core.ui.view.panel.FeedBackListPanel;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.springframework.stereotype.Component;

/**
 * 展示反馈列表
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-30 下午5:19:20
 * @version 1.0
 */
@Component("showFeedBackListAction")
public class ShowFeedBackListAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="feedBackListPanel")
    private FeedBackListPanel feedBackListPanel;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	
	public ShowFeedBackListAction(){
		super(LanguageLoader.getString("Core.feedBack.list"),ImageLoader.getImageIcon("CrawlerResource.feedBack"));
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(feedBackListPanel, feedBackListPanel.getPageId());
		feedBackListPanel.init();
	}


}
