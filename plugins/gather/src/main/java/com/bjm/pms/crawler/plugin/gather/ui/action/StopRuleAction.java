package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.core.CrawlerService;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerRuleTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.RuleListPage;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;

/**
 * 停止执行任务
 *@author DuanYong
 *@since 2012-11-17下午9:09:54
 *@version 1.0
 */
@Component("stopRuleAction")
public class StopRuleAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private JTable ruleTable;
	
	private CrawlerRuleTabelModel crawlerRuleTabelModel;
	
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;

	
	@Resource(name="crawlerService")
	private CrawlerService crawlerService;
	
	
	
	public StopRuleAction(){
		super(LanguageLoader.getString("Task.stop"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleError"));
		
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
				for(Integer selectRow : ruleTable.getSelectedRows()){
					tempCrawlerRuleBean = crawlerRuleTabelModel.getRowObject(selectRow);
					crawlerService.stop(tempCrawlerRuleBean.getRuleId());
				}
			}
		}
	}

}
