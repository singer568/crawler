package com.bjm.pms.crawler.plugin.core.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.core.ui.dialog.FeedBaskDialog;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;



/**
 * 信息反馈
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-30 下午2:26:20
 * @version 1.0
 */
@Component("feedBackAction")
public class FeedBackAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	
	/**主窗体*/
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	/**反馈面板*/
	@Resource(name="feedBaskDialog")
	private FeedBaskDialog feedBaskDialog;
	public FeedBackAction(){
		super(LanguageLoader.getString("Core.feedBack"),ImageLoader.getImageIcon("CrawlerResource.feedBack"));
		putValue(SHORT_DESCRIPTION, LanguageLoader.getString("Core.feedBack"));
		putValue(ACTION_COMMAND_KEY ,LanguageLoader.getString("Core.feedBack"));
		putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		feedBaskDialog.init(crawlerMainFrame, Constant.OPTION_TYPE_ADD, LanguageLoader.getString("Core.feedBack"));
		feedBaskDialog.setVisible(true);
	}
	
}
