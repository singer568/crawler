/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.scheduler.ui.view.panel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.service.ICrawlerService;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.scheduler.constant.SchedulerConstant;
import org.javacoo.cowswing.plugin.scheduler.service.beans.SchedulerBean;
import org.javacoo.cowswing.plugin.scheduler.service.beans.SchedulerCriteria;
import org.javacoo.cowswing.plugin.scheduler.ui.action.AddSchedulerAction;
import org.javacoo.cowswing.plugin.scheduler.ui.action.DeleteSchedulerAction;
import org.javacoo.cowswing.plugin.scheduler.ui.action.ExecuteSchedulerAction;
import org.javacoo.cowswing.plugin.scheduler.ui.action.StopSchedulerAction;
import org.javacoo.cowswing.plugin.scheduler.ui.action.UpdateSchedulerAction;
import org.javacoo.cowswing.plugin.scheduler.ui.model.CrawlerConfigSchedulerTableModel;
import org.javacoo.cowswing.plugin.scheduler.ui.view.dialog.SchedulerSettingDialog;
import org.javacoo.cowswing.ui.util.ColumnResizer;
import org.javacoo.cowswing.ui.view.panel.AbstractListPage;
import org.javacoo.persistence.PaginationSupport;
import org.springframework.stereotype.Component;

/**
 * 定时任务列表
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 下午1:24:48
 * @version 1.0
 */
@Component("schedulerListPage")
public class SchedulerListPage extends AbstractListPage{
	private static final long serialVersionUID = 1L;
	/**
	 * 添加定时任务配置信息按钮
	 */
	private JButton addButton;
	/**
	 * 修改定时任务配置信息按钮
	 */
	private JButton modifyButton;
	/**
	 * 删除定时任务配置信息按钮
	 */
	private JButton deleteButton;

	/**
	 * 执行定时任务配置信息按钮
	 */
	private JButton executeButton;

	/**
	 * 停止定时任务配置信息按钮
	 */
	private JButton stopButton;
	/**
	 * 添加定时任务配置信息Action
	 */
	@Resource(name="addSchedulerAction")
	private AddSchedulerAction addSchedulerAction;
	/**
	 * 修改定时任务配置信息Action
	 */
	@Resource(name="updateSchedulerAction")
	private UpdateSchedulerAction updateSchedulerAction;
	/**
	 * 删除定时任务配置信息Action
	 */
	@Resource(name="deleteSchedulerAction")
	private DeleteSchedulerAction deleteSchedulerAction;
	/**
	 * 执行定时任务配置信息Action
	 */
	@Resource(name="executeSchedulerAction")
	private ExecuteSchedulerAction executeSchedulerAction;
	/**
	 * 停止定时任务配置信息Action
	 */
	@Resource(name="stopSchedulerAction")
	private StopSchedulerAction stopSchedulerAction;
	/**
	 * 定时任务配置Table
	 */
	private JTable crawlerSchedulerTable;
	/**
	 * 定时任务配置服务类
	 */
	@Resource(name="crawlerSchedulerService")
	private ICrawlerService<SchedulerBean,SchedulerCriteria> crawlerSchedulerService;
	
	/**
	 * 定时任务配置信息设置Dialog
	 */
	@Resource(name="schedulerSettingDialog")
	private SchedulerSettingDialog schedulerSettingDialog;
	public SchedulerListPage(){
		super();
	}
	
	@Override
	protected JComponent getTopPane() {
		super.getTopPane();
		buttonBar.add(getAddButton());
		buttonBar.add(getModifyButton());
		buttonBar.add(getDeleteButton());
		buttonBar.add(getExecuteButton());
		buttonBar.add(getStopButton());
		return buttonBar;
	}
	
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton(addSchedulerAction);
		}
		return addButton;
	}

	private JButton getModifyButton() {
		if (modifyButton == null) {
			modifyButton = new JButton(updateSchedulerAction);
		}
		return modifyButton;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteSchedulerAction);
		}
		return deleteButton;
	}
	
	private JButton getExecuteButton() {
		if (executeButton == null) {
			executeButton = new JButton(executeSchedulerAction);
		}
		return executeButton;
	}
	
	private JButton getStopButton() {
		if (stopButton == null) {
			stopButton = new JButton(stopSchedulerAction);
		}
		return stopButton;
	}
	
	
	@Override
	protected JComponent getCenterPane() {
		return new JScrollPane(getCrawlerSchedulerConfigTable());
	}
	/**
	 * @return crawlerDataBaseTable
	 * */
	public JTable getCrawlerSchedulerConfigTable() {
		if (crawlerSchedulerTable == null) {
			crawlerSchedulerTable = new JTable();
			final CrawlerConfigSchedulerTableModel dataModel = new CrawlerConfigSchedulerTableModel(
					getColumnNames());
			crawlerSchedulerTable.setModel(dataModel);
			crawlerSchedulerTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			crawlerSchedulerTable.setFillsViewportHeight(true);

			crawlerSchedulerTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (crawlerSchedulerTable.getSelectedRow() != -1) {
										SchedulerBean tempSchedulerBean = null;
										if (crawlerSchedulerTable.getSelectedRows().length > 1) {
											updateSchedulerAction.setEnabled(false);
											executeButton.setEnabled(false);
											stopButton.setEnabled(false);
											deleteSchedulerAction.setEnabled(true);
										} else {
											updateSchedulerAction.setEnabled(true);
											deleteSchedulerAction.setEnabled(true);
											tempSchedulerBean = dataModel.getRowObject(crawlerSchedulerTable.getSelectedRow());
											if(Constant.TASK_STATUS_RUN.equals(tempSchedulerBean.getStatus())){
												deleteSchedulerAction.setEnabled(false);
												executeButton.setEnabled(false);
												updateSchedulerAction.setEnabled(false);
												stopButton.setEnabled(true);
											}else{
												deleteSchedulerAction.setEnabled(true);
												executeButton.setEnabled(true);
												stopButton.setEnabled(false);
											}
										}
										
									} else {
										updateSchedulerAction.setEnabled(false);
										deleteSchedulerAction.setEnabled(false);
										executeButton.setEnabled(false);
										stopButton.setEnabled(false);
									}
								}
							});
						}
					});

			crawlerSchedulerTable.setAutoCreateRowSorter(true);
		}

		return crawlerSchedulerTable;
	}
	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("Scheduler.id"));
		columnNames.add(LanguageLoader.getString("Scheduler.name"));
		columnNames.add(LanguageLoader.getString("Scheduler.startTime"));
		columnNames.add(LanguageLoader.getString("Scheduler.endTime"));
		columnNames.add(LanguageLoader.getString("Scheduler.status"));
		return columnNames;
	}
	public void showData(int startIndex,int pageSize) {
		List<SchedulerBean> dataList = getData(startIndex, pageSize);
		((CrawlerConfigSchedulerTableModel) getCrawlerSchedulerConfigTable().getModel()).setData(dataList);
		final JTable table = getCrawlerSchedulerConfigTable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(table);
			}
		});
		
	}

	public List<SchedulerBean> getData(int startIndex,int pageSize) {
		SchedulerCriteria criteria = new SchedulerCriteria();
		criteria.setStartIndex(startIndex);
		criteria.setPageSize(pageSize);
		PaginationSupport<SchedulerBean> result = crawlerSchedulerService.getPaginatedList(criteria,SchedulerConstant.SQLMAP_ID_LIST_CRAWLER_SCHEDULING);
		paginationBar.setPaginationSupport(result);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<SchedulerBean>) result.getData();
	}
	
	@Override
	public String getPageName() {
		return LanguageLoader.getString("Scheduler.schedulerList");
	}

	@Override
	public void disposePage() {
		super.disposePage();
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.event.CrawlerListener#update(org.javacoo.crawler.event.CrawlerEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		logger.info("SchedulerListPage---响应事件");
		if (event.getEventType().isAlso(CowSwingEventType.SchedulerTableChangeEvent)) {
			initData();
		}
	}

	/**
	 * @return the crawlerSchedulerService
	 */
	public ICrawlerService<SchedulerBean, SchedulerCriteria> getCrawlerSchedulerService() {
		return crawlerSchedulerService;
	}

	/**
	 * @return the schedulerSettingDialog
	 */
	public SchedulerSettingDialog getSchedulerSettingDialog() {
		return schedulerSettingDialog;
	}
	
	

}
