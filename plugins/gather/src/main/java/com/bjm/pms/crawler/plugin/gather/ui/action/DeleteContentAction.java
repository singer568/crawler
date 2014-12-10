package com.bjm.pms.crawler.plugin.gather.ui.action;
        
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCommentBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentPaginationBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentResourceBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerExtendFieldBean;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerContentTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.ContentListPage;
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
@Component("deleteContentAction")
public class DeleteContentAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private JTable contentTable;
	
	private CrawlerContentTabelModel crawlerContentTabelModel;
	
	@Resource(name="contentListPage")
	private ContentListPage contentListPage;
	
	public DeleteContentAction(){
		super(LanguageLoader.getString("RuleList.delete"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleDelete"));
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		contentTable = contentListPage.getCrawlerContentTable();
		if(contentTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerContentTabelModel = (CrawlerContentTabelModel)contentTable.getModel();
				List<Integer> contentIdList = new ArrayList<Integer>();
				CrawlerContentBean tempCrawlerContentBean = null;
				for(Integer selectRow : contentTable.getSelectedRows()){
					tempCrawlerContentBean = crawlerContentTabelModel.getRowObject(selectRow);
					contentIdList.add(tempCrawlerContentBean.getContentId());
				}
				doDeleteByContentIdList(contentIdList);
			}
		}
	}
	/**
	 * 根据内容ID集合删除内容相关数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-5 上午9:52:28
	 * @param contentIdList
	 * @return void
	 */
	public void doDeleteByContentIdList(List<Integer> contentIdList){
		//根据内容ID删除评论
		CrawlerContentCommentBean crawlerContentCommentBean = new CrawlerContentCommentBean();
		crawlerContentCommentBean.setContentIdList(contentIdList);
		crawlerContentCommentBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentCommentTableDeleteEvent));
		contentListPage.getCrawlerContentCommentService().delete(crawlerContentCommentBean, GatherConstant.SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_CONTENT_COMMENT);
		//根据内容ID删除内容分页
		CrawlerContentPaginationBean crawlerContentPaginationBean = new CrawlerContentPaginationBean();
		crawlerContentPaginationBean.setContentIdList(contentIdList);
		crawlerContentPaginationBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentPaginationTableDeleteEvent));
		contentListPage.getCrawlerContentPaginationService().delete(crawlerContentPaginationBean, GatherConstant.SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_CONTENT_PAGINATION);
		//根据内容ID删除内容资源
		CrawlerContentResourceBean crawlerContentResourceBean = new CrawlerContentResourceBean();
		crawlerContentResourceBean.setContentIdList(contentIdList);
		crawlerContentResourceBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentResourceTableDeleteEvent));
		contentListPage.getCrawlerContentResourceService().delete(crawlerContentResourceBean, GatherConstant.SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_CONTENT_RESOURCE);
		//根据内容ID删除扩展字段
		CrawlerExtendFieldBean crawlerExtendFieldBean = new CrawlerExtendFieldBean();
		crawlerExtendFieldBean.setContentIdList(contentIdList);
		crawlerExtendFieldBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentExtendFieldDeleteEvent));
		contentListPage.getCrawlerExtendFieldService().delete(crawlerExtendFieldBean, GatherConstant.SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_EXTEND_FIELD);
		
		//根据内容ID删除内容
		CrawlerContentBean crawlerContentBean = new CrawlerContentBean();
		crawlerContentBean.setContentIdList(contentIdList);
		crawlerContentBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentTableDeleteEvent));
		contentListPage.getCrawlerContentService().delete(crawlerContentBean,GatherConstant.SQLMAP_ID_DELETE_CRAWLER_CONTENT);
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
		//根据规则ID删除评论
		CrawlerContentCommentBean crawlerContentCommentBean = new CrawlerContentCommentBean();
		crawlerContentCommentBean.setRuleIdList(ruleIdList);
		crawlerContentCommentBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentCommentTableDeleteEvent));
		contentListPage.getCrawlerContentCommentService().delete(crawlerContentCommentBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_COMMENT);
		//根据规则ID删除内容分页
		CrawlerContentPaginationBean crawlerContentPaginationBean = new CrawlerContentPaginationBean();
		crawlerContentPaginationBean.setRuleIdList(ruleIdList);
		crawlerContentPaginationBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentPaginationTableDeleteEvent));
		contentListPage.getCrawlerContentPaginationService().delete(crawlerContentPaginationBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_PAGINATION);
		//根据规则ID删除内容资源
		CrawlerContentResourceBean crawlerContentResourceBean = new CrawlerContentResourceBean();
		crawlerContentResourceBean.setRuleIdList(ruleIdList);
		crawlerContentResourceBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentResourceTableDeleteEvent));
		contentListPage.getCrawlerContentResourceService().delete(crawlerContentResourceBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_RESOURCE);
		
		//根据规则ID删除扩展字段
		CrawlerExtendFieldBean crawlerExtendFieldBean = new CrawlerExtendFieldBean();
		crawlerExtendFieldBean.setRuleIdList(ruleIdList);
		crawlerExtendFieldBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentExtendFieldDeleteEvent));
		contentListPage.getCrawlerExtendFieldService().delete(crawlerExtendFieldBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_EXTEND_FIELD);
		
		//根据规则ID删除内容
		CrawlerContentBean crawlerContentBean = new CrawlerContentBean();
		crawlerContentBean.setRuleIdList(ruleIdList);
		crawlerContentBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentTableDeleteEvent));
		contentListPage.getCrawlerContentService().delete(crawlerContentBean,GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT);
	}

}
