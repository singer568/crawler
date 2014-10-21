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
 * 修改定时任务
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 下午1:46:31
 * @version 1.0
 */
@Component("updateSchedulerAction")
public class UpdateSchedulerAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	@Resource(name="schedulerListPage")
	private SchedulerListPage schedulerListPage;
	public UpdateSchedulerAction(){
    	super(LanguageLoader.getString("Scheduler.update"),ImageLoader.getImageIcon("CrawlerResource.clockEdit"));
    }
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		schedulerListPage.getSchedulerSettingDialog().init(crawlerMainFrame, Constant.OPTION_TYPE_MODIFY, LanguageLoader.getString("Scheduler.update"));
		schedulerListPage.getSchedulerSettingDialog().setVisible(true);
	}
}
