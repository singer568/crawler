/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.view.dialog;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.core.beans.CrawlerDiyDataConfigBean;
import com.bjm.pms.crawler.plugin.core.service.beans.CrawlerConfigBean;
import com.bjm.pms.crawler.plugin.gather.constant.GatherConstant;
import com.bjm.pms.crawler.plugin.gather.constant.SystemConstant;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerConfigDiyDataTableModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.DiyDataListPage;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.DiyDataMapSettingPanel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.DiyDataSettingPanel;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.core.event.CowSwingListener;
import com.bjm.pms.crawler.view.ui.view.dialog.AbstractDialog;
import com.bjm.pms.crawler.view.ui.view.panel.IContentPanel;


/**
 * 自定义数据参数设置
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午5:23:48
 * @version 1.0
 */
@Component("diyDataSettingDialog")
public class DiyDataSettingDialog extends AbstractDialog implements CowSwingListener{

	private static final long serialVersionUID = 1L;
	@Resource(name="diyDataListPage")
	private DiyDataListPage diyDataListPage;
	@Resource(name="diyDataSettingPanel")
	private DiyDataSettingPanel diyDataSettingPanel;
	@Resource(name="diyDataMapSettingPanel")
	private DiyDataMapSettingPanel diyDataMapSettingPanel;
    private CrawlerConfigBean crawlerConfigBean;
	private String type;
	private Integer configId;
	private JTabbedPane jTabbedPane;
	public DiyDataSettingDialog(){
		super(350,400,true);
	}
	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
			logger.info("初始化自定义信息参数面板");
			jTabbedPane.addTab(LanguageLoader.getString("System.DiySetting"), diyDataSettingPanel);
			jTabbedPane.addTab(LanguageLoader.getString("System.DiyMapSetting"), diyDataMapSettingPanel);
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
		crawlerConfigBean.setConfigType(Constant.CRAWLER_CONFIG_TYPE_DIYDATA);
		IContentPanel panel = (IContentPanel)jTabbedPane.getSelectedComponent();
		if(null != panel.getData()){
			crawlerConfigBean.setCrawlerDiyDataConfigBean((CrawlerDiyDataConfigBean)panel.getData());
			if(Constant.OPTION_TYPE_ADD == this.type){
				crawlerConfigBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ConfigTableAddEvent));
				diyDataListPage.getCrawlerConfigService().insert(crawlerConfigBean,SystemConstant.SQLMAP_ID_INSERT_CRAWLER_CINFIG);
			}else{
				crawlerConfigBean.setConfigId(this.configId);
				crawlerConfigBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ConfigTableUpdateEvent));
				diyDataListPage.getCrawlerConfigService().update(crawlerConfigBean,SystemConstant.SQLMAP_ID_UPDATE_CRAWLER_CINFIG);
			}
			this.dispose();
		}
	}
	
	protected void initData(String type) {
		this.type = type;
		JTable dataBaseTable = diyDataListPage.getDiyDataConfigTable();
		if(dataBaseTable.getSelectedRow() != -1 && Constant.OPTION_TYPE_MODIFY == type){
			CrawlerConfigDiyDataTableModel crawlerConfigDiyDataTableModel = (CrawlerConfigDiyDataTableModel)dataBaseTable.getModel();
			crawlerConfigBean = crawlerConfigDiyDataTableModel.getRowObject(dataBaseTable.getSelectedRow());
			this.configId = crawlerConfigBean.getConfigId();
			if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_MAP.equals(crawlerConfigBean.getCrawlerDiyDataConfigBean().getType())){
				jTabbedPane.setSelectedIndex(1);
			}else{
				jTabbedPane.setSelectedIndex(0);
			}
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
		if(null != crawlerConfigBean.getCrawlerDiyDataConfigBean() && GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_MAP.equals(crawlerConfigBean.getCrawlerDiyDataConfigBean().getType())){
			diyDataMapSettingPanel.initData(crawlerConfigBean.getCrawlerDiyDataConfigBean());
		}else{
			diyDataSettingPanel.initData(crawlerConfigBean.getCrawlerDiyDataConfigBean());
		}
		
	}
}
