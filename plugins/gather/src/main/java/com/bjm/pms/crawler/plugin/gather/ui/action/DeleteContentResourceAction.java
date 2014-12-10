package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentResourceBean;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerContentResourceTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.content.ContentResourceListPanel;
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
@Component("deleteContentResourceAction")
public class DeleteContentResourceAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private JTable contentResourceTable;
	
	private CrawlerContentResourceTabelModel crawlerContentResourceTabelModel;
	
	@Resource(name="contentResourceListPanel")
	private ContentResourceListPanel contentResourceListPanel;
	
	public DeleteContentResourceAction(){
		super(LanguageLoader.getString("RuleList.delete"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleDelete"));
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		contentResourceTable = contentResourceListPanel.getCrawlerContentResourceTable();
		if(contentResourceTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerContentResourceTabelModel = (CrawlerContentResourceTabelModel)contentResourceTable.getModel();
				List<Integer> resourceIdList = new ArrayList<Integer>();
				CrawlerContentResourceBean tempCrawlerContentResourceBean = null;
				for(Integer selectRow : contentResourceTable.getSelectedRows()){
					tempCrawlerContentResourceBean = crawlerContentResourceTabelModel.getRowObject(selectRow);
					resourceIdList.add(tempCrawlerContentResourceBean.getResourceId());
				}
				doDeleteByResourceIdList(resourceIdList);
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
	public void doDeleteByResourceIdList(List<Integer> ResourceIdList){
		//根据内容ID删除内容分页
		CrawlerContentResourceBean crawlerContentResourceBean = new CrawlerContentResourceBean();
		crawlerContentResourceBean.setResourceIdList(ResourceIdList);
		crawlerContentResourceBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentResourceTableDeleteEvent));
		contentResourceListPanel.getCrawlerContentResourceService().delete(crawlerContentResourceBean, GatherConstant.SQLMAP_ID_DELETE_CRAWLER_CONTENT_RESOURCE);
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
		CrawlerContentResourceBean crawlerContentResourceBean = new CrawlerContentResourceBean();
		crawlerContentResourceBean.setContentIdList(contentIdList);
		crawlerContentResourceBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentResourceTableDeleteEvent));
		contentResourceListPanel.getCrawlerContentResourceService().delete(crawlerContentResourceBean, GatherConstant.SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_CONTENT_RESOURCE);
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
		CrawlerContentResourceBean crawlerContentResourceBean = new CrawlerContentResourceBean();
		crawlerContentResourceBean.setRuleIdList(ruleIdList);
		crawlerContentResourceBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentResourceTableDeleteEvent));
		contentResourceListPanel.getCrawlerContentResourceService().delete(crawlerContentResourceBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_RESOURCE);
	}

}
