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
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCommentBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCommentCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentPaginationBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentPaginationCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentResourceBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentResourceCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerExtendFieldBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerExtendFieldCriteria;
import com.bjm.pms.crawler.plugin.gather.ui.action.DeleteContentAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.ViewContentAction;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerContentTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.dialog.ContentDialog;
import com.bjm.pms.crawler.view.base.constant.GatherConstant;
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
@Component("contentListPage")
public class ContentListPage extends AbstractListPage implements ListSelectionListener {
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
	private JTable crawlerContentTable;
	/**
	 * 采集内容服务类
	 */
	@Resource(name="crawlerContentService")
	private ICrawlerService<CrawlerContentBean,CrawlerContentCriteria> crawlerContentService;
	/**
	 * 采集内容评论服务类
	 */
	@Resource(name="crawlerContentCommentService")
	private ICrawlerService<CrawlerContentCommentBean,CrawlerContentCommentCriteria> crawlerContentCommentService;
	/**
	 * 采集内容分页服务类
	 */
	@Resource(name="crawlerContentPaginationService")
	private ICrawlerService<CrawlerContentPaginationBean,CrawlerContentPaginationCriteria> crawlerContentPaginationService;
	/**
	 * 采集内容资源服务类
	 */
	@Resource(name="crawlerContentResourceService")
	private ICrawlerService<CrawlerContentResourceBean,CrawlerContentResourceCriteria> crawlerContentResourceService;
	/**扩展字段服务类*/
	@Resource(name="crawlerExtendFieldService")
	private ICrawlerService<CrawlerExtendFieldBean,CrawlerExtendFieldCriteria> crawlerExtendFieldService;
	/**
	 * 删除采集内容Action
	 */
	@Resource(name="deleteContentAction")
	private DeleteContentAction deleteContentAction;
	/**
	 * 查看内容Action
	 */
	@Resource(name="viewContentAction")
	private ViewContentAction viewContentAction;
	/**
	 * 查看内容Dialog
	 */
	@Resource(name="contentDialog")
	private ContentDialog contentDialog;

	public ContentListPage() {
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
		return new JScrollPane(getCrawlerContentTable());
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteContentAction);
		}
		return deleteButton;
	}

	private JButton getViewButton() {
		if (viewButton == null) {
			viewButton = new JButton(viewContentAction);
		}
		return viewButton;
	}
	/**
	 * @return crawlerRuleTable
	 * */
	public JTable getCrawlerContentTable() {
		if (crawlerContentTable == null) {
			crawlerContentTable = new JTable();
			CrawlerContentTabelModel dataModel = new CrawlerContentTabelModel(
					getColumnNames());
			crawlerContentTable.setModel(dataModel);
			crawlerContentTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			crawlerContentTable.setFillsViewportHeight(true);

			crawlerContentTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (crawlerContentTable.getSelectedRow() != -1) {
										deleteButton.setEnabled(true);
										if (crawlerContentTable.getSelectedRows().length > 1) {
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

			crawlerContentTable.setAutoCreateRowSorter(true);
		}

		return crawlerContentTable;
	}

	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("ContentList.id"));
		columnNames.add(LanguageLoader.getString("ContentList.title"));
		columnNames.add(LanguageLoader.getString("ContentList.date"));
		columnNames.add(LanguageLoader.getString("ContentList.viewDate"));
		columnNames.add(LanguageLoader.getString("ContentList.hasSave"));
		return columnNames;
	}
	public void showData(int startIndex,int pageSize) {
		((CrawlerContentTabelModel) getCrawlerContentTable().getModel()).setData(getData(startIndex, pageSize));
		final JTable table = getCrawlerContentTable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(table);
			}
		});
	}

	public List<CrawlerContentBean> getData(int startIndex,int pageSize) {
		CrawlerContentCriteria criteria = new CrawlerContentCriteria();
		criteria.setStartIndex(startIndex);
		criteria.setPageSize(pageSize);
		PaginationSupport<CrawlerContentBean> result = crawlerContentService.getPaginatedList(criteria,GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT);
		paginationBar.setPaginationSupport(result);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<CrawlerContentBean>) result.getData();
	}
	

	@Override
	public void update(CowSwingEvent event) {
		logger.info("ContentListPage---响应事件");
		if (event.getEventType().equals(CowSwingEventType.ContentTableAddEvent) || event.getEventType().equals(CowSwingEventType.ContentTableDeleteEvent)) {
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



	public ContentDialog getContentDialog() {
		return contentDialog;
	}



	public void setContentDialog(ContentDialog contentDialog) {
		this.contentDialog = contentDialog;
	}



	public ICrawlerService<CrawlerContentBean, CrawlerContentCriteria> getCrawlerContentService() {
		return crawlerContentService;
	}



	public void setCrawlerContentService(
			ICrawlerService<CrawlerContentBean, CrawlerContentCriteria> crawlerContentService) {
		this.crawlerContentService = crawlerContentService;
	}



	public ICrawlerService<CrawlerContentCommentBean, CrawlerContentCommentCriteria> getCrawlerContentCommentService() {
		return crawlerContentCommentService;
	}



	public void setCrawlerContentCommentService(
			ICrawlerService<CrawlerContentCommentBean, CrawlerContentCommentCriteria> crawlerContentCommentService) {
		this.crawlerContentCommentService = crawlerContentCommentService;
	}



	public ICrawlerService<CrawlerContentPaginationBean, CrawlerContentPaginationCriteria> getCrawlerContentPaginationService() {
		return crawlerContentPaginationService;
	}



	public void setCrawlerContentPaginationService(
			ICrawlerService<CrawlerContentPaginationBean, CrawlerContentPaginationCriteria> crawlerContentPaginationService) {
		this.crawlerContentPaginationService = crawlerContentPaginationService;
	}



	public ICrawlerService<CrawlerContentResourceBean, CrawlerContentResourceCriteria> getCrawlerContentResourceService() {
		return crawlerContentResourceService;
	}



	public void setCrawlerContentResourceService(
			ICrawlerService<CrawlerContentResourceBean, CrawlerContentResourceCriteria> crawlerContentResourceService) {
		this.crawlerContentResourceService = crawlerContentResourceService;
	}



	public ICrawlerService<CrawlerExtendFieldBean, CrawlerExtendFieldCriteria> getCrawlerExtendFieldService() {
		return crawlerExtendFieldService;
	}



	public void setCrawlerExtendFieldService(
			ICrawlerService<CrawlerExtendFieldBean, CrawlerExtendFieldCriteria> crawlerExtendFieldService) {
		this.crawlerExtendFieldService = crawlerExtendFieldService;
	}
    
	

   
    
    
	

}
