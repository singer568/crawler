package org.javacoo.cowswing.plugin.gather.ui.view.panel.content;

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

import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.service.ICrawlerService;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentResourceBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentResourceCriteria;
import org.javacoo.cowswing.plugin.gather.ui.action.DeleteContentResourceAction;
import org.javacoo.cowswing.plugin.gather.ui.action.ViewContentResourceAction;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerContentResourceTabelModel;
import org.javacoo.cowswing.ui.util.ColumnResizer;
import org.javacoo.cowswing.ui.view.panel.AbstractListPage;
import org.javacoo.persistence.PaginationSupport;
import org.springframework.stereotype.Component;

/**
 * 采集内容资源列表panel
 *@author DuanYong
 *@since 2012-12-5下午9:09:22
 *@version 1.0
 */
@Component("contentResourceListPanel")
public class ContentResourceListPanel extends AbstractListPage implements ListSelectionListener {
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
	private JTable crawlerContentResourceTable;
	/**
	 * 采集内容资源服务类
	 */
	@Resource(name="crawlerContentResourceService")
	private ICrawlerService<CrawlerContentResourceBean,CrawlerContentResourceCriteria> crawlerContentResourceService;
	/**
	 * 查看内容Action
	 */
	@Resource(name="viewContentResourceAction")
	private ViewContentResourceAction viewContentResourceAction;
	/**
	 * 删除采集内容资源Action
	 */
	@Resource(name="deleteContentResourceAction")
	private DeleteContentResourceAction deleteContentResourceAction;

	/**
	 * 查看内容ID
	 */
	private Integer contentId;

	public ContentResourceListPanel() {
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
		return new JScrollPane(getCrawlerContentResourceTable());
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteContentResourceAction);
		}
		return deleteButton;
	}

	private JButton getViewButton() {
		if (viewButton == null) {
			viewButton = new JButton(viewContentResourceAction);
		}
		return viewButton;
	}

	/**
	 * @return crawlerRuleTable
	 * */
	public JTable getCrawlerContentResourceTable() {
		if (crawlerContentResourceTable == null) {
			crawlerContentResourceTable = new JTable();
			CrawlerContentResourceTabelModel dataModel = new CrawlerContentResourceTabelModel(
					getColumnNames());
			crawlerContentResourceTable.setModel(dataModel);
			crawlerContentResourceTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			crawlerContentResourceTable.setFillsViewportHeight(true);
			crawlerContentResourceTable.setRowHeight(93);
			for(int i = 0 ;i<dataModel.getColumnCount();i++){
				crawlerContentResourceTable.getColumnModel().getColumn(i).setPreferredWidth(100);
			}
			crawlerContentResourceTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (crawlerContentResourceTable.getSelectedRow() != -1) {
										deleteButton.setEnabled(true);
										if (crawlerContentResourceTable.getSelectedRows().length > 1) {
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

			crawlerContentResourceTable.setAutoCreateRowSorter(true);
		}

		return crawlerContentResourceTable;
	}

	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("ContentList.id"));
		columnNames.add(LanguageLoader.getString("ContentList.title"));
		columnNames.add(LanguageLoader.getString("ContentList.resType"));
		columnNames.add(LanguageLoader.getString("ContentList.resPath"));
		columnNames.add(LanguageLoader.getString("ContentList.hasUpload"));
		columnNames.add(LanguageLoader.getString("ContentList.isLocal"));
		columnNames.add(LanguageLoader.getString("ContentList.image"));
		return columnNames;
	}
	public void showContent(Integer contentId){
		this.contentId = contentId;
		initData();
	}
	public void showData(int startIndex,int pageSize) {
		final JTable tempTable = getCrawlerContentResourceTable();
		CrawlerContentResourceTabelModel dataModel = (CrawlerContentResourceTabelModel) tempTable.getModel();
		dataModel.setData(getData(startIndex,pageSize));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(tempTable);
			}
		});
	}

	public List<CrawlerContentResourceBean> getData(int startIndex,int pageSize) {
		CrawlerContentResourceCriteria criteria = new CrawlerContentResourceCriteria();
		criteria.setStartIndex(startIndex);
		criteria.setPageSize(pageSize);
		if(null == this.contentId){
			this.contentId = -1;
		}
		criteria.setContentId(contentId);
		PaginationSupport<CrawlerContentResourceBean> result = crawlerContentResourceService.getPaginatedList(criteria,GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_RESOURCE);
		paginationBar.setPaginationSupport(result);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<CrawlerContentResourceBean>) result.getData();
	}

	@Override
	public void update(CowSwingEvent event) {
		logger.info("ContentResourceListPanel---响应事件");
		if (event.getEventType().equals(CowSwingEventType.ContentResourceTableAddEvent) || event.getEventType().equals(CowSwingEventType.ContentResourceTableDeleteEvent)) {
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



	public ICrawlerService<CrawlerContentResourceBean, CrawlerContentResourceCriteria> getCrawlerContentResourceService() {
		return crawlerContentResourceService;
	}



	public void setCrawlerContentResourceService(
			ICrawlerService<CrawlerContentResourceBean, CrawlerContentResourceCriteria> crawlerContentResourceService) {
		this.crawlerContentResourceService = crawlerContentResourceService;
	}



}
