package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerHistoryBean;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerHistoryTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.HistoryListPage;
import com.bjm.pms.crawler.view.base.constant.GatherConstant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;

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
