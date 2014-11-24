/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.view.dialog;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.core.event.CowSwingListener;
import org.javacoo.cowswing.plugin.core.service.beans.CrawlerConfigBean;
import org.javacoo.cowswing.plugin.gather.constant.SystemConstant;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerConfigFtpTableModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.FtpListPage;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.FtpSettingPanel;
import org.javacoo.cowswing.ui.view.dialog.AbstractDialog;
import org.springframework.stereotype.Component;


/**
 * FTP配置参数设置
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午5:23:48
 * @version 1.0
 */
@Component("ftpSettingDialog")
public class FtpSettingDialog extends AbstractDialog implements CowSwingListener{

	private static final long serialVersionUID = 1L;
	@Resource(name="ftpListPage")
	private FtpListPage ftpListPage;
	@Resource(name="ftpSettingPanel")
	private FtpSettingPanel ftpSettingPanel;
    private CrawlerConfigBean crawlerConfigBean;
	private String type;
	private Integer configId;
	public FtpSettingDialog(){
		super(350,300,true);
	}
	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
			logger.info("初始化FTP信息参数面板");
			jTabbedPane.addTab(LanguageLoader.getString("System.FtpSetting"), ftpSettingPanel);
			centerPane = jTabbedPane;
		}
		return centerPane;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.dialog.AbstractDialog#finishButtonActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void finishButtonActionPerformed(ActionEvent event) {
		crawlerConfigBean = new CrawlerConfigBean();
		crawlerConfigBean.setConfigType(Constant.CRAWLER_CONFIG_TYPE_FTP);
		crawlerConfigBean.setCrawlerFtpConfigBean(ftpSettingPanel.getData());
		if(Constant.OPTION_TYPE_ADD == this.type){
			crawlerConfigBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ConfigTableAddEvent));
			ftpListPage.getCrawlerConfigService().insert(crawlerConfigBean,SystemConstant.SQLMAP_ID_INSERT_CRAWLER_CINFIG);
		}else{
			crawlerConfigBean.setConfigId(this.configId);
			crawlerConfigBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ConfigTableUpdateEvent));
			ftpListPage.getCrawlerConfigService().update(crawlerConfigBean,SystemConstant.SQLMAP_ID_UPDATE_CRAWLER_CINFIG);
		}
		this.dispose();
	}
	
	protected void initData(String type) {
		this.type = type;
		JTable dataBaseTable = ftpListPage.getFtpConfigTable();
		if(dataBaseTable.getSelectedRow() != -1 && Constant.OPTION_TYPE_MODIFY == type){
			CrawlerConfigFtpTableModel crawlerConfigFtpTableModel = (CrawlerConfigFtpTableModel)dataBaseTable.getModel();
			crawlerConfigBean = crawlerConfigFtpTableModel.getRowObject(dataBaseTable.getSelectedRow());
			this.configId = crawlerConfigBean.getConfigId();
		}else{
			crawlerConfigBean = new CrawlerConfigBean();
		}
		fillJTabbedPane();
	}
	public void dispose(){
		super.dispose();
		centerPane = null;
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
		ftpSettingPanel.initData(crawlerConfigBean.getCrawlerFtpConfigBean());
	}
}
