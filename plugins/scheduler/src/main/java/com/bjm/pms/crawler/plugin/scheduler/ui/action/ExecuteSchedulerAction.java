/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.scheduler.ui.action;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.scheduler.constant.SchedulerConstant;
import com.bjm.pms.crawler.plugin.scheduler.core.SchedulerTaskManageSvc;
import com.bjm.pms.crawler.plugin.scheduler.domain.Scheduler;
import com.bjm.pms.crawler.plugin.scheduler.service.beans.SchedulerBean;
import com.bjm.pms.crawler.plugin.scheduler.ui.model.CrawlerConfigSchedulerTableModel;
import com.bjm.pms.crawler.plugin.scheduler.ui.view.panel.SchedulerListPage;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;

/**
 * 执行定时任务
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 下午5:02:55
 * @version 1.0
 */
@Component("executeSchedulerAction")
public class ExecuteSchedulerAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="schedulerListPage")
	private SchedulerListPage schedulerListPage;
	private JTable schedulerTable;
	private CrawlerConfigSchedulerTableModel crawlerConfigSchedulerTableModel;
	/**计划任务管理服务*/
	@Resource(name="schedulerTaskManageSvc")
	private SchedulerTaskManageSvc schedulerTaskManageSvc;
	public ExecuteSchedulerAction(){
    	super(LanguageLoader.getString("Scheduler.execute"),ImageLoader.getImageIcon("CrawlerResource.clockExecute"));
    	this.setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		schedulerTable = schedulerListPage.getCrawlerSchedulerConfigTable();
		if(schedulerTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("Scheduler.execute"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerConfigSchedulerTableModel = (CrawlerConfigSchedulerTableModel)schedulerTable.getModel();
				SchedulerBean tempSchedulerBean = null;
				Scheduler scheduler = null;
				for(Integer selectRow : schedulerTable.getSelectedRows()){
					try {
						tempSchedulerBean = crawlerConfigSchedulerTableModel.getRowObject(selectRow);
						tempSchedulerBean.setStatus(Constant.TASK_STATUS_RUN);
						tempSchedulerBean.setStartTime(new Date());
						tempSchedulerBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.SchedulerTableExecuteEvent));
						schedulerListPage.getCrawlerSchedulerService().update(tempSchedulerBean,SchedulerConstant.SQLMAP_ID_UPDATE_CRAWLER_SCHEDULING);
						scheduler = new Scheduler();
						BeanUtils.copyProperties(scheduler, tempSchedulerBean);
						schedulerTaskManageSvc.start(scheduler);
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		
	}

}
