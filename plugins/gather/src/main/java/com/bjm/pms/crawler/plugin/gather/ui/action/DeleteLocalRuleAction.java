package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerRuleTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.LocalRuleListPage;
import com.bjm.pms.crawler.view.base.constant.GatherConstant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;

/**
 * 删除本地采集规则
 *@author DuanYong
 *@since 2012-11-5下午9:03:03
 *@version 1.0
 */
@Component("deleteLocalRuleAction")
public class DeleteLocalRuleAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private JTable ruleTable;
	
	private CrawlerRuleTabelModel crawlerRuleTabelModel;
	
	@Resource(name="localRuleListPage")
	private LocalRuleListPage ruleListPage;
	@Resource(name="deleteContentAction")
	private DeleteContentAction deleteContentAction;
	
	public DeleteLocalRuleAction(){
		super(LanguageLoader.getString("RuleList.delete"),ImageLoader.getImageIcon("CrawlerResource.drive_delete"));
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ruleTable = ruleListPage.getCrawlerRuleTable();
		if(ruleTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerRuleTabelModel = (CrawlerRuleTabelModel)ruleTable.getModel();
				List<Integer> ruleIdList = new ArrayList<Integer>();
				CrawlerRuleBean tempCrawlerRuleBean = null;
				for(Integer selectRow : ruleTable.getSelectedRows()){
					tempCrawlerRuleBean = crawlerRuleTabelModel.getRowObject(selectRow);
					ruleIdList.add(tempCrawlerRuleBean.getRuleId());
				}
				//根据规则ID集合删除内容相关数据
				deleteContentAction.doDeleteByRuleIdList(ruleIdList);
				//根据规则ID删除规则
				CrawlerRuleBean crawlerRule = new CrawlerRuleBean();
				crawlerRule.setRuleIdList(ruleIdList);
				crawlerRule.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.LocalRuleTableDeleteEvent));
				ruleListPage.getCrawlerRuleService().delete(crawlerRule,GatherConstant.SQLMAP_ID_DELETE_CRAWLER_RULE);
			}
		}
	}

}
