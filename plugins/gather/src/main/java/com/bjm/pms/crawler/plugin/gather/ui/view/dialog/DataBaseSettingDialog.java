/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.view.dialog;

import java.awt.event.ActionEvent;
import java.sql.Connection;

import javax.annotation.Resource;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.persist.util.ConnectionConfig;
import com.bjm.pms.crawler.plugin.gather.constant.SystemConstant;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerDataBaseBean;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerDataBaseTableModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.DataBaseListPage;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.DataBaseSettingPanel;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.core.event.CowSwingListener;
import com.bjm.pms.crawler.view.ui.view.dialog.AbstractDialog;

/**
 * 数据库信息参数设置
 *@author DuanYong
 *@since 2013-2-14下午6:03:05
 *@version 1.0
 */
@Component("dataBaseSettingDialog")
public class DataBaseSettingDialog extends AbstractDialog implements CowSwingListener{
	private static final long serialVersionUID = 1L;
	@Resource(name="dataBaseListPage")
    private DataBaseListPage dataBaseListPage;
	@Resource(name="dataBaseSettingPanel")
	private DataBaseSettingPanel dataBaseSettingPanel;
    private CrawlerDataBaseBean crawlerDataBaseBean;
	private String type;
	private Integer dataBaseId;
	public DataBaseSettingDialog(){
		super(650,350,true);
	}
	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
			logger.info("初始化数据库信息参数面板");
			jTabbedPane.addTab(LanguageLoader.getString("System.DataBaseSetting"), dataBaseSettingPanel);
			centerPane = jTabbedPane;
		}
		return centerPane;
	}
	/**
	 * 填充JTabbedPane值
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-3 下午12:20:32
	 * @return void
	 */
	private void fillJTabbedPane(){
		logger.info("填充JTabbedPane值");
		dataBaseSettingPanel.initData(crawlerDataBaseBean);
	}
	@Override
	public void update(CowSwingEvent event) {
		logger.info("DataBaseSettingDialog---响应事件");
		if (event.getEventType().isAlso(CowSwingEventType.DataBaseTableAddEvent)){
			
		}
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.dialog.AbstractDialog#finishButtonActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void finishButtonActionPerformed(ActionEvent event) {
		crawlerDataBaseBean = new CrawlerDataBaseBean();
		crawlerDataBaseBean.setDescription(dataBaseSettingPanel.getData().getDescription());
		crawlerDataBaseBean.setClassName(dataBaseSettingPanel.getData().getClassName());
		crawlerDataBaseBean.setPassword(dataBaseSettingPanel.getData().getPassword());
		crawlerDataBaseBean.setType(dataBaseSettingPanel.getData().getType());
		crawlerDataBaseBean.setUrl(dataBaseSettingPanel.getData().getUrl());
		crawlerDataBaseBean.setUserName(dataBaseSettingPanel.getData().getUserName());
		
		ConnectionConfig config = new ConnectionConfig();
		config.setClassName(crawlerDataBaseBean.getClassName());
		config.setPassword(crawlerDataBaseBean.getPassword());
		config.setType(crawlerDataBaseBean.getType());
		config.setUrl(crawlerDataBaseBean.getUrl());
		config.setUserName(crawlerDataBaseBean.getUserName());
		if(Constant.OPTION_TYPE_ADD == this.type){
			crawlerDataBaseBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.DataBaseTableAddEvent));
			int id = dataBaseListPage.getCrawlerDataBaseService().insert(crawlerDataBaseBean,SystemConstant.SQLMAP_ID_INSERT_CRAWLER_DATABASE);
			config.setId(id+"");
			dataBaseListPage.getConnectionManager().addConnConfig(config);
		}else{
			config.setId(this.dataBaseId+"");
			crawlerDataBaseBean.setDataBaseId(this.dataBaseId);
			crawlerDataBaseBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.DataBaseTableUpdateEvent));
			dataBaseListPage.getCrawlerDataBaseService().update(crawlerDataBaseBean,SystemConstant.SQLMAP_ID_UPDATE_CRAWLER_DATABASE);
			dataBaseListPage.getConnectionManager().updateConnConfig(config);
		}
		Connection conn = dataBaseListPage.getConnectionManager().getConnection(config.getId());
		if(null == conn){
			JOptionPane.showMessageDialog(null, LanguageLoader.getString("System.DataBaseSettingErrorMessage"), LanguageLoader.getString("System.DataBaseSettingError"), JOptionPane.CLOSED_OPTION);
		}else{
			dataBaseListPage.getConnectionManager().freeConnection(config.getId(),conn);
		}
		this.dispose();
	}
	protected void initData(String type) {
		this.type = type;
		JTable dataBaseTable = dataBaseListPage.getCrawlerDataBaseTable();
		if(dataBaseTable.getSelectedRow() != -1 && Constant.OPTION_TYPE_MODIFY == type){
			CrawlerDataBaseTableModel crawlerDataBaseTabelModel = (CrawlerDataBaseTableModel)dataBaseTable.getModel();
			crawlerDataBaseBean = crawlerDataBaseTabelModel.getRowObject(dataBaseTable.getSelectedRow());
			this.dataBaseId = crawlerDataBaseBean.getDataBaseId();
		}else{
			crawlerDataBaseBean = new CrawlerDataBaseBean();
		}
		fillJTabbedPane();
	}
	public void dispose(){
		super.dispose();
		centerPane = null;
	}

}
