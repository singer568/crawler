package org.javacoo.cowswing.plugin.core.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.Action;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.main.CowSwingMainFrame;
import org.javacoo.cowswing.plugin.core.ui.dialog.VersionInfoDialog;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



/**
 * 检查更新
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-1-1 下午6:37:27
 * @version 1.0
 */
@Component("updateAction")
public class UpdateAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	
	/**主窗体*/
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	/**版本更新面板*/
	@Resource(name="versionInfoDialog")
	private VersionInfoDialog versionInfoDialog;
	public UpdateAction(){
		super(LanguageLoader.getString("CrawlerMainFrame.Update"),ImageLoader.getImageIcon("CrawlerResource.update_16"));
		putValue(SHORT_DESCRIPTION, LanguageLoader.getString("CrawlerMainFrame.Update"));
		putValue(ACTION_COMMAND_KEY ,LanguageLoader.getString("CrawlerMainFrame.Update"));
		putValue(MNEMONIC_KEY, KeyEvent.VK_U);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		versionInfoDialog.init(crawlerMainFrame, Constant.OPTION_TYPE_ADD, LanguageLoader.getString("Core.version_title"));
		versionInfoDialog.setVisible(true);
	}
	
}
