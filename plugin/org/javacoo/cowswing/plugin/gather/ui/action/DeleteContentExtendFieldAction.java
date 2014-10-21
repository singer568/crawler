/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
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
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerExtendFieldBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerContentExtendFieldTableModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.content.ContentExtendFieldListPanel;
import org.springframework.stereotype.Component;

/**
 *@author DuanYong
 *@since 2013-3-25上午10:58:15
 *@version 1.0
 */
@Component("deleteContentExtendFieldAction")
public class DeleteContentExtendFieldAction extends AbstractAction{
private static final long serialVersionUID = 1L;
	
	private JTable contentExtendFieldTable;
	
	private CrawlerContentExtendFieldTableModel crawlerContentExtendFieldTableModel;
	@Resource(name="contentExtendFieldListPanel")
	private ContentExtendFieldListPanel contentExtendFieldListPanel;
	
	public DeleteContentExtendFieldAction(){
		super(LanguageLoader.getString("RuleList.delete"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleDelete"));
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		contentExtendFieldTable = contentExtendFieldListPanel.getCrawlerContentExtendFieldTable();
		if(contentExtendFieldTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerContentExtendFieldTableModel = (CrawlerContentExtendFieldTableModel)contentExtendFieldTable.getModel();
				List<Integer> extendFieldIdList = new ArrayList<Integer>();
				CrawlerExtendFieldBean crawlerExtendFieldBean = null;
				for(Integer selectRow : contentExtendFieldTable.getSelectedRows()){
					crawlerExtendFieldBean = crawlerContentExtendFieldTableModel.getRowObject(selectRow);
					extendFieldIdList.add(crawlerExtendFieldBean.getExtendFieldId());
				}
				doDeleteByExtendFieldIdList(extendFieldIdList);
			}
		}
	}
	public void doDeleteByExtendFieldIdList(List<Integer> extendFieldIdList){
		//根据内容ID删除内容分页
		CrawlerExtendFieldBean crawlerExtendFieldBean = new CrawlerExtendFieldBean();
		crawlerExtendFieldBean.setExtendFieldIdList(extendFieldIdList);
		crawlerExtendFieldBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentExtendFieldDeleteEvent));
		contentExtendFieldListPanel.getCrawlerExtendFieldService().delete(crawlerExtendFieldBean, GatherConstant.SQLMAP_ID_DELETE_CRAWLER_EXTEND_FIELD);
	}
	
	public void doDeleteByRuleIdList(List<Integer> ruleIdList){
		//根据内容ID删除内容分页
		CrawlerExtendFieldBean crawlerExtendFieldBean = new CrawlerExtendFieldBean();
		crawlerExtendFieldBean.setRuleIdList(ruleIdList);
		crawlerExtendFieldBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentExtendFieldDeleteEvent));
		contentExtendFieldListPanel.getCrawlerExtendFieldService().delete(crawlerExtendFieldBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_EXTEND_FIELD);
	}
	
}
