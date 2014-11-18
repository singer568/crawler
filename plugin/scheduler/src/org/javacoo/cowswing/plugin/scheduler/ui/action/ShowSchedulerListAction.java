/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.scheduler.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.scheduler.ui.view.panel.SchedulerListPage;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.springframework.stereotype.Component;

/**
 * 显示定时任务列表
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 下午2:44:33
 * @version 1.0
 */
@Component("showSchedulerListAction")
public class ShowSchedulerListAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="schedulerListPage")
	private SchedulerListPage schedulerListPage;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	
	public ShowSchedulerListAction(){
		super(LanguageLoader.getString("Scheduler.schedulerList"),ImageLoader.getImageIcon("CrawlerResource.clockGo"));
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		pageContainer.addPage(schedulerListPage, schedulerListPage.getPageId());
		schedulerListPage.init();
	}
	/**
	 * @return the schedulerListPage
	 */
	public SchedulerListPage getSchedulerListPage() {
		return schedulerListPage;
	}
	
	

}
