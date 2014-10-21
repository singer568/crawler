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
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentCommentBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentCommentCriteria;
import org.javacoo.cowswing.plugin.gather.ui.action.DeleteContentCommentAction;
import org.javacoo.cowswing.plugin.gather.ui.action.ViewContentCommentAction;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerContentCommentTabelModel;
import org.javacoo.cowswing.ui.util.ColumnResizer;
import org.javacoo.cowswing.ui.view.panel.AbstractListPage;
import org.javacoo.persistence.PaginationSupport;

/**
 * 采集内容评论列表panel
 *@author DuanYong
 *@since 2012-12-5下午9:09:22
 *@version 1.0
 */
public class CopyOfContentCommentListPanel extends AbstractListPage implements ListSelectionListener {
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
	private JTable crawlerContentCommentTable;
	/**
	 * 采集内容分页服务类
	 */
	@Resource(name="crawlerContentCommentService")
	private ICrawlerService<CrawlerContentCommentBean,CrawlerContentCommentCriteria> crawlerContentCommentService;
	/**
	 * 查看内容Action
	 */
	@Resource(name="viewContentCommentAction")
	private ViewContentCommentAction viewContentCommentAction;
	/**
	 * 删除采集内容分页Action
	 */
	@Resource(name="deleteContentCommentAction")
	private DeleteContentCommentAction deleteContentCommentAction;

	/**
	 * 查看内容ID
	 */
	private Integer contentId = 0;

	public CopyOfContentCommentListPanel() {
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
		return new JScrollPane(getCrawlerContentCommentTable());
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteContentCommentAction);
		}
		return deleteButton;
	}

	private JButton getViewButton() {
		if (viewButton == null) {
			viewButton = new JButton(viewContentCommentAction);
		}
		return viewButton;
	}

	/**
	 * @return crawlerRuleTable
	 * */
	public JTable getCrawlerContentCommentTable() {
		if (crawlerContentCommentTable == null) {
			crawlerContentCommentTable = new JTable();
			CrawlerContentCommentTabelModel dataModel = new CrawlerContentCommentTabelModel(
					getColumnNames());
			crawlerContentCommentTable.setModel(dataModel);
			crawlerContentCommentTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			crawlerContentCommentTable.setFillsViewportHeight(true);

			crawlerContentCommentTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (crawlerContentCommentTable.getSelectedRow() != -1) {
										deleteButton.setEnabled(true);
										if (crawlerContentCommentTable.getSelectedRows().length > 1) {
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

			crawlerContentCommentTable.setAutoCreateRowSorter(true);
		}

		return crawlerContentCommentTable;
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
		((CrawlerContentCommentTabelModel) getCrawlerContentCommentTable().getModel()).setData(getData(startIndex,pageSize));
		final JTable table = getCrawlerContentCommentTable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(table);
			}
		});
	}

	public List<CrawlerContentCommentBean> getData(int startIndex,int pageSize) {
		CrawlerContentCommentCriteria criteria = new CrawlerContentCommentCriteria();
		criteria.setStartIndex(startIndex);
		criteria.setPageSize(pageSize);
		if(null == this.contentId){
			this.contentId = 0;
		}
		criteria.setContentId(this.contentId);
		PaginationSupport<CrawlerContentCommentBean> result = crawlerContentCommentService.getPaginatedList(criteria,GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_COMMENT);
		paginationBar.setPaginationSupport(result);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<CrawlerContentCommentBean>) result.getData();
	}

	@Override
	public void update(CowSwingEvent event) {
		logger.info("RuleListPage---响应事件");
		if (event.getEventType().equals(CowSwingEventType.ContentCommentTableAddEvent) || event.getEventType().equals(CowSwingEventType.ContentCommentTableDeleteEvent)) {
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



	public ICrawlerService<CrawlerContentCommentBean, CrawlerContentCommentCriteria> getCrawlerContentCommentService() {
		return crawlerContentCommentService;
	}



	public void setCrawlerContentCommentService(
			ICrawlerService<CrawlerContentCommentBean, CrawlerContentCommentCriteria> crawlerContentCommentService) {
		this.crawlerContentCommentService = crawlerContentCommentService;
	}


	
}
