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
import org.javacoo.cowswing.base.utils.DateUtil;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.core.event.CowSwingObserver;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskCriteria;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerRuleTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.RuleListPage;
import org.springframework.stereotype.Component;

/**
 * 后续处理
 *@author DuanYong
 *@since 2012-11-17下午9:09:54
 *@version 1.0
 */
@Component("dealWithRuleAction")
public class DealWithRuleAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private JTable ruleTable;
	
	private CrawlerRuleTabelModel crawlerRuleTabelModel;
	
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;

	/**采集任务服务类*/
	@Resource(name="crawlerTaskService")
	private ICrawlerService<CrawlerTaskBean,CrawlerTaskCriteria> crawlerTaskService;
	
	public DealWithRuleAction(){
		super(LanguageLoader.getString("RuleList.dealWith"),ImageLoader.getImageIcon("CrawlerResource.toolbarDisk"));
		
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ruleTable = ruleListPage.getCrawlerRuleTable();
		if(ruleTable.getSelectedRows().length > 0){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.dealWithInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerRuleTabelModel = (CrawlerRuleTabelModel)ruleTable.getModel();
				CrawlerRuleBean tempCrawlerRuleBean = null;
				ruleListPage.showTabPanel(Constant.SYSTEM_TABPANEL_INDEX_SAVE);
				for(Integer selectRow : ruleTable.getSelectedRows()){
					tempCrawlerRuleBean = crawlerRuleTabelModel.getRowObject(selectRow);
					deleteTask(tempCrawlerRuleBean.getRuleId());
					CowSwingObserver.getInstance().notifyEvents(new CowSwingEvent(this,CowSwingEventType.TaskFinishedEvent,tempCrawlerRuleBean.getRuleId()));
				}
			}
		}
	}
	/**
	 * 删除该规则下的任务
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-22 下午2:33:39
	 * @version 1.0
	 * @exception 
	 * @param ruleId
	 */
	private void deleteTask(Integer ruleId){
		CrawlerTaskBean crawlerTaskBean = new CrawlerTaskBean();
		crawlerTaskBean.setRuleId(ruleId);
		this.crawlerTaskService.delete(crawlerTaskBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_TASK);
	}

}
