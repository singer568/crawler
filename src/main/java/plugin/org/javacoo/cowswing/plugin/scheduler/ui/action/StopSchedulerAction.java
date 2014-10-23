/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.scheduler.ui.action;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.commons.beanutils.BeanUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.scheduler.constant.SchedulerConstant;
import org.javacoo.cowswing.plugin.scheduler.core.SchedulerTaskManageSvc;
import org.javacoo.cowswing.plugin.scheduler.domain.Scheduler;
import org.javacoo.cowswing.plugin.scheduler.service.beans.SchedulerBean;
import org.javacoo.cowswing.plugin.scheduler.ui.model.CrawlerConfigSchedulerTableModel;
import org.javacoo.cowswing.plugin.scheduler.ui.view.panel.SchedulerListPage;
import org.springframework.stereotype.Component;

/**
 * 停止定时任务
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 下午5:02:55
 * @version 1.0
 */
@Component("stopSchedulerAction")
public class StopSchedulerAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="schedulerListPage")
	private SchedulerListPage schedulerListPage;
	private JTable schedulerTable;
	private CrawlerConfigSchedulerTableModel crawlerConfigSchedulerTableModel;
	/**计划任务管理服务*/
	@Resource(name="schedulerTaskManageSvc")
	private SchedulerTaskManageSvc schedulerTaskManageSvc;
	
	public StopSchedulerAction(){
    	super(LanguageLoader.getString("Scheduler.stop"),ImageLoader.getImageIcon("CrawlerResource.clockStop"));
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
						tempSchedulerBean.setStatus(Constant.TASK_STATUS_STOP);
						tempSchedulerBean.setEndTime(new Date());
						tempSchedulerBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.SchedulerTableStopEvent));
						schedulerListPage.getCrawlerSchedulerService().update(tempSchedulerBean,SchedulerConstant.SQLMAP_ID_UPDATE_CRAWLER_SCHEDULING);
						scheduler = new Scheduler();
						BeanUtils.copyProperties(scheduler, tempSchedulerBean);
						schedulerTaskManageSvc.stop(scheduler);
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
