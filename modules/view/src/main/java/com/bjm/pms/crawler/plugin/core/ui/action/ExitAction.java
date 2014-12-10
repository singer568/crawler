package com.bjm.pms.crawler.plugin.core.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;



/**
 * 退出
 *@author DuanYong
 *@since 2012-11-4下午4:59:08
 *@version 1.0
 */
@Component("exitAction")
public class ExitAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame cowSwingMainFrame;
	public ExitAction(){
		super(LanguageLoader.getString("CrawlerMainFrame.Exit"),ImageLoader.getImageIcon("CrawlerResource.toolbarClose"));
		putValue(SHORT_DESCRIPTION, LanguageLoader.getString("CrawlerMainFrame.Exit"));
		putValue(ACTION_COMMAND_KEY ,LanguageLoader.getString("CrawlerMainFrame.Exit"));
		putValue(MNEMONIC_KEY, KeyEvent.VK_X);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		cowSwingMainFrame.closeMainFrame();
	}
}
