/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.scheduler.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.main.CowSwingMainFrame;
import org.javacoo.cowswing.plugin.scheduler.ui.view.panel.SchedulerListPage;
import org.springframework.stereotype.Component;

/**
 * 添加定时任务
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 下午1:43:47
 * @version 1.0
 */
@Component("addSchedulerAction")
public class AddSchedulerAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	@Resource(name="schedulerListPage")
	private SchedulerListPage schedulerListPage;
	public AddSchedulerAction(){
    	super(LanguageLoader.getString("Scheduler.add"),ImageLoader.getImageIcon("CrawlerResource.clockAdd"));
    	putValue(SHORT_DESCRIPTION, LanguageLoader.getString("Scheduler.add"));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		schedulerListPage.getSchedulerSettingDialog().init(crawlerMainFrame, Constant.OPTION_TYPE_ADD, LanguageLoader.getString("Scheduler.add"));
		schedulerListPage.getSchedulerSettingDialog().setVisible(true);
	}

}
