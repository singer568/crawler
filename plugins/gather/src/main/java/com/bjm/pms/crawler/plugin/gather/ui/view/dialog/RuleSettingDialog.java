package com.bjm.pms.crawler.plugin.gather.ui.view.dialog;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.constant.GatherConstant;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleCriteria;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerRuleTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.RuleListPage;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule.RuleBaseSettingPanel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule.RuleCommentSettingPanel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule.RuleContentPageSettingPanel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule.RuleContentSettingPanel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule.RuleDataBaseSettingPanel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule.RuleExtendFieldsSettingPanel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule.RuleFtpSettingPanel;
import com.bjm.pms.crawler.plugin.tool.ui.view.panel.ImageSettingPanel;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.core.event.CowSwingListener;
import com.bjm.pms.crawler.view.ui.view.dialog.AbstractDialog;


/**
 * 爬虫采集规则参数设置窗口
 * 
 * @author javacoo
 * @since 2012-03-14
 */
@Component("ruleSettingDialog")
public class RuleSettingDialog extends AbstractDialog implements CowSwingListener{
	private static final long serialVersionUID = 1L;

	/**
	 * 采集规则服务类
	 */
	@Resource(name="crawlerRuleService")
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	/**
	 * 采集规则列表页面
	 */
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;
	
	@Resource(name="ruleBaseSettingPanel")
	private RuleBaseSettingPanel ruleBaseSettingPanel;
	@Resource(name="ruleContentSettingPanel")
	private RuleContentSettingPanel ruleContentSettingPanel;
	@Resource(name="ruleContentPageSettingPanel")
	private RuleContentPageSettingPanel ruleContentPageSettingPanel;
	@Resource(name="ruleCommentSettingPanel")
	private RuleCommentSettingPanel ruleCommentSettingPanel;
	@Resource(name="ruleExtendFieldsSettingPanel")
	private RuleExtendFieldsSettingPanel ruleExtendFieldsSettingPanel;
	@Resource(name="ruleDataBaseSettingPanel")
	private RuleDataBaseSettingPanel ruleDataBaseSettingPanel;
	@Resource(name="ruleFtpSettingPanel")
	private RuleFtpSettingPanel ruleFtpSettingPanel;
	@Resource(name="imageSettingPanel")
    private ImageSettingPanel imageSettingPanel;
	
	private CrawlerRuleBean crawlerRuleBean;
	private Integer ruleId;
	private String status;
	private String type;
	public RuleSettingDialog(){
		super();
	}
	
	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
			logger.info("初始化配置参数面板");
			jTabbedPane.addTab(LanguageLoader.getString("RuleContentSetting.base"), ruleBaseSettingPanel);
			jTabbedPane.addTab(LanguageLoader.getString("RuleContentSetting.contentInfo"), ruleContentSettingPanel);
			jTabbedPane.addTab(LanguageLoader.getString("RuleContentSetting.pagination"), ruleContentPageSettingPanel);
			jTabbedPane.addTab(LanguageLoader.getString("RuleContentSetting.comment"), ruleCommentSettingPanel);
			jTabbedPane.addTab(LanguageLoader.getString("RuleContentSetting.extndFields"), ruleExtendFieldsSettingPanel);
			jTabbedPane.addTab(LanguageLoader.getString("RuleContentSetting.dataBaseSet"), ruleDataBaseSettingPanel);
			jTabbedPane.addTab(LanguageLoader.getString("RuleContentSetting.ftpSet"), ruleFtpSettingPanel);
			jTabbedPane.addTab(LanguageLoader.getString("RuleContentSetting.imagesSet"), imageSettingPanel);
			imageSettingPanel.init();
			imageSettingPanel.disableSavePath();
			imageSettingPanel.getImageSettingBean().setExampleImagePath(ImageLoader.getImagePath("CrawlerResource.example"));
			imageSettingPanel.fillComponentData(imageSettingPanel.getImageSettingBean());
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
		ruleContentSettingPanel.initData(crawlerRuleBean.getRuleContentBean());
		ruleContentPageSettingPanel.initData(crawlerRuleBean.getRuleContentPageBean());
		ruleCommentSettingPanel.initData(crawlerRuleBean.getRuleCommentBean());
		ruleExtendFieldsSettingPanel.initData(crawlerRuleBean.getRuleFieldsBean());
		ruleDataBaseSettingPanel.initData(crawlerRuleBean.getRuleDataBaseBean(),crawlerRuleBean);
		ruleFtpSettingPanel.initData(crawlerRuleBean.getCrawlerFtpConfigBean());
		if(null != crawlerRuleBean.getImageSettingBean()){
			imageSettingPanel.fillComponentData(crawlerRuleBean.getImageSettingBean());
			imageSettingPanel.repaint();
		}
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
		sb.append(ruleContentSettingPanel.getErrorMsg());
		sb.append(ruleContentPageSettingPanel.getErrorMsg());
		sb.append(ruleCommentSettingPanel.getErrorMsg());
		sb.append(ruleExtendFieldsSettingPanel.getErrorMsg());
		sb.append(ruleFtpSettingPanel.getErrorMsg());
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
		crawlerRuleBean.setRuleContentBean(ruleContentSettingPanel.getData());
		crawlerRuleBean.setRuleContentPageBean(ruleContentPageSettingPanel.getData());
		crawlerRuleBean.setRuleCommentBean(ruleCommentSettingPanel.getData());
		crawlerRuleBean.setRuleFieldsBean(ruleExtendFieldsSettingPanel.getData());
		crawlerRuleBean.setRuleDataBaseBean(ruleDataBaseSettingPanel.getData());
		crawlerRuleBean.setCrawlerFtpConfigBean(ruleFtpSettingPanel.getData());
		crawlerRuleBean.setImageSettingBean(imageSettingPanel.getImageSettingBean());
		if(null != ruleDataBaseSettingPanel.getData() && Boolean.valueOf(ruleDataBaseSettingPanel.getData().getSaveToDataBaseFlag()) && "".equals(ruleDataBaseSettingPanel.getData().getPrimaryTable())){
			JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTablePrimaryTableValueIsNotBlank"),
					 LanguageLoader.getString("Common.alertTitle"),
					 JOptionPane.CLOSED_OPTION);
			return;
		}
		if(Constant.OPTION_TYPE_ADD == this.type){
			crawlerRuleBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.RuleTableAddEvent));
			crawlerRuleService.insert(crawlerRuleBean,GatherConstant.SQLMAP_ID_INSERT_CRAWLER_RULE);
		}else if(Constant.OPTION_TYPE_SAVEAS == this.type){
			crawlerRuleBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.RuleTableAddEvent));
			crawlerRuleService.insert(crawlerRuleBean,GatherConstant.SQLMAP_ID_INSERT_CRAWLER_RULE);
		}else{
			crawlerRuleBean.setRuleId(this.ruleId);
			crawlerRuleBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.RuleTableUpdateEvent));
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

	/**
	 * @return the ruleBaseSettingPanel
	 */
	public RuleBaseSettingPanel getRuleBaseSettingPanel() {
		return ruleBaseSettingPanel;
	}
	
}
