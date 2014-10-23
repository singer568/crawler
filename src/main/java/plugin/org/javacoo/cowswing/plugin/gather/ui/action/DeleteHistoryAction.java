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
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerHistoryBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerHistoryTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.HistoryListPage;
import org.springframework.stereotype.Component;

/**
 * 删除采集历史
 *@author DuanYong
 *@since 2012-11-5下午9:03:03
 *@version 1.0
 */
@Component("deleteHistoryAction")
public class DeleteHistoryAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private JTable historyTable;
	
	private CrawlerHistoryTabelModel crawlerHistoryTabelModel;
	
	@Resource(name="historyListPage")
	private HistoryListPage historyListPage;
	
	public DeleteHistoryAction(){
		super(LanguageLoader.getString("RuleList.delete"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleDelete"));
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		historyTable = historyListPage.getCrawlerHistoryTable();
		if(historyTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerHistoryTabelModel = (CrawlerHistoryTabelModel)historyTable.getModel();
				List<Integer> historyIdList = new ArrayList<Integer>();
				CrawlerHistoryBean tempCrawlerContentHistoryBean = null;
				for(Integer selectRow : historyTable.getSelectedRows()){
					tempCrawlerContentHistoryBean = crawlerHistoryTabelModel.getRowObject(selectRow);
					historyIdList.add(tempCrawlerContentHistoryBean.getHistoryId());
				}
				CrawlerHistoryBean crawlerContentHistoryBean = new CrawlerHistoryBean();
				crawlerContentHistoryBean.setHistoryIdList(historyIdList);
				crawlerContentHistoryBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.RuleHistoryTableDeleteEvent));
				historyListPage.getCrawlerHistoryService().delete(crawlerContentHistoryBean,GatherConstant.SQLMAP_ID_DELETE_CRAWLER_CONTENT_HISTORY);
			}
		}
	}

}
