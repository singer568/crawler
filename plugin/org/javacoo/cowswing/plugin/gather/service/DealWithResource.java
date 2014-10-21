/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.service.ICrawlerService;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentResourceBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentResourceCriteria;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleCriteria;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskCriteria;
import org.javacoo.cowswing.plugin.tool.ui.DealWithImage;

/**
 * 资源处理
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-11 上午11:13:46
 * @version 1.0
 */
public class DealWithResource extends AbstractThreadService{
	/**规则服务类*/
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	/**资源服务类*/
	private ICrawlerService<CrawlerContentResourceBean,CrawlerContentResourceCriteria> crawlerContentResourceService;
	/**规则ID*/
	private Integer ruleId;
	public DealWithResource(Integer ruleId,ICrawlerService<CrawlerContentResourceBean,CrawlerContentResourceCriteria> crawlerContentResourceService,ICrawlerService<CrawlerTaskBean,CrawlerTaskCriteria> crawlerTaskService,ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService){
		super(crawlerTaskService);
		this.ruleId = ruleId;
		this.crawlerRuleService = crawlerRuleService;
		this.crawlerContentResourceService = crawlerContentResourceService;
		this.crawlerTaskService = crawlerTaskService;
	}
	/**
	 * Ftp上传
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午9:36:05
	 * @version 1.0
	 * @exception
	 */
	protected void doRun(){
		CrawlerRuleBean rule = new CrawlerRuleBean();
		rule.setRuleId(ruleId);
		rule = this.crawlerRuleService.get(rule, GatherConstant.SQLMAP_ID_GET_CRAWLER_RULE);
		if(null != rule.getImageSettingBean()){
			CrawlerContentResourceCriteria criteria = new CrawlerContentResourceCriteria();
			criteria.setRuleId(ruleId);
			criteria.setHasDealWith(Constant.NO);
			criteria.setIsLocal(Constant.YES);
			List<CrawlerContentResourceBean> resultList = this.crawlerContentResourceService.getList(criteria,GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_RESOURCE);
			if(CollectionUtils.isNotEmpty(resultList)){
				//插入图片处理任务
				insertTask(ruleId,resultList.size(),GatherConstant.TASK_TYPE_4,CowSwingEventType.DealWithImageStartEvent);
				//休眠，避免锁表
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				String desc = "";
				
				for(CrawlerContentResourceBean crawlerContentResourceBean : resultList){
					rule.getImageSettingBean().setExampleImagePath(Constant.SYSTEM_ROOT_PATH +Constant.getSlash()+ crawlerContentResourceBean.getPath());
					rule.getImageSettingBean().setSavePath("");
					DealWithImage.dealWith(rule.getImageSettingBean());
					desc = "完成图片处理："+crawlerContentResourceBean.getName();
					//修改状态
					updateContentResourceState(crawlerContentResourceBean);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				    updateTask(crawlerContentResourceBean.getRuleId(),desc,GatherConstant.TASK_TYPE_4,CowSwingEventType.DealWithImageStatusChangeEvent);
				}
				deleteTask(this.ruleId,GatherConstant.TASK_TYPE_4,LanguageLoader.getString("CrawlerMainFrame.CrawlDealWithImageComplateTask"),CowSwingEventType.DealWithImageFinishedEvent);
			}
		}
	}
	/**
	 * 修改资源状态
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-17 下午9:38:48
	 * @version 1.0
	 * @exception 
	 * @param crawlerContentResourceBean
	 */
	private void updateContentResourceState(
			CrawlerContentResourceBean crawlerContentResourceBean) {
		CrawlerContentResourceBean contentResourceBean = new CrawlerContentResourceBean();
		contentResourceBean.setResourceId(crawlerContentResourceBean.getResourceId());
		contentResourceBean.setHasDealWith(Constant.YES);
		contentResourceBean.setIsLocal("");
		crawlerContentResourceService.update(contentResourceBean, GatherConstant.SQLMAP_ID_UPDATE_CRAWLER_CONTENT_RESOURCE);
	}

}
