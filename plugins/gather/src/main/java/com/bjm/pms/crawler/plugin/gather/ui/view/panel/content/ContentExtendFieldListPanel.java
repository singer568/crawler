/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
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
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerExtendFieldBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerExtendFieldCriteria;
import com.bjm.pms.crawler.plugin.gather.ui.action.DeleteContentExtendFieldAction;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerContentExtendFieldTableModel;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.ui.util.ColumnResizer;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractListPage;

/**
 * 内容扩展字段panel
 *@author DuanYong
 *@since 2013-3-25上午9:44:29
 *@version 1.0
 */
@Component("contentExtendFieldListPanel")
public class ContentExtendFieldListPanel extends AbstractListPage implements ListSelectionListener {
	private static final long serialVersionUID = 1L;
	/**
	 * 删除扩展字段按钮
	 */
	private JButton deleteButton;
	/**
	 * 内容扩展字段Table
	 */
	private JTable crawlerContentExtendFieldTable;
	/**扩展字段服务类*/
	@Resource(name="crawlerExtendFieldService")
	private ICrawlerService<CrawlerExtendFieldBean,CrawlerExtendFieldCriteria> crawlerExtendFieldService;
	/**
	 * 删除扩展字段Action
	 */
	@Resource(name="deleteContentExtendFieldAction")
	private DeleteContentExtendFieldAction deleteContentExtendFieldAction;
	/**
	 * 查看内容ID
	 */
	private Integer contentId = 0;
	public ContentExtendFieldListPanel() {
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
		return new JScrollPane(getCrawlerContentExtendFieldTable());
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteContentExtendFieldAction);
		}
		return deleteButton;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.event.CrawlerListener#update(org.javacoo.crawler.event.CowSwingEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public JTable getCrawlerContentExtendFieldTable() {
		if (crawlerContentExtendFieldTable == null) {
			crawlerContentExtendFieldTable = new JTable();
			CrawlerContentExtendFieldTableModel dataModel = new CrawlerContentExtendFieldTableModel(
					getColumnNames());
			crawlerContentExtendFieldTable.setModel(dataModel);
			crawlerContentExtendFieldTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			crawlerContentExtendFieldTable.setFillsViewportHeight(true);

			crawlerContentExtendFieldTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (crawlerContentExtendFieldTable.getSelectedRow() != -1) {
										deleteButton.setEnabled(true);
									} else {
										deleteButton.setEnabled(false);
									}
								}
							});
						}
					});

			crawlerContentExtendFieldTable.setAutoCreateRowSorter(true);
		}

		return crawlerContentExtendFieldTable;
	}
	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("ContentList.id"));
		columnNames.add(LanguageLoader.getString("RuleContentSetting.extndsFieldName"));
		columnNames.add(LanguageLoader.getString("RuleContentSetting.extndsFieldValue"));

		return columnNames;
	}
	public void showContent(Integer contentId){
		this.contentId = contentId;
		initData();
	}
	public void showData(int startIndex,int pageSize) {
		((CrawlerContentExtendFieldTableModel) getCrawlerContentExtendFieldTable().getModel()).setData(getData(startIndex,pageSize));
		final JTable table = getCrawlerContentExtendFieldTable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(table);
			}
		});
	}
	
	public List<CrawlerExtendFieldBean> getData(int startIndex,int pageSize) {
		CrawlerExtendFieldCriteria criteria = new CrawlerExtendFieldCriteria();
		criteria.setStartIndex(startIndex);
		criteria.setPageSize(pageSize);
		if(null == this.contentId){
			this.contentId = 0;
		}
		criteria.setContentId(this.contentId);
		PaginationSupport<CrawlerExtendFieldBean> result = crawlerExtendFieldService.getPaginatedList(criteria,GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_EXTEND_FIELD);
		paginationBar.setPaginationSupport(result);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<CrawlerExtendFieldBean>) result.getData();
	}
	
	public ICrawlerService<CrawlerExtendFieldBean, CrawlerExtendFieldCriteria> getCrawlerExtendFieldService() {
		return crawlerExtendFieldService;
	}
	public void setCrawlerExtendFieldService(
			ICrawlerService<CrawlerExtendFieldBean, CrawlerExtendFieldCriteria> crawlerExtendFieldService) {
		this.crawlerExtendFieldService = crawlerExtendFieldService;
	}
	
	
	
}
