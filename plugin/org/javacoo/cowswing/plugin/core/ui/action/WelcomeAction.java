package org.javacoo.cowswing.plugin.core.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.ui.view.dialog.ViewDialog;
import org.springframework.stereotype.Component;

/**
 * 欢迎
 *@author DuanYong
 *@since 2012-11-5上午9:08:15
 *@version 1.0
 */
@Component("welcomeAction")
public class WelcomeAction extends AbstractAction{
	/**
	 * 详细信息页面
	 */
	@Resource(name="viewDialog")
	private ViewDialog viewDialog;
	private static final long serialVersionUID = 1L;
	
	public WelcomeAction(){
		super(LanguageLoader.getString("CrawlerMainFrame.Welcome"),ImageLoader.getImageIcon("CrawlerResource.toolbarHouse"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		viewDialog.setViewSize(400,200);
		viewDialog.showContent(LanguageLoader.getString("Core.about"));
		viewDialog.setVisible(true);
	}

}
