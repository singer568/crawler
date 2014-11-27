/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
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
import com.bjm.pms.crawler.plugin.core.service.beans.CrawlerConfigBean;
import com.bjm.pms.crawler.plugin.core.service.beans.CrawlerConfigCriteria;
import com.bjm.pms.crawler.plugin.gather.constant.SystemConstant;
import com.bjm.pms.crawler.plugin.gather.ui.action.AddDiyDataAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.DeleteDiyDataAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.UpdateDiyDataAction;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerConfigDiyDataTableModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.dialog.DiyDataSettingDialog;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.ui.util.ColumnResizer;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractListPage;

/**
 * 自定义数据信息列表
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-8-27 上午11:21:55
 * @version 1.0
 */
@Component("diyDataListPage")
public class DiyDataListPage extends AbstractListPage implements ListSelectionListener{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 添加自定义数据信息按钮
	 */
	private JButton addButton;
	/**
	 * 修改自定义数据信息按钮
	 */
	private JButton modifyButton;
	/**
	 * 删除自定义数据信息按钮
	 */
	private JButton deleteButton;
	
	/**
	 * 添加自定义数据信息Action
	 */
	@Resource(name="addDiyDataAction")
	private AddDiyDataAction addDiyDataAction;
	/**
	 * 修改自定义数据信息Action
	 */
	@Resource(name="updateDiyDataAction")
	private UpdateDiyDataAction updateDiyDataAction;
	/**
	 * 删除自定义数据信息Action
	 */
	@Resource(name="deleteDiyDataAction")
	private DeleteDiyDataAction deleteDiyDataAction;
	/**
	 * 自定义数据Table
	 */
	private JTable crawlerDiyDataTable;
	/**
	 * 自定义数据服务类
	 */
	@Resource(name="crawlerConfigService")
	private ICrawlerService<CrawlerConfigBean,CrawlerConfigCriteria> crawlerConfigService;
	
	/**
	 * 自定义数据信息设置Dialog
	 */
	@Resource(name="diyDataSettingDialog")
	private DiyDataSettingDialog diyDataSettingDialog;
	
	public DiyDataListPage(){
		super();
	}
	
	@Override
	protected JComponent getTopPane() {
		super.getTopPane();
		buttonBar.add(getAddButton());
		buttonBar.add(getModifyButton());
		buttonBar.add(getDeleteButton());
		return buttonBar;
	}
	
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton(addDiyDataAction);
		}
		return addButton;
	}

	private JButton getModifyButton() {
		if (modifyButton == null) {
			modifyButton = new JButton(updateDiyDataAction);
		}
		return modifyButton;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteDiyDataAction);
		}
		return deleteButton;
	}
	@Override
	protected JComponent getCenterPane() {
		return new JScrollPane(getDiyDataConfigTable());
	}
	/**
	 * @return crawlerDataBaseTable
	 * */
	public JTable getDiyDataConfigTable() {
		if (crawlerDiyDataTable == null) {
			crawlerDiyDataTable = new JTable();
			CrawlerConfigDiyDataTableModel dataModel = new CrawlerConfigDiyDataTableModel(
					getColumnNames());
			crawlerDiyDataTable.setModel(dataModel);
			crawlerDiyDataTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			crawlerDiyDataTable.setFillsViewportHeight(true);

			crawlerDiyDataTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (crawlerDiyDataTable.getSelectedRow() != -1) {
										if (crawlerDiyDataTable.getSelectedRows().length > 1) {
											updateDiyDataAction.setEnabled(false);
										} else {
											updateDiyDataAction.setEnabled(true);
										}
										deleteDiyDataAction.setEnabled(true);
									} else {
										updateDiyDataAction.setEnabled(false);
										deleteDiyDataAction.setEnabled(false);
									}
								}
							});
						}
					});

			crawlerDiyDataTable.setAutoCreateRowSorter(true);
		}

		return crawlerDiyDataTable;
	}
	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("System.Diy_ID"));
		columnNames.add(LanguageLoader.getString("System.Diy_name"));
		columnNames.add(LanguageLoader.getString("System.Diy_value"));
		columnNames.add(LanguageLoader.getString("System.Diy_type"));
		columnNames.add(LanguageLoader.getString("System.Diy_desc"));
		return columnNames;
	}
	public void showData(int startIndex,int pageSize) {
		List<CrawlerConfigBean> dataList = getData(startIndex, pageSize);
		((CrawlerConfigDiyDataTableModel) getDiyDataConfigTable().getModel()).setData(dataList);
		final JTable table = getDiyDataConfigTable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(table);
			}
		});
		
	}

	public List<CrawlerConfigBean> getData(int startIndex,int pageSize) {
		CrawlerConfigCriteria criteria = new CrawlerConfigCriteria();
		criteria.setStartIndex(startIndex);
		criteria.setPageSize(pageSize);
		criteria.setConfigType(Constant.CRAWLER_CONFIG_TYPE_DIYDATA);
		PaginationSupport<CrawlerConfigBean> result = crawlerConfigService.getPaginatedList(criteria,SystemConstant.SQLMAP_ID_LIST_CRAWLER_CINFIG);
		paginationBar.setPaginationSupport(result);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<CrawlerConfigBean>) result.getData();
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.event.CrawlerListener#update(org.javacoo.crawler.event.CrawlerEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		logger.info("DiyDataListPage---响应事件");
		if (event.getEventType().isAlso(CowSwingEventType.ConfigTableChangeEvent)) {
			initData();
		}
	}
	@Override
	public String getPageName() {
		return LanguageLoader.getString("System.DiyDataSettingList");
	}

	@Override
	public void disposePage() {
		super.disposePage();
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param crawlerConfigService the crawlerConfigService to set
	 */
	public void setCrawlerConfigService(
			ICrawlerService<CrawlerConfigBean, CrawlerConfigCriteria> crawlerConfigService) {
		this.crawlerConfigService = crawlerConfigService;
	}
	

	/**
	 * @return the crawlerConfigService
	 */
	public ICrawlerService<CrawlerConfigBean, CrawlerConfigCriteria> getCrawlerConfigService() {
		return crawlerConfigService;
	}

	/**
	 * @return the DiyDataSettingDialog
	 */
	public DiyDataSettingDialog getDiyDataSettingDialog() {
		return diyDataSettingDialog;
	}
	
	

}
