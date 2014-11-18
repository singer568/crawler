package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerRuleTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.LocalRuleListPage;
import org.springframework.stereotype.Component;

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