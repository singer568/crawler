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
import com.bjm.pms.crawler.plugin.gather.ui.action.AddFtpAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.DeleteFtpAction;
import com.bjm.pms.crawler.plugin.gather.ui.action.UpdateFtpAction;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerConfigFtpTableModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.dialog.FtpSettingDialog;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.ui.util.ColumnResizer;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractListPage;

/**
 * FTP配置信息列表
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午4:58:16
 * @version 1.0
 */
@Component("ftpListPage")
public class FtpListPage extends AbstractListPage implements ListSelectionListener{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 添加FTP配置信息按钮
	 */
	private JButton addButton;
	/**
	 * 修改FTP配置信息按钮
	 */
	private JButton modifyButton;
	/**
	 * 删除FTP配置信息按钮
	 */
	private JButton deleteButton;
	
	/**
	 * 添加FTP配置信息Action
	 */
	@Resource(name="addFtpAction")
	private AddFtpAction addFtpAction;
	/**
	 * 修改FTP配置信息Action
	 */
	@Resource(name="updateFtpAction")
	private UpdateFtpAction updateFtpAction;
	/**
	 * 删除FTP配置信息Action
	 */
	@Resource(name="deleteFtpAction")
	private DeleteFtpAction deleteFtpAction;
	/**
	 * FTP配置Table
	 */
	private JTable crawlerFtpTable;
	/**
	 * FTP配置服务类
	 */
	@Resource(name="crawlerConfigService")
	private ICrawlerService<CrawlerConfigBean,CrawlerConfigCriteria> crawlerConfigService;
	
	/**
	 * FTP配置信息设置Dialog
	 */
	@Resource(name="ftpSettingDialog")
	private FtpSettingDialog ftpSettingDialog;
	
	public FtpListPage(){
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
			addButton = new JButton(addFtpAction);
		}
		return addButton;
	}

	private JButton getModifyButton() {
		if (modifyButton == null) {
			modifyButton = new JButton(updateFtpAction);
		}
		return modifyButton;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteFtpAction);
		}
		return deleteButton;
	}
	@Override
	protected JComponent getCenterPane() {
		return new JScrollPane(getFtpConfigTable());
	}
	/**
	 * @return crawlerDataBaseTable
	 * */
	public JTable getFtpConfigTable() {
		if (crawlerFtpTable == null) {
			crawlerFtpTable = new JTable();
			CrawlerConfigFtpTableModel dataModel = new CrawlerConfigFtpTableModel(
					getColumnNames());
			crawlerFtpTable.setModel(dataModel);
			crawlerFtpTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			crawlerFtpTable.setFillsViewportHeight(true);

			crawlerFtpTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (crawlerFtpTable.getSelectedRow() != -1) {
										if (crawlerFtpTable.getSelectedRows().length > 1) {
											updateFtpAction.setEnabled(false);
										} else {
											updateFtpAction.setEnabled(true);
										}
										deleteFtpAction.setEnabled(true);
									} else {
										updateFtpAction.setEnabled(false);
										deleteFtpAction.setEnabled(false);
									}
								}
							});
						}
					});

			crawlerFtpTable.setAutoCreateRowSorter(true);
		}

		return crawlerFtpTable;
	}
	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("System.Ftp_ID"));
		columnNames.add(LanguageLoader.getString("System.Ftp_name"));
		columnNames.add(LanguageLoader.getString("System.Ftp_port"));
		columnNames.add(LanguageLoader.getString("System.Ftp_url"));
		columnNames.add(LanguageLoader.getString("System.Ftp_userName"));
		columnNames.add(LanguageLoader.getString("System.Ftp_password"));

		return columnNames;
	}
	public void showData(int startIndex,int pageSize) {
		List<CrawlerConfigBean> dataList = getData(startIndex, pageSize);
		((CrawlerConfigFtpTableModel) getFtpConfigTable().getModel()).setData(dataList);
		final JTable table = getFtpConfigTable();
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
		criteria.setConfigType(Constant.CRAWLER_CONFIG_TYPE_FTP);
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
		logger.info("FtpListPage---响应事件");
		if (event.getEventType().isAlso(CowSwingEventType.ConfigTableChangeEvent)) {
			initData();
		}
	}
	@Override
	public String getPageName() {
		return LanguageLoader.getString("System.FtpSettingList");
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
	 * @return the ftpSettingDialog
	 */
	public FtpSettingDialog getFtpSettingDialog() {
		return ftpSettingDialog;
	}
	
	

}
