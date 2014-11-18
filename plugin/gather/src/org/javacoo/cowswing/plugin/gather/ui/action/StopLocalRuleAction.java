package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.service.ICrawlerService;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.service.LocalFileMonitorServiceImpl;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleCriteria;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerRuleTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.LocalRuleListPage;
import org.springframework.stereotype.Component;

/**
 * 停止执行本地采集任务
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-23 下午2:42:52
 * @version 1.0
 */
@Component("stopLocalRuleAction")
public class StopLocalRuleAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private JTable ruleTable;
	
	private CrawlerRuleTabelModel crawlerRuleTabelModel;
	
	@Resource(name="localRuleListPage")
	private LocalRuleListPage ruleListPage;

	
	/**规则服务类*/
	@Resource(name="crawlerRuleService")
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	

    /**本地文件夹监听*/
	@Resource(name="localFileMonitorService")
	private LocalFileMonitorServiceImpl localFileMonitorService;
	
	
	public StopLocalRuleAction(){
		super(LanguageLoader.getString("Task.stop"),ImageLoader.getImageIcon("CrawlerResource.drive_error"));
		
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ruleTable = ruleListPage.getCrawlerRuleTable();
		if(ruleTable.getSelectedRows().length > 0){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.stopInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerRuleTabelModel = (CrawlerRuleTabelModel)ruleTable.getModel();
				CrawlerRuleBean tempCrawlerRuleBean = null;
				//更新状态
				CrawlerRuleBean crawlerRule = new CrawlerRuleBean();
				crawlerRule.setStatus(Constant.TASK_STATUS_STOP);
				crawlerRule.setEndTime(new Date());
				crawlerRule.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.LocalRuleTableUpdateEvent));
				for(Integer selectRow : ruleTable.getSelectedRows()){
					tempCrawlerRuleBean = crawlerRuleTabelModel.getRowObject(selectRow);
					crawlerRule.setRuleId(tempCrawlerRuleBean.getRuleId());
					crawlerRuleService.update(crawlerRule,GatherConstant.SQLMAP_ID_STOP_CRAWLER_RULE);
					localFileMonitorService.removeListener(tempCrawlerRuleBean.getRuleBaseBean().getUrlRepairUrl());
				}
			}
		}
	}

}
