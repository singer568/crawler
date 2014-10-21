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
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentPaginationBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerContentPaginationTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.content.ContentPaginationListPanel;
import org.springframework.stereotype.Component;

/**
 * 删除内容
 *@author DuanYong
 *@since 2012-11-5下午9:03:03
 *@version 1.0
 */
@Component("deleteContentPaginationAction")
public class DeleteContentPaginationAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private JTable contentPaginationTable;
	
	private CrawlerContentPaginationTabelModel crawlerContentPaginationTabelModel;
	
	@Resource(name="contentPaginationListPanel")
	private ContentPaginationListPanel contentPaginationListPanel;
	
	public DeleteContentPaginationAction(){
		super(LanguageLoader.getString("RuleList.delete"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleDelete"));
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		contentPaginationTable = contentPaginationListPanel.getCrawlerContentPaginationTable();
		if(contentPaginationTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerContentPaginationTabelModel = (CrawlerContentPaginationTabelModel)contentPaginationTable.getModel();
				List<Integer> paginationIdList = new ArrayList<Integer>();
				CrawlerContentPaginationBean tempCrawlerContentPaginationBean = null;
				for(Integer selectRow : contentPaginationTable.getSelectedRows()){
					tempCrawlerContentPaginationBean = crawlerContentPaginationTabelModel.getRowObject(selectRow);
					paginationIdList.add(tempCrawlerContentPaginationBean.getPaginationId());
				}
				doDeleteByPaginationIdList(paginationIdList);
			}
		}
	}
	/**
	 * 根据内容分页ID集合删除相关数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-5 上午9:52:28
	 * @param contentIdList
	 * @return void
	 */
	public void doDeleteByPaginationIdList(List<Integer> paginationIdList){
		//根据内容ID删除内容分页
		CrawlerContentPaginationBean crawlerContentPaginationBean = new CrawlerContentPaginationBean();
		crawlerContentPaginationBean.setPaginationIdList(paginationIdList);
		crawlerContentPaginationBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentPaginationTableDeleteEvent));
		contentPaginationListPanel.getCrawlerContentPaginationService().delete(crawlerContentPaginationBean, GatherConstant.SQLMAP_ID_DELETE_CRAWLER_CONTENT_PAGINATION);
	}
	/**
	 * 根据内容ID集合删除相关数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-5 上午9:52:28
	 * @param contentIdList
	 * @return void
	 */
	public void doDeleteByContentIdList(List<Integer> contentIdList){
		//根据内容ID删除内容分页
		CrawlerContentPaginationBean crawlerContentPaginationBean = new CrawlerContentPaginationBean();
		crawlerContentPaginationBean.setContentIdList(contentIdList);
		crawlerContentPaginationBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentPaginationTableDeleteEvent));
		contentPaginationListPanel.getCrawlerContentPaginationService().delete(crawlerContentPaginationBean, GatherConstant.SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_CONTENT_PAGINATION);
	}
	/**
	 * 根据规则ID集合删除内容相关数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-5 上午9:52:28
	 * @param contentIdList
	 * @return void
	 */
	public void doDeleteByRuleIdList(List<Integer> ruleIdList){
		//根据规则ID删除内容分页
		CrawlerContentPaginationBean crawlerContentPaginationBean = new CrawlerContentPaginationBean();
		crawlerContentPaginationBean.setRuleIdList(ruleIdList);
		crawlerContentPaginationBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentPaginationTableDeleteEvent));
		contentPaginationListPanel.getCrawlerContentPaginationService().delete(crawlerContentPaginationBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_PAGINATION);
	}

}
