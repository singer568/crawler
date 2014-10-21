/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.scheduler.ui.view.dialog;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.core.event.CowSwingListener;
import org.javacoo.cowswing.plugin.scheduler.constant.SchedulerConstant;
import org.javacoo.cowswing.plugin.scheduler.service.beans.SchedulerBean;
import org.javacoo.cowswing.plugin.scheduler.ui.model.CrawlerConfigSchedulerTableModel;
import org.javacoo.cowswing.plugin.scheduler.ui.view.panel.SchedulerListPage;
import org.javacoo.cowswing.plugin.scheduler.ui.view.panel.SchedulerSettingPanel;
import org.javacoo.cowswing.ui.view.dialog.AbstractDialog;
import org.springframework.stereotype.Component;

/**
 * 定时任务设置Dialog
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 下午2:42:09
 * @version 1.0
 */
@Component("schedulerSettingDialog")
public class SchedulerSettingDialog extends AbstractDialog implements CowSwingListener{
	private static final long serialVersionUID = 1L;
	@Resource(name="schedulerListPage")
	private SchedulerListPage schedulerListPage;
	@Resource(name="schedulerSettingPanel")
	private SchedulerSettingPanel schedulerSettingPanel;
    private SchedulerBean schedulerBean;
	private String type;
	private Integer schedulerId;
	public SchedulerSettingDialog(){
		super(350,300,true);
	}
	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
			logger.info("初始化定时任务信息参数面板");
			jTabbedPane.addTab(LanguageLoader.getString("Scheduler.setting"), schedulerSettingPanel);
			centerPane = jTabbedPane;
		}
		return centerPane;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.dialog.AbstractDialog#finishButtonActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void finishButtonActionPerformed(ActionEvent event) {
		schedulerBean = new SchedulerBean();
		schedulerBean = schedulerSettingPanel.getData();
		if(Constant.OPTION_TYPE_ADD == this.type){
			schedulerBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.SchedulerTableAddEvent));
			schedulerListPage.getCrawlerSchedulerService().insert(schedulerBean,SchedulerConstant.SQLMAP_ID_INSERT_CRAWLER_SCHEDULING);
		}else{
			schedulerBean.setSchedulerId(this.schedulerId);
			schedulerBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.SchedulerTableUpdateEvent));
			schedulerListPage.getCrawlerSchedulerService().update(schedulerBean,SchedulerConstant.SQLMAP_ID_UPDATE_CRAWLER_SCHEDULING);
		}
		this.dispose();
	}
	
	protected void initData(String type) {
		this.type = type;
		JTable dataBaseTable = schedulerListPage.getCrawlerSchedulerConfigTable();
		if(dataBaseTable.getSelectedRow() != -1 && Constant.OPTION_TYPE_MODIFY == type){
			CrawlerConfigSchedulerTableModel crawlerConfigSchedulerTableModel = (CrawlerConfigSchedulerTableModel)dataBaseTable.getModel();
			schedulerBean = crawlerConfigSchedulerTableModel.getRowObject(dataBaseTable.getSelectedRow());
			this.schedulerId = schedulerBean.getSchedulerId();
		}else{
			schedulerBean = new SchedulerBean();
		}
		fillJTabbedPane();
	}
	public void dispose(){
		super.dispose();
		centerPane = null;
	}
	/**
	 * 填充JTabbedPane值
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-3 下午12:20:32
	 * @return void
	 */
	private void fillJTabbedPane(){
		logger.info("填充JTabbedPane值");
		schedulerSettingPanel.initData(schedulerBean);
	}
}
