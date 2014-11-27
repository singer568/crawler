package com.bjm.pms.crawler.plugin.gather.ui.view.panel.content;

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
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentPaginationBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentPaginationCriteria;
import com.bjm.pms.crawler.plugin.gather.ui.action.DeleteContentPaginationAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ViewContentPaginationAction;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerContentPaginationTabelModel;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.ui.util.ColumnResizer;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractListPage;

/**
 * 采集内容列表panel
 *@author DuanYong
 *@since 2012-12-4下午9:19:11
 *@version 1.0
 */
@Component("contentPaginationListPanel")
public class ContentPaginationListPanel extends AbstractListPage implements ListSelectionListener {
	private static final long serialVersionUID = 1L;
	/**
	 * 删除内容按钮
	 */
	private JButton deleteButton;
	/**
	 * 查看内容按钮
	 */
	private JButton viewButton;

	/**
	 * 内容Table
	 */
	private JTable crawlerContentPaginationTable;
	/**
	 * 采集内容分页服务类
	 */
	@Resource(name="crawlerContentPaginationService")
	private ICrawlerService<CrawlerContentPaginationBean,CrawlerContentPaginationCriteria> crawlerContentPaginationService;
	/**
	 * 查看内容Action
	 */
	@Resource(name="viewContentPaginationAction")
	private ViewContentPaginationAction viewContentPaginationAction;
	/**
	 * 删除采集内容分页Action
	 */
	@Resource(name="deleteContentPaginationAction")
	private DeleteContentPaginationAction deleteContentPaginationAction;

	/**
	 * 查看内容ID
	 */
	private Integer contentId;

	public ContentPaginationListPanel() {
		super();
	}
	
	

	@Override
	protected JComponent getTopPane() {
		super.getTopPane();
		buttonBar.add(getDeleteButton());
		buttonBar.add(getViewButton());
		return buttonBar;
	}

	@Override
	protected JComponent getCenterPane() {
		return new JScrollPane(getCrawlerContentPaginationTable());
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteContentPaginationAction);
		}
		return deleteButton;
	}

	private JButton getViewButton() {
		if (viewButton == null) {
			viewButton = new JButton(viewContentPaginationAction);
		}
		return viewButton;
	}

	/**
	 * @return crawlerRuleTable
	 * */
	public JTable getCrawlerContentPaginationTable() {
		if (crawlerContentPaginationTable == null) {
			crawlerContentPaginationTable = new JTable();
			CrawlerContentPaginationTabelModel dataModel = new CrawlerContentPaginationTabelModel(
					getColumnNames());
			crawlerContentPaginationTable.setModel(dataModel);
			crawlerContentPaginationTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			crawlerContentPaginationTable.setFillsViewportHeight(true);

			crawlerContentPaginationTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (crawlerContentPaginationTable.getSelectedRow() != -1) {
										deleteButton.setEnabled(true);
										if (crawlerContentPaginationTable.getSelectedRows().length > 1) {
											viewButton.setEnabled(false);
										} else {
											viewButton.setEnabled(true);
										}
									} else {
										deleteButton.setEnabled(false);
										viewButton.setEnabled(false);
									}
								}
							});
						}
					});

			crawlerContentPaginationTable.setAutoCreateRowSorter(true);
		}

		return crawlerContentPaginationTable;
	}

	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("ContentList.id"));
		columnNames.add(LanguageLoader.getString("ContentList.desc"));

		return columnNames;
	}
	public void showContent(Integer contentId){
		this.contentId = contentId;
		initData();
	}
	public void showData(int startIndex,int pageSize) {
		((CrawlerContentPaginationTabelModel) getCrawlerContentPaginationTable().getModel()).setData(getData(startIndex,pageSize));
		final JTable table = getCrawlerContentPaginationTable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(table);
			}
		});
	}

	public List<CrawlerContentPaginationBean> getData(int startIndex,int pageSize) {
		CrawlerContentPaginationCriteria criteria = new CrawlerContentPaginationCriteria();
		criteria.setStartIndex(startIndex);
		criteria.setPageSize(pageSize);
		if(null == this.contentId){
			this.contentId = 0;
		}
		criteria.setContentId(this.contentId);
		PaginationSupport<CrawlerContentPaginationBean> result = crawlerContentPaginationService.getPaginatedList(criteria,GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_PAGINATION);
		paginationBar.setPaginationSupport(result);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<CrawlerContentPaginationBean>) result.getData();
	}

	@Override
	public void update(CowSwingEvent event) {
		logger.info("RuleListPage---响应事件");
		if (event.getEventType().equals(CowSwingEventType.ContentPaginationTableAddEvent) || event.getEventType().equals(CowSwingEventType.ContentPaginationTableDeleteEvent)) {
			initData();
		}
	}

	@Override
	public String getPageName() {
		return LanguageLoader.getString("CrawlerMainFrame.CrawlContentList");
	}

	@Override
	public void disposePage() {
		super.disposePage();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		logger.info("ListSelectionEvent---响应事件");
	}



	public ICrawlerService<CrawlerContentPaginationBean, CrawlerContentPaginationCriteria> getCrawlerContentPaginationService() {
		return crawlerContentPaginationService;
	}



	public void setCrawlerContentPaginationService(
			ICrawlerService<CrawlerContentPaginationBean, CrawlerContentPaginationCriteria> crawlerContentPaginationService) {
		this.crawlerContentPaginationService = crawlerContentPaginationService;
	}



	

}
