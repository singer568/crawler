package com.bjm.pms.crawler.plugin.gather.ui.view.panel;

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

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.persist.PaginationSupport;
import com.bjm.pms.crawler.plugin.gather.constant.GatherConstant;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleCriteria;
import com.bjm.pms.crawler.plugin.gather.ui.action.AddLocalRuleAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.DeleteLocalRuleAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ExecuteLocalRuleAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.SaveAsLocalRuleAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.StopLocalRuleAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.UpdateLocalRuleAction;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerRuleTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.dialog.LocalRuleSettingDialog;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.ui.util.ColumnResizer;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractListPage;

/**
 * 本地采集规则列表panel
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-22 下午5:22:27
 * @version 1.0
 */
@Component("localRuleListPage")
public class LocalRuleListPage extends AbstractListPage implements ListSelectionListener {
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
	 * 采集规则Table
	 */
	private JTable crawlerRuleTable;
	/**
	 * 采集规则服务类
	 */
	@Resource(name="crawlerRuleService")
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	
	/**
	 * 添加本地采集规则Action
	 */
	@Resource(name="addLocalRuleAction")
	private AddLocalRuleAction addRuleAction;
	/**
	 * 另存本地采集规则Action
	 */
	@Resource(name="saveAsLocalRuleAction")
	private SaveAsLocalRuleAction saveAsRuleAction;
	/**
	 * 修改本地采集规则Action
	 */
	@Resource(name="updateLocalRuleAction")
	private UpdateLocalRuleAction updateRuleAction;
	/**
	 * 删除本地采集规则Action
	 */
	@Resource(name="deleteLocalRuleAction")
	private DeleteLocalRuleAction deleteRuleAction;
	/**
	 * 执行本地采集规则Action
	 */
	@Resource(name="executeLocalRuleAction")
	private ExecuteLocalRuleAction executeRuleAction;
	/**
	 * 停止执行采集规则Action
	 */
	@Resource(name="stopLocalRuleAction")
	private StopLocalRuleAction stopRuleAction;
	
	/**
	 * 采集设置Dialog
	 */
	@Resource(name="localRuleSettingDialog")
	private LocalRuleSettingDialog ruleSettingDialog;

	public LocalRuleListPage() {
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
											if (crawlerRuleTable
													.getSelectedRows().length > 1) {
												updateRuleAction.setEnabled(false);
												saveAsRuleAction.setEnabled(false);
											} else {
												updateRuleAction.setEnabled(true);
												saveAsRuleAction.setEnabled(true);
											}
										} else {
											saveAsRuleAction.setEnabled(false);
											updateRuleAction.setEnabled(false);
											deleteRuleAction.setEnabled(false);
											executeRuleAction.setEnabled(false);
											stopRuleAction.setEnabled(true);
										}
									} else {
										saveAsRuleAction.setEnabled(false);
										updateRuleAction.setEnabled(false);
										deleteRuleAction.setEnabled(false);
										executeRuleAction.setEnabled(false);
										stopRuleAction.setEnabled(false);
									}
								}
							});
						}
					});

			crawlerRuleTable.setAutoCreateRowSorter(true);
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
		criteria.setRuleType(Constant.YES);
		criteria.setPageSize(pageSize);
		PaginationSupport<CrawlerRuleBean> result = crawlerRuleService.getPaginatedList(criteria,GatherConstant.SQLMAP_ID_LIST_CRAWLER_RULE);
		paginationBar.setPaginationSupport(result);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<CrawlerRuleBean>) result.getData();
	}

	@Override
	public void update(final CowSwingEvent event) {
		logger.info("LocalRuleListPage---响应事件"+event.getEventType());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (event.getEventType().isAlso(CowSwingEventType.LocalRuleTableChangeEvent)) {
					initData();
				}
			}
		});
	}

	@Override
	public String getPageName() {
		return LanguageLoader.getString("Local.driveList");
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



	public LocalRuleSettingDialog getRuleSettingDialog() {
		return ruleSettingDialog;
	}



	public void setRuleSettingDialog(LocalRuleSettingDialog ruleSettingDialog) {
		this.ruleSettingDialog = ruleSettingDialog;
	}

}
