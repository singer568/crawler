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
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerHistoryBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerHistoryCriteria;
import com.bjm.pms.crawler.plugin.gather.ui.action.DeleteHistoryAction;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerHistoryTabelModel;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.ui.util.ColumnResizer;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractListPage;

/**
 * 采集历史列表panel
 *@author DuanYong
 *@since 2012-12-4下午4:02:35
 *@version 1.0
 */
@Component("historyListPage")
public class HistoryListPage extends AbstractListPage implements ListSelectionListener {
	private static final long serialVersionUID = 1L;
	/**
	 * 删除采集历史按钮
	 */
	private JButton deleteButton;

	/**
	 * 采集规则Table
	 */
	private JTable crawlerRuleHistoryTable;
	/**
	 * 采集历史服务类
	 */
	@Resource(name="crawlerHistoryService")
	private ICrawlerService<CrawlerHistoryBean,CrawlerHistoryCriteria> crawlerHistoryService;
	/**
	 * 删除采集规则Action
	 */
	@Resource(name="deleteHistoryAction")
	private DeleteHistoryAction deleteHistoryAction;

	public HistoryListPage() {
		super();
	}
	
	

	@Override
	protected JComponent getTopPane() {
		super.getTopPane();
		buttonBar.add(getDeleteButton());
		return buttonBar;
	}

	@Override
	protected JComponent getCenterPane() {
		return new JScrollPane(getCrawlerHistoryTable());
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteHistoryAction);
		}
		return deleteButton;
	}

	/**
	 * @return crawlerRuleTable
	 * */
	public JTable getCrawlerHistoryTable() {
		if (crawlerRuleHistoryTable == null) {
			crawlerRuleHistoryTable = new JTable();
			CrawlerHistoryTabelModel dataModel = new CrawlerHistoryTabelModel(
					getColumnNames());
			crawlerRuleHistoryTable.setModel(dataModel);
			crawlerRuleHistoryTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			crawlerRuleHistoryTable.setFillsViewportHeight(true);

			crawlerRuleHistoryTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (crawlerRuleHistoryTable.getSelectedRow() != -1) {
										deleteHistoryAction.setEnabled(true);
									} else {
										deleteHistoryAction.setEnabled(false);
									}
								}
							});
						}
					});

			crawlerRuleHistoryTable.setAutoCreateRowSorter(true);
		}

		return crawlerRuleHistoryTable;
	}

	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("RuleHistoryList.id"));
		columnNames.add(LanguageLoader.getString("RuleHistoryList.title"));
		columnNames.add(LanguageLoader.getString("RuleHistoryList.url"));
		columnNames.add(LanguageLoader.getString("RuleHistoryList.date"));

		return columnNames;
	}

	public void showData(int startIndex,int pageSize) {
		((CrawlerHistoryTabelModel) getCrawlerHistoryTable().getModel())
				.setData(getData(startIndex, pageSize));
		final JTable table = getCrawlerHistoryTable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(table);
			}
		});
	}

	public List<CrawlerHistoryBean> getData(int startIndex,int pageSize) {
		CrawlerHistoryCriteria criteria = new CrawlerHistoryCriteria();
		criteria.setStartIndex(startIndex);
		criteria.setPageSize(pageSize);
		PaginationSupport<CrawlerHistoryBean> result = crawlerHistoryService.getPaginatedList(criteria,GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_HISTORY);
		paginationBar.setPaginationSupport(result);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<CrawlerHistoryBean>) result.getData();
	}

	@Override
	public void update(CowSwingEvent event) {
		logger.info("RuleListPage---响应事件");
		if (event.getEventType().equals(CowSwingEventType.RuleHistoryTableAddEvent) || event.getEventType().equals(CowSwingEventType.RuleHistoryTableDeleteEvent)) {
			initData();
		}
	}

	@Override
	public String getPageName() {
		return LanguageLoader.getString("CrawlerMainFrame.CrawlHistoryList");
	}

	@Override
	public void disposePage() {
		super.disposePage();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		logger.info("ListSelectionEvent---响应事件");
	}



	public ICrawlerService<CrawlerHistoryBean, CrawlerHistoryCriteria> getCrawlerHistoryService() {
		return crawlerHistoryService;
	}



	public void setCrawlerHistoryService(
			ICrawlerService<CrawlerHistoryBean, CrawlerHistoryCriteria> crawlerHistoryService) {
		this.crawlerHistoryService = crawlerHistoryService;
	}


	

    
   


}
