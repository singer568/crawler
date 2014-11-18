/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.view.panel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.collections.CollectionUtils;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.service.ICrawlerService;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.gather.constant.SystemConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerDataBaseBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerDataBaseCriteria;
import org.javacoo.cowswing.plugin.gather.ui.action.AddDataBaseAction;
import org.javacoo.cowswing.plugin.gather.ui.action.ConnectionDataBaseAction;
import org.javacoo.cowswing.plugin.gather.ui.action.DeleteDataBaseAction;
import org.javacoo.cowswing.plugin.gather.ui.action.UpdateDataBaseAction;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerDataBaseTableModel;
import org.javacoo.cowswing.plugin.gather.ui.view.dialog.DataBaseInfoDialog;
import org.javacoo.cowswing.plugin.gather.ui.view.dialog.DataBaseSettingDialog;
import org.javacoo.cowswing.ui.util.ColumnResizer;
import org.javacoo.cowswing.ui.view.panel.AbstractListPage;
import org.javacoo.persistence.PaginationSupport;
import org.javacoo.persistence.util.ConnectionConfig;
import org.javacoo.persistence.util.DBConnectionManager;
import org.springframework.stereotype.Component;

/**
 * 数据库信息列表panel
 *@author DuanYong
 *@since 2013-2-13下午8:33:35
 *@version 1.0
 */
@Component("dataBaseListPage")
public class DataBaseListPage extends AbstractListPage implements ListSelectionListener{

	private static final long serialVersionUID = 1L;
	/**
	 * 添加数据库信息按钮
	 */
	private JButton addButton;
	/**
	 * 修改数据库信息按钮
	 */
	private JButton modifyButton;
	/**
	 * 删除数据库信息按钮
	 */
	private JButton deleteButton;
	/**
	 * 测试连接数据库信息按钮
	 */
	private JButton connectionButton;
	
	/**
	 * 添加数据库信息Action
	 */
	@Resource(name="addDataBaseAction")
	private AddDataBaseAction addDataBaseAction;
	/**
	 * 修改数据库信息Action
	 */
	@Resource(name="updateDataBaseAction")
	private UpdateDataBaseAction updateDataBaseAction;
	/**
	 * 删除数据库信息Action
	 */
	@Resource(name="deleteDataBaseAction")
	private DeleteDataBaseAction deleteDataBaseAction;
	/**
	 * 连接数据库信息Action
	 */
	@Resource(name="connectionDataBaseAction")
	private ConnectionDataBaseAction connectionDataBaseAction;
	/**
	 * 数据库Table
	 */
	private JTable crawlerDataBaseTable;
	/**
	 * 数据库服务类
	 */
	@Resource(name="crawlerDataBaseService")
	private ICrawlerService<CrawlerDataBaseBean,CrawlerDataBaseCriteria> crawlerDataBaseService;
	
	/**
	 * 数据库信息设置Dialog
	 */
	@Resource(name="dataBaseSettingDialog")
	private DataBaseSettingDialog dataBaseSettingDialog;
	/**
	 * 数据库信息查看设置Dialog
	 */
	@Resource(name="dataBaseInfoDialog")
	private DataBaseInfoDialog dataBaseInfoDialog;
	
	private DBConnectionManager connectionManager;
	
	public DataBaseListPage(){
		super();
		connectionManager = DBConnectionManager.getInstance();
	}
	@Override
	protected JComponent getTopPane() {
		super.getTopPane();
		buttonBar.add(getAddButton());
		buttonBar.add(getModifyButton());
		buttonBar.add(getDeleteButton());
		buttonBar.add(getConnectionButton());
		return buttonBar;
	}
	
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton(addDataBaseAction);
		}
		return addButton;
	}

	private JButton getModifyButton() {
		if (modifyButton == null) {
			modifyButton = new JButton(updateDataBaseAction);
		}
		return modifyButton;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteDataBaseAction);
		}
		return deleteButton;
	}
	
	private JButton getConnectionButton() {
		if (connectionButton == null) {
			connectionButton = new JButton(connectionDataBaseAction);
		}
		return connectionButton;
	}

	@Override
	protected JComponent getCenterPane() {
		return new JScrollPane(getCrawlerDataBaseTable());
	}
	/**
	 * @return crawlerDataBaseTable
	 * */
	public JTable getCrawlerDataBaseTable() {
		if (crawlerDataBaseTable == null) {
			crawlerDataBaseTable = new JTable();
			CrawlerDataBaseTableModel dataModel = new CrawlerDataBaseTableModel(
					getColumnNames());
			crawlerDataBaseTable.setModel(dataModel);
			crawlerDataBaseTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			crawlerDataBaseTable.setFillsViewportHeight(true);

			crawlerDataBaseTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (crawlerDataBaseTable.getSelectedRow() != -1) {
										if (crawlerDataBaseTable.getSelectedRows().length > 1) {
											updateDataBaseAction.setEnabled(false);
											connectionDataBaseAction.setEnabled(false);
										} else {
											updateDataBaseAction.setEnabled(true);
											connectionDataBaseAction.setEnabled(true);
										}
										deleteDataBaseAction.setEnabled(true);
									} else {
										connectionDataBaseAction.setEnabled(false);
										updateDataBaseAction.setEnabled(false);
										deleteDataBaseAction.setEnabled(false);
									}
								}
							});
						}
					});

			crawlerDataBaseTable.setAutoCreateRowSorter(true);
		}

		return crawlerDataBaseTable;
	}
	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("System.DataBase_id"));
		columnNames.add(LanguageLoader.getString("System.DataBase_desc"));
		columnNames.add(LanguageLoader.getString("System.DataBase_url"));
		columnNames.add(LanguageLoader.getString("System.DataBase_className"));
		columnNames.add(LanguageLoader.getString("System.DataBase_userName"));
		columnNames.add(LanguageLoader.getString("System.DataBase_password"));
		columnNames.add(LanguageLoader.getString("System.DataBase_type"));

		return columnNames;
	}
	public void showData(int startIndex,int pageSize) {
		List<CrawlerDataBaseBean> dataList = getData(startIndex, pageSize);
		((CrawlerDataBaseTableModel) getCrawlerDataBaseTable().getModel()).setData(dataList);
		final JTable table = getCrawlerDataBaseTable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(table);
			}
		});
		
	}

	public List<CrawlerDataBaseBean> getData(int startIndex,int pageSize) {
		CrawlerDataBaseCriteria criteria = new CrawlerDataBaseCriteria();
		criteria.setStartIndex(startIndex);
		criteria.setPageSize(pageSize);
		PaginationSupport<CrawlerDataBaseBean> result = crawlerDataBaseService.getPaginatedList(criteria,SystemConstant.SQLMAP_ID_LIST_CRAWLER_DATABASE);
		paginationBar.setPaginationSupport(result);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<CrawlerDataBaseBean>) result.getData();
	}
	@PostConstruct
	protected void initDBConnection(){
		List<CrawlerDataBaseBean> dataList = crawlerDataBaseService.getList(new CrawlerDataBaseCriteria(), SystemConstant.SQLMAP_ID_LIST_CRAWLER_DATABASE);
		if(CollectionUtils.isNotEmpty(dataList)){
			ConnectionConfig config = null;
			for(CrawlerDataBaseBean bean : dataList){
				config = new ConnectionConfig();
				config.setClassName(bean.getClassName());
				config.setId(bean.getDataBaseId()+"");
				config.setPassword(bean.getPassword());
				config.setType(bean.getType());
				config.setUrl(bean.getUrl());
				config.setUserName(bean.getUserName());
				connectionManager.addConnConfig(config);
			}
		}
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.event.CrawlerListener#update(org.javacoo.crawler.event.CrawlerEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		logger.info("DataBaseListPage---响应事件");
		if (event.getEventType().isAlso(CowSwingEventType.DataBaseTableChangeEvent)) {
			initData();
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		logger.info("DataBaseListPage-ListSelectionEvent---响应事件");
	}
	@Override
	public String getPageName() {
		return LanguageLoader.getString("System.DataBaseList");
	}

	@Override
	public void disposePage() {
		super.disposePage();
	}
	public ICrawlerService<CrawlerDataBaseBean, CrawlerDataBaseCriteria> getCrawlerDataBaseService() {
		return crawlerDataBaseService;
	}
	public void setCrawlerDataBaseService(
			ICrawlerService<CrawlerDataBaseBean, CrawlerDataBaseCriteria> crawlerDataBaseService) {
		this.crawlerDataBaseService = crawlerDataBaseService;
	}
	public DataBaseSettingDialog getDataBaseSettingDialog() {
		return dataBaseSettingDialog;
	}
	public void setDataBaseSettingDialog(DataBaseSettingDialog dataBaseSettingDialog) {
		this.dataBaseSettingDialog = dataBaseSettingDialog;
	}
	public DBConnectionManager getConnectionManager() {
		return connectionManager;
	}
	public DataBaseInfoDialog getDataBaseInfoDialog() {
		return dataBaseInfoDialog;
	}
    
	
}
