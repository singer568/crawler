package org.javacoo.cowswing.plugin.gather.ui.view.dialog;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.service.ICrawlerService;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.core.event.CowSwingListener;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleCriteria;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerRuleTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.LocalRuleListPage;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.rule.LocalRuleBaseSettingPanel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.rule.LocalRuleDataBaseSettingPanel;
import org.javacoo.cowswing.ui.view.dialog.AbstractDialog;
import org.springframework.stereotype.Component;


/**
 * 本地采集规则参数设置窗口
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-22 下午5:54:33
 * @version 1.0
 */
@Component("localRuleSettingDialog")
public class LocalRuleSettingDialog extends AbstractDialog implements CowSwingListener{
	private static final long serialVersionUID = 1L;

	/**
	 * 采集规则服务类
	 */
	@Resource(name="crawlerRuleService")
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	/**
	 * 本地采集规则列表页面
	 */
	@Resource(name="localRuleListPage")
	private LocalRuleListPage ruleListPage;
	
	@Resource(name="localRuleBaseSettingPanel")
	private LocalRuleBaseSettingPanel ruleBaseSettingPanel;
	@Resource(name="localRuleDataBaseSettingPanel")
	private LocalRuleDataBaseSettingPanel ruleDataBaseSettingPanel;
	
	private CrawlerRuleBean crawlerRuleBean;
	private Integer ruleId;
	private String status;
	private String type;
	public LocalRuleSettingDialog(){
		super();
	}
	
	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
			logger.info("初始化配置参数面板");
			jTabbedPane.addTab(LanguageLoader.getString("RuleContentSetting.base"), ruleBaseSettingPanel);
			jTabbedPane.addTab(LanguageLoader.getString("RuleContentSetting.dataBaseSet"), ruleDataBaseSettingPanel);
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
		ruleBaseSettingPanel.initData(crawlerRuleBean.getRuleBaseBean());
		ruleDataBaseSettingPanel.initData(crawlerRuleBean.getRuleDataBaseBean(),crawlerRuleBean);
	}
	@Override
	public void update(CowSwingEvent event) {
		logger.info("RuleSettingDialog---响应事件");
		if (event.getEventType().isAlso(CowSwingEventType.RuleTableAddEvent)){
			
		}
	}
	protected String errorMsg(){
		StringBuilder sb = new StringBuilder();
		sb.append(ruleBaseSettingPanel.getErrorMsg());
		return sb.toString();
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.dialog.AbstractDialog#finishButtonActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void finishButtonActionPerformed(ActionEvent event) {
		crawlerRuleBean = new CrawlerRuleBean();
		crawlerRuleBean.setRuleName(ruleBaseSettingPanel.getData().getRuleName());
		crawlerRuleBean.setStatus(this.status);
		crawlerRuleBean.setRuleBaseBean(ruleBaseSettingPanel.getData());
		crawlerRuleBean.setRuleDataBaseBean(ruleDataBaseSettingPanel.getData());
		crawlerRuleBean.setRuleFieldsBean(ruleBaseSettingPanel.getRuleFieldsBean());
		crawlerRuleBean.setRuleType(Constant.YES);
		if(null != ruleDataBaseSettingPanel.getData() && Boolean.valueOf(ruleDataBaseSettingPanel.getData().getSaveToDataBaseFlag()) && "".equals(ruleDataBaseSettingPanel.getData().getPrimaryTable())){
			JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTablePrimaryTableValueIsNotBlank"),
					 LanguageLoader.getString("Common.alertTitle"),
					 JOptionPane.CLOSED_OPTION);
			return;
		}
		if(StringUtils.isBlank(ruleBaseSettingPanel.getData().getDateFormat())){
			JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.docTypesValueIsNotBlank"),
					 LanguageLoader.getString("Common.alertTitle"),
					 JOptionPane.CLOSED_OPTION);
			return;
		}
		if(Constant.OPTION_TYPE_ADD == this.type){
			crawlerRuleBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.LocalRuleTableAddEvent));
			crawlerRuleService.insert(crawlerRuleBean,GatherConstant.SQLMAP_ID_INSERT_CRAWLER_RULE);
		}else if(Constant.OPTION_TYPE_SAVEAS == this.type){
			crawlerRuleBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.LocalRuleTableAddEvent));
			crawlerRuleService.insert(crawlerRuleBean,GatherConstant.SQLMAP_ID_INSERT_CRAWLER_RULE);
		}else{
			crawlerRuleBean.setRuleId(this.ruleId);
			crawlerRuleBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.LocalRuleTableUpdateEvent));
			crawlerRuleService.update(crawlerRuleBean,GatherConstant.SQLMAP_ID_UPDATE_CRAWLER_RULE);
		}
		this.dispose();
	}
	protected void initData(String type) {
		this.type = type;
		JTable ruleTable = ruleListPage.getCrawlerRuleTable();
		if(ruleTable.getSelectedRow() != -1 && Constant.OPTION_TYPE_MODIFY == type){
			CrawlerRuleTabelModel crawlerRuleTabelModel = (CrawlerRuleTabelModel)ruleTable.getModel();
			crawlerRuleBean = crawlerRuleTabelModel.getRowObject(ruleTable.getSelectedRow());
			this.status = crawlerRuleBean.getStatus();
			this.ruleId = crawlerRuleBean.getRuleId();
		}else if(ruleTable.getSelectedRow() != -1 && Constant.OPTION_TYPE_SAVEAS == type){
			CrawlerRuleTabelModel crawlerRuleTabelModel = (CrawlerRuleTabelModel)ruleTable.getModel();
			crawlerRuleBean = crawlerRuleTabelModel.getRowObject(ruleTable.getSelectedRow());
			this.status = crawlerRuleBean.getStatus();
		}else{
			crawlerRuleBean = new CrawlerRuleBean();
			this.status = Constant.TASK_STATUS_STOP;
		}
		fillJTabbedPane();
	}
	public void dispose(){
		super.dispose();
		centerPane = null;
	}
	
}
