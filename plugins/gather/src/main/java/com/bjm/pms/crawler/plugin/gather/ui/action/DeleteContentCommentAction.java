package com.bjm.pms.crawler.plugin.gather.ui.action;
        
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCommentBean;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerContentCommentTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.content.ContentCommentListPanel;
import com.bjm.pms.crawler.view.base.constant.GatherConstant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;

/**
 * 删除内容
 *@author DuanYong
 *@since 2012-11-5下午9:03:03
 *@version 1.0
 */
@Component("deleteContentCommentAction")
public class DeleteContentCommentAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private JTable contentCommentTable;
	
	private CrawlerContentCommentTabelModel crawlerContentCommentTabelModel;
	
	@Resource(name="contentCommentListPanel")
	private ContentCommentListPanel contentCommentListPanel;
	
	public DeleteContentCommentAction(){
		super(LanguageLoader.getString("RuleList.delete"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleDelete"));
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		contentCommentTable = contentCommentListPanel.getCrawlerContentCommentTable();
		if(contentCommentTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerContentCommentTabelModel = (CrawlerContentCommentTabelModel)contentCommentTable.getModel();
				List<Integer> commentIdList = new ArrayList<Integer>();
				CrawlerContentCommentBean tempCrawlerContentCommentBean = null;
				for(Integer selectRow : contentCommentTable.getSelectedRows()){
					tempCrawlerContentCommentBean = crawlerContentCommentTabelModel.getRowObject(selectRow);
					commentIdList.add(tempCrawlerContentCommentBean.getCommentId());
				}
				doDeleteByCommentIdList(commentIdList);
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
	public void doDeleteByCommentIdList(List<Integer> commentIdList){
		//根据内容ID删除内容分页
		CrawlerContentCommentBean crawlerContentCommentBean = new CrawlerContentCommentBean();
		crawlerContentCommentBean.setCommentIdList(commentIdList);
		crawlerContentCommentBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentCommentTableDeleteEvent));
		contentCommentListPanel.getCrawlerContentCommentService().delete(crawlerContentCommentBean, GatherConstant.SQLMAP_ID_DELETE_CRAWLER_CONTENT_COMMENT);
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
		CrawlerContentCommentBean crawlerContentCommentBean = new CrawlerContentCommentBean();
		crawlerContentCommentBean.setContentIdList(contentIdList);
		crawlerContentCommentBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentCommentTableDeleteEvent));
		contentCommentListPanel.getCrawlerContentCommentService().delete(crawlerContentCommentBean, GatherConstant.SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_CONTENT_COMMENT);
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
		CrawlerContentCommentBean crawlerContentCommentBean = new CrawlerContentCommentBean();
		crawlerContentCommentBean.setRuleIdList(ruleIdList);
		crawlerContentCommentBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentCommentTableDeleteEvent));
		contentCommentListPanel.getCrawlerContentCommentService().delete(crawlerContentCommentBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_COMMENT);
	}

}
