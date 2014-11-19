/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.scheduler.ui.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.scheduler.constant.SchedulerConstant;
import org.javacoo.cowswing.plugin.scheduler.service.beans.SchedulerBean;
import org.javacoo.cowswing.plugin.scheduler.ui.model.CrawlerConfigSchedulerTableModel;
import org.javacoo.cowswing.plugin.scheduler.ui.view.panel.SchedulerListPage;
import org.springframework.stereotype.Component;

/**
 * 删除定时任务
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 下午1:47:08
 * @version 1.0
 */
@Component("deleteSchedulerAction")
public class DeleteSchedulerAction extends AbstractAction{
	private static final long serialVersionUID = 1L;

	@Resource(name="schedulerListPage")
	private SchedulerListPage schedulerListPage;
	private JTable schedulerTable;
	private CrawlerConfigSchedulerTableModel crawlerConfigSchedulerTableModel;
	public DeleteSchedulerAction(){
    	super(LanguageLoader.getString("Scheduler.delete"),ImageLoader.getImageIcon("CrawlerResource.clockDelete"));
    	this.setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		schedulerTable = schedulerListPage.getCrawlerSchedulerConfigTable();
		if(schedulerTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerConfigSchedulerTableModel = (CrawlerConfigSchedulerTableModel)schedulerTable.getModel();
				List<Integer> schedulerIdList = new ArrayList<Integer>();
				SchedulerBean tempSchedulerBean = null;
				for(Integer selectRow : schedulerTable.getSelectedRows()){
					tempSchedulerBean = crawlerConfigSchedulerTableModel.getRowObject(selectRow);
					schedulerIdList.add(tempSchedulerBean.getSchedulerId());
				}
				//根据数据库ID删除配置
				SchedulerBean schedulerBean = new SchedulerBean();
				schedulerBean.setSchedulerIdList(schedulerIdList);
				schedulerBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.SchedulerTableDeleteEvent));
				schedulerListPage.getCrawlerSchedulerService().delete(schedulerBean,SchedulerConstant.SQLMAP_ID_DELETE_CRAWLER_SCHEDULING);
			}
		}
	}

}
