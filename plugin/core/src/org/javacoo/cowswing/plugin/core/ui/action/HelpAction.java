package org.javacoo.cowswing.plugin.core.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.AbstractAction;

import org.apache.log4j.Logger;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.core.constant.CoreConstant;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 帮助
 *@author DuanYong
 *@since 2012-11-5上午9:14:01
 *@version 1.0
 */
@Component("helpAction")
@Scope("prototype")
public class HelpAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	private HelpSet helpset = null;
	private ClassLoader loader = this.getClass().getClassLoader();
	public HelpAction(){
		super(LanguageLoader.getString("CrawlerMainFrame.Help"),ImageLoader.getImageIcon("CrawlerResource.toolbarHelp"));
		URL url = HelpSet.findHelpSet(loader, "resources/help/help.hs");// 这一句是找到helpset文件
		try {
			helpset = new HelpSet(loader, url);
			logger.info("加载帮助文件成功");
		} catch (HelpSetException e) {
			logger.info("加载帮助文件失败");
			return;
		}
		HelpBroker helpbroker = helpset.createHelpBroker();
		ActionListener listener = new CSH.DisplayHelpFromSource(helpbroker);
		this.putValue(Constant.ACTION_LISTENER, listener);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
