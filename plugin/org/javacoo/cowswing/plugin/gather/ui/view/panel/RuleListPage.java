package org.javacoo.cowswing.plugin.gather.ui.view.panel;

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
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleCriteria;
import org.javacoo.cowswing.plugin.gather.ui.action.AddRuleAction;
import org.javacoo.cowswing.plugin.gather.ui.action.DealWithRuleAction;
import org.javacoo.cowswing.plugin.gather.ui.action.DeleteRuleAction;
import org.javacoo.cowswing.plugin.gather.ui.action.ExecuteRuleAction;
import org.javacoo.cowswing.plugin.gather.ui.action.ExportRuleAction;
import org.javacoo.cowswing.plugin.gather.ui.action.ImportRuleAction;
import org.javacoo.cowswing.plugin.gather.ui.action.SaveAsRuleAction;
import org.javacoo.cowswing.plugin.gather.ui.action.StopRuleAction;
import org.javacoo.cowswing.plugin.gather.ui.action.UpdateRuleAction;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerRuleTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.dialog.RuleSettingDialog;
import org.javacoo.cowswing.ui.util.ColumnResizer;
import org.javacoo.cowswing.ui.view.panel.AbstractListPage;
import org.javacoo.persistence.PaginationSupport;
import org.springframework.stereotype.Component;

/**
 * 采集规则列表panel
 * 
 * @author javacoo
 * @since 2012-03-14
 */
@Component("ruleListPage")
public class RuleListPage extends AbstractListPage implements ListSelectionListener {
	private static final long serialVersionUID = 1L;
	/**
	 * 添加采集规则按钮
	 */
	private JButton addButton;
	/**
	 * 另存采集规则按钮
	 */
	private JButton saveAsButton;
	/**
	 * 添加采集规则按钮
	 */
	private JButton modifyButton;
	/**
	 * 删除采集规则按钮
	 */
	private JButton deleteButton;
	/**
	 * 执行采集规则按钮
	 */
	private JButton executeButton;
	/**
	 * 停止执行采集规则按钮
	 */
	private JButton stopButton;
	/**
	 * 导入采集规则按钮
	 */
	private JButton importButton;
	/**
	 * 导出采集规则按钮
	 */
	private JButton exportButton;

	/**
	 * 后续处理按钮
	 */
	private JButton dealWithButton;

	/**
	 * 采集规则Table
	 */
	private JTable crawlerRuleTable;
	/**
	 * 采集规则服务类
	 */
	@Resource(name="crawlerRuleService")
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	
	/**
	 * 添加采集规则Action
	 */
	@Resource(name="addRuleAction")
	private AddRuleAction addRuleAction;
	/**
	 * 另存采集规则Action
	 */
	@Resource(name="saveAsRuleAction")
	private SaveAsRuleAction saveAsRuleAction;
	/**
	 * 修改采集规则Action
	 */
	@Resource(name="updateRuleAction")
	private UpdateRuleAction updateRuleAction;
	/**
	 * 删除采集规则Action
	 */
	@Resource(name="deleteRuleAction")
	private DeleteRuleAction deleteRuleAction;
	/**
	 * 执行采集规则Action
	 */
	@Resource(name="executeRuleAction")
	private ExecuteRuleAction executeRuleAction;
	/**
	 * 停止执行采集规则Action
	 */
	@Resource(name="stopRuleAction")
	private StopRuleAction stopRuleAction;
	/**
	 * 导入采集规则Action
	 */
	@Resource(name="importRuleAction")
	private ImportRuleAction importRuleAction;
	/**
	 * 导出采集规则Action
	 */
	@Resource(name="exportRuleAction")
	private ExportRuleAction exportRuleAction;
	/**
	 * 后续处理Action
	 */
	@Resource(name="dealWithRuleAction")
	private DealWithRuleAction dealWithRuleAction;
	
	/**
	 * 采集设置Dialog
	 */
	@Resource(name="ruleSettingDialog")
	private RuleSettingDialog ruleSettingDialog;

	public RuleListPage() {
		super();
	}
	
	

	@Override
	protected JComponent getTopPane() {
		super.getTopPane();
		buttonBar.add(getAddButton());
		buttonBar.add(getSaveAsButton());
		buttonBar.add(getModifyButton());
		buttonBar.add(getDeleteButton());
		buttonBar.add(getExecuteButton());
		buttonBar.add(getStopButton());
		buttonBar.add(getImportButton());
		buttonBar.add(getExportButton());
		buttonBar.add(getDealWithButton());
		return buttonBar;
	}

	@Override
	protected JComponent getCenterPane() {
		return new JScrollPane(getCrawlerRuleTable());
	}

	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton(addRuleAction);
		}
		return addButton;
	}
	private JButton getSaveAsButton() {
		if (saveAsButton == null) {
			saveAsButton = new JButton(saveAsRuleAction);
		}
		return saveAsButton;
	}
	private JButton getModifyButton() {
		if (modifyButton == null) {
			modifyButton = new JButton(updateRuleAction);
		}
		return modifyButton;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteRuleAction);
		}
		return deleteButton;
	}

	private JButton getExecuteButton() {
		if (executeButton == null) {
			executeButton = new JButton(executeRuleAction);
		}
		return executeButton;
	}
	private JButton getStopButton() {
		if (stopButton == null) {
			stopButton = new JButton(stopRuleAction);
		}
		return stopButton;
	}
	private JButton getImportButton() {
		if (importButton == null) {
			importButton = new JButton(importRuleAction);
		}
		return importButton;
	}
	private JButton getExportButton() {
		if (exportButton == null) {
			exportButton = new JButton(exportRuleAction);
		}
		return exportButton;
	}
	private JButton getDealWithButton() {
		if (dealWithButton == null) {
			dealWithButton = new JButton(dealWithRuleAction);
		}
		return dealWithButton;
	}

	/**
	 * @return crawlerRuleTable
	 * */
	public JTable getCrawlerRuleTable() {
		if (crawlerRuleTable == null) {
			crawlerRuleTable = new JTable();
			CrawlerRuleTabelModel dataModel = new CrawlerRuleTabelModel(
					getColumnNames());
			crawlerRuleTable.setModel(dataModel);
			crawlerRuleTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			crawlerRuleTable.setFillsViewportHeight(true);

			crawlerRuleTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (crawlerRuleTable.getSelectedRow() != -1) {
										CrawlerRuleTabelModel crawlerRuleTabelModel = (CrawlerRuleTabelModel) crawlerRuleTable
												.getModel();
										CrawlerRuleBean crawlerRule = crawlerRuleTabelModel
												.getRowObject(crawlerRuleTable
														.getSelectedRow());
										if (!Constant.TASK_STATUS_RUN
												.equals(crawlerRule.getStatus())) {
											deleteRuleAction.setEnabled(true);
											executeRuleAction.setEnabled(true);
											stopRuleAction.setEnabled(false);
											dealWithRuleAction.setEnabled(false);
											exportRuleAction.setEnabled(true);
											if (crawlerRuleTable
													.getSelectedRows().length > 1) {
												updateRuleAction.setEnabled(false);
												saveAsRuleAction.setEnabled(false);
												dealWithRuleAction.setEnabled(false);
											} else {
												updateRuleAction.setEnabled(true);
												saveAsRuleAction.setEnabled(true);
												dealWithRuleAction.setEnabled(true);
											}
										} else {
											saveAsRuleAction.setEnabled(false);
											updateRuleAction.setEnabled(false);
											deleteRuleAction.setEnabled(false);
											executeRuleAction.setEnabled(false);
											stopRuleAction.setEnabled(true);
											dealWithRuleAction.setEnabled(false);
											exportRuleAction.setEnabled(true);
										}
									} else {
										saveAsRuleAction.setEnabled(false);
										updateRuleAction.setEnabled(false);
										deleteRuleAction.setEnabled(false);
										executeRuleAction.setEnabled(false);
										stopRuleAction.setEnabled(false);
										dealWithRuleAction.setEnabled(false);
										exportRuleAction.setEnabled(false);
									}
								}
							});
						}
					});

			crawlerRuleTable.setAutoCreateRowSorter(true);
			// set data.
			// showData(CalendarUtils.getCurrentMonthInstance());
			setCellEditor(crawlerRuleTable);
		}

		return crawlerRuleTable;
	}

	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("RuleList.id"));
		columnNames.add(LanguageLoader.getString("RuleList.name"));
		columnNames.add(LanguageLoader.getString("RuleList.start_time"));
		columnNames.add(LanguageLoader.getString("RuleList.end_time"));
		columnNames.add(LanguageLoader.getString("RuleList.use_time"));
		columnNames.add(LanguageLoader.getString("RuleList.status"));
		columnNames.add(LanguageLoader.getString("RuleList.total"));

		return columnNames;
	}

	/***/
	private void setCellEditor(JTable table) {
		// Date
		// TableColumn column0 = table.getColumnModel().getColumn(0);
		// SubjectEntity
		// TableColumn columnSubject = table.getColumnModel().getColumn(2);
		// JComboBox subjectComboBox = new JComboBox();
		// SubjectComboBoxModel subjectComboBoxModel = new
		// SubjectComboBoxModel();
		// subjectComboBox.setModel(subjectComboBoxModel);
		// subjectComboBoxModel.setDefaultData(Constant.EXPENSE_KBN);
		// columnSubject.setCellEditor(new DefaultCellEditor(subjectComboBox));
		// subjectComboBox.setRenderer(new SubjectListCellRender());
		//
		// TableColumn columnPassbook = table.getColumnModel().getColumn(6);
		// JComboBox passbookComboBox = new JComboBox();
		// PassbookComboBoxModel2 passbookComboBoxModel = new
		// PassbookComboBoxModel2();
		// passbookComboBox.setModel(passbookComboBoxModel);
		// columnPassbook.setCellEditor(new
		// DefaultCellEditor(passbookComboBox));
		// passbookComboBox.setRenderer(new PassbookListCellRender());

		// memo
		// TableColumn columnMemo = table.getColumnModel().getColumn(7);
		// columnMemo.setCellRenderer(new MemoReceiptTableCellRenderer());
	}

	public void showData(int startIndex,int pageSize) {
		((CrawlerRuleTabelModel) getCrawlerRuleTable().getModel())
				.setData(getData(startIndex, pageSize));
		final JTable table = getCrawlerRuleTable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(table);
			}
		});
	}

	public List<CrawlerRuleBean> getData(int startIndex,int pageSize) {
		CrawlerRuleCriteria criteria = new CrawlerRuleCriteria();
		criteria.setStartIndex(startIndex);
		criteria.setPageSize(pageSize);
		PaginationSupport<CrawlerRuleBean> result = crawlerRuleService.getPaginatedList(criteria,GatherConstant.SQLMAP_ID_LIST_CRAWLER_RULE);
		paginationBar.setPaginationSupport(result);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<CrawlerRuleBean>) result.getData();
	}

	@Override
	public void update(final CowSwingEvent event) {
		logger.info("RuleListPage---响应事件");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (event.getEventType().isAlso(CowSwingEventType.RuleTableChangeEvent)) {
					initData();
				}
				//采集开始显示采集监控面板
				if(CowSwingEventType.TaskStartEvent.equals(event.getEventType())){
					showTabPanel(Constant.SYSTEM_TABPANEL_INDEX_GATHER);
				}
				//入库开始显示入库监控面板
				if(CowSwingEventType.SaveStartEvent.equals(event.getEventType())){
					showTabPanel(Constant.SYSTEM_TABPANEL_INDEX_SAVE);
				}
				//图片处理开始显示图片处理监控面板
				if(CowSwingEventType.DealWithImageStartEvent.equals(event.getEventType())){
					showTabPanel(Constant.SYSTEM_TABPANEL_INDEX_IMAGE);
				}
				//FTP上传开始显示FTP上传监控面板
				if(CowSwingEventType.FtpStartEvent.equals(event.getEventType())){
					showTabPanel(Constant.SYSTEM_TABPANEL_INDEX_FTP);
				}
			}
		});
	}

	@Override
	public String getPageName() {
		return LanguageLoader.getString("CrawlerMainFrame.CrawlRuleList");
	}

	@Override
	public void disposePage() {
		super.disposePage();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		logger.info("ListSelectionEvent---响应事件");
	}


	



	public ICrawlerService<CrawlerRuleBean, CrawlerRuleCriteria> getCrawlerRuleService() {
		return crawlerRuleService;
	}



	public void setCrawlerRuleService(
			ICrawlerService<CrawlerRuleBean, CrawlerRuleCriteria> crawlerRuleService) {
		this.crawlerRuleService = crawlerRuleService;
	}



	public RuleSettingDialog getRuleSettingDialog() {
		return ruleSettingDialog;
	}



	public void setRuleSettingDialog(RuleSettingDialog ruleSettingDialog) {
		this.ruleSettingDialog = ruleSettingDialog;
	}

}
