package com.bjm.pms.crawler.plugin.gather.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bjm.pms.crawler.core.data.Task;
import com.bjm.pms.crawler.core.data.uri.CrawlResURI;
import com.bjm.pms.crawler.core.persistent.CrawlerPersistent;
import com.bjm.pms.crawler.persist.PaginationSupport;
import com.bjm.pms.crawler.persist.util.DBConnectionManager;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCommentBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCommentCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentPaginationBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentPaginationCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentResourceBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentResourceCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerExtendFieldBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerExtendFieldCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerHistoryBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerHistoryCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerTaskBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerTaskCriteria;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.constant.GatherConstant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.view.base.utils.DateFormatUtils;
import com.bjm.pms.crawler.view.base.utils.DateUtil;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.core.event.CowSwingListener;
import com.bjm.pms.crawler.view.core.event.CowSwingObserver;


/**
 * 爬虫持久层接口实现类
 * @author javacoo
 * @since 2011-11-12
 */
@Service("crawlerPersistent")
public class SimpleCrawlerPersistentImpl implements CrawlerPersistent,CowSwingListener{
	protected Logger log = Logger.getLogger(this.getClass());
	/**任务服务类*/
	@Resource(name="crawlerTaskService")
	private ICrawlerService<CrawlerTaskBean,CrawlerTaskCriteria> crawlerTaskService;
	/**规则服务类*/
	@Resource(name="crawlerRuleService")
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	/**采集内容服务类*/
	@Resource(name="crawlerContentService")
	private ICrawlerService<CrawlerContentBean,CrawlerContentCriteria> crawlerContentService;
	/**采集内容评论服务类*/
	@Resource(name="crawlerContentCommentService")
	private ICrawlerService<CrawlerContentCommentBean,CrawlerContentCommentCriteria> crawlerContentCommentService;
	/**采集内容分页服务类*/
	@Resource(name="crawlerContentPaginationService")
	private ICrawlerService<CrawlerContentPaginationBean,CrawlerContentPaginationCriteria> crawlerContentPaginationService;
	/**采集内容资源服务类*/
	@Resource(name="crawlerContentResourceService")
	private ICrawlerService<CrawlerContentResourceBean,CrawlerContentResourceCriteria> crawlerContentResourceService;
	/**扩展字段服务类*/
	@Resource(name="crawlerExtendFieldService")
	private ICrawlerService<CrawlerExtendFieldBean,CrawlerExtendFieldCriteria> crawlerExtendFieldService;
	/**采集历史*/
	@Resource(name="crawlerHistoryService")
	private ICrawlerService<CrawlerHistoryBean,CrawlerHistoryCriteria> crawlerHistoryService;
	/**数据库连接管理*/
	private DBConnectionManager connectionManager;
	
	public SimpleCrawlerPersistentImpl(){
		CowSwingObserver.getInstance().addCrawlerListener(this);
	}
	/**
	 * 保存内容
	 * @param task
	 */
	public synchronized void save(Task task) {
		CrawlerRuleBean rule = new CrawlerRuleBean();
		rule.setRuleId(task.getController().getCrawlScope().getId());
		rule = this.getCrawlerRuleService().get(rule, GatherConstant.SQLMAP_ID_GET_CRAWLER_RULE);
		//插入内容
		insertContent(task,rule);
		//更新任务表
		String desc = DateUtil.dateToStr( new Date(), "yyyy-MM-dd HH:mm:ss")+" "+LanguageLoader.getString("CrawlerMainFrame.CrawlGatherComplate") +" "+ task.getContentBean().getTitle();
		updateTask(task.getController().getCrawlScope().getId(),task.getController().getFrontier().getTaskSize(),desc,GatherConstant.TASK_TYPE_1,CowSwingEventType.TaskStatusChangeEvent);
	}
	/**
	 * 更新任务表
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午3:56:37
	 * @version 1.0
	 * @exception 
	 * @param ruleId 规则ID
	 * @param total 总数
	 * @param type 任务类型
	 * @param CowSwingEventType 事件类型
	 */
	private void updateTask(int ruleId,int total,String desc,String type,CowSwingEventType CowSwingEventType){
		CrawlerTaskBean crawlerTaskBean = new CrawlerTaskBean();
		crawlerTaskBean.setRuleId(ruleId);
		crawlerTaskBean.setType(type);
		crawlerTaskBean = getCrawlerTaskService().get(crawlerTaskBean,GatherConstant.SQLMAP_ID_GET_BY_RULEID_CRAWLER_TASK);
		crawlerTaskBean.setTotal(total);
		crawlerTaskBean.setDesc(desc);
		crawlerTaskBean.setComplete(crawlerTaskBean.getComplete() + 1);
		crawlerTaskBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType,crawlerTaskBean));
		getCrawlerTaskService().update(crawlerTaskBean, GatherConstant.SQLMAP_ID_UPDATE_COMPLETE_CRAWLER_TASK);
	}
	
	
    /**
     * 插入内容及相关内容
     * <p>方法说明:</p>
     * @auther DuanYong
     * @since 2012-11-28 上午10:24:21
     * @param task
     * @return void
     */
	private void insertContent(Task task,CrawlerRuleBean rule) {

		//组装返回信息
		StringBuilder info = null;
        // 组装内容
		CrawlerContentBean crawlerContentBean = new CrawlerContentBean();
		crawlerContentBean.setContent(task.getContentBean().getContent());
		crawlerContentBean.setRuleId(task.getController().getCrawlScope().getId());
		crawlerContentBean.setSaveDate(new Date());
		crawlerContentBean.setViewDate(getRandomDate(rule));
		crawlerContentBean.setTitle(task.getContentBean().getTitle());
		crawlerContentBean.setBrief(task.getContentBean().getBrief());
		crawlerContentBean.setTitleImg(task.getContentBean().getTitleImg());
		info = new StringBuilder();
		info.append(task.getController().getCrawlScope().getId());
		info.append(":");
		info.append(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
		info.append("  ");
		info.append(LanguageLoader.getString("CrawlerMainFrame.CrawlGatherComplateContent"));
		info.append(crawlerContentBean.getTitle());
		crawlerContentBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentTableAddEvent,info.toString()));
		//插入内容
		int contentId = getCrawlerContentService().insert(crawlerContentBean,GatherConstant.SQLMAP_ID_INSERT_CRAWLER_CONTENT);
		crawlerContentBean.setContentId(contentId);
		
		//插入评论
		if(CollectionUtils.isNotEmpty(task.getContentBean().getCommentList())){
			CrawlerContentCommentBean crawlerContentCommentBean = null;
			for(String comment : task.getContentBean().getCommentList()){
				crawlerContentCommentBean = new CrawlerContentCommentBean();
				crawlerContentCommentBean.setContent(comment);
				crawlerContentCommentBean.setRuleId(task.getController().getCrawlScope().getId());
				crawlerContentCommentBean.setContentId(crawlerContentBean.getContentId());
				info = new StringBuilder();
				info.append(task.getController().getCrawlScope().getId());
				info.append(":");
				info.append(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
				info.append("  ");
				info.append(LanguageLoader.getString("CrawlerMainFrame.CrawlGatherComplateComment"));
				info.append(comment);
				crawlerContentCommentBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentCommentTableAddEvent,info.toString()));
				getCrawlerContentCommentService().insert(crawlerContentCommentBean, GatherConstant.SQLMAP_ID_INSERT_CRAWLER_CONTENT_COMMENT);
			}
		}
		//插入分页内容
		if(CollectionUtils.isNotEmpty(task.getContentBean().getContentList())){
			CrawlerContentPaginationBean crawlerContentPaginationBean = null;
			for(String content : task.getContentBean().getContentList()){
				crawlerContentPaginationBean = new CrawlerContentPaginationBean();
				crawlerContentPaginationBean.setContent(content);
				crawlerContentPaginationBean.setRuleId(task.getController().getCrawlScope().getId());
				crawlerContentPaginationBean.setContentId(crawlerContentBean.getContentId());
				info = new StringBuilder();
				info.append(task.getController().getCrawlScope().getId());
				info.append(":");
				info.append(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
				info.append("  ");
				info.append(LanguageLoader.getString("CrawlerMainFrame.CrawlGatherComplateContentPage"));
				info.append(content);
				crawlerContentPaginationBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentPaginationTableAddEvent,info.toString()));
				getCrawlerContentPaginationService().insert(crawlerContentPaginationBean, GatherConstant.SQLMAP_ID_INSERT_CRAWLER_CONTENT_PAGINATION);
			}
		}
		//插入资源
		if(CollectionUtils.isNotEmpty(task.getContentBean().getResCrawlURIList())){
			CrawlerContentResourceBean crawlerContentResourceBean = null;
			for(CrawlResURI crawlResURI : task.getContentBean().getResCrawlURIList()){
				if(StringUtils.isNotBlank(crawlResURI.getUrl())){
					crawlerContentResourceBean = new CrawlerContentResourceBean();
					crawlerContentResourceBean.setName(FilenameUtils.getName(crawlResURI.getUrl()));
					crawlerContentResourceBean.setPath(crawlResURI.getUrl());
					crawlerContentResourceBean.setRuleId(task.getController().getCrawlScope().getId());
					crawlerContentResourceBean.setType(FilenameUtils.getExtension(crawlResURI.getUrl()));
					crawlerContentResourceBean.setContentId(crawlerContentBean.getContentId());
					if(crawlResURI.getUrl().toLowerCase().startsWith(Constant.HTTP) || crawlResURI.getUrl().toLowerCase().startsWith(Constant.HTTPS)){
						crawlerContentResourceBean.setIsLocal(Constant.NO);	
					}else{
						crawlerContentResourceBean.setIsLocal(Constant.YES);
					}
					
					info = new StringBuilder();
					info.append(task.getController().getCrawlScope().getId());
					info.append(":");
					info.append(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
					info.append("  ");
					info.append(LanguageLoader.getString("CrawlerMainFrame.CrawlGatherComplateRes"));
					info.append(crawlResURI.getOriginResUrl());
					crawlerContentResourceBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentResourceTableAddEvent,info.toString()));
					getCrawlerContentResourceService().insert(crawlerContentResourceBean, GatherConstant.SQLMAP_ID_INSERT_CRAWLER_CONTENT_RESOURCE);
				}
			}
		}
		//插入扩展字段
		if(null != task.getContentBean().getFieldValueMap() && !task.getContentBean().getFieldValueMap().isEmpty()){
			String key = "";
			CrawlerExtendFieldBean crawlerExtendFieldBean = null;
			for(Iterator<String> it = task.getContentBean().getFieldValueMap().keySet().iterator();it.hasNext();){
				key = it.next();
				crawlerExtendFieldBean = new CrawlerExtendFieldBean();
				crawlerExtendFieldBean.setRuleId(task.getController().getCrawlScope().getId());
				crawlerExtendFieldBean.setContentId(contentId);
				crawlerExtendFieldBean.setFieldName(key);
				crawlerExtendFieldBean.setFieldValue(task.getContentBean().getFieldValueMap().get(key));
				info = new StringBuilder();
				info.append(task.getController().getCrawlScope().getId());
				info.append(":");
				info.append(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
				info.append("  ");
				info.append(LanguageLoader.getString("CrawlerMainFrame.CrawlGatherComplateExtendField"));
				info.append(key);
				crawlerExtendFieldBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentExtendFieldAddEvent,info.toString()));
				crawlerExtendFieldService.insert(crawlerExtendFieldBean, GatherConstant.SQLMAP_ID_INSERT_CRAWLER_EXTEND_FIELD);
			}
		}
		//检查历史表中是否已经存在
		if(!checkHistory(task)){
			//插入采集历史
			CrawlerHistoryBean crawlerHistoryBean = new CrawlerHistoryBean();
			crawlerHistoryBean.setContentId(contentId);
			crawlerHistoryBean.setRuleId(task.getController().getCrawlScope().getId());
			crawlerHistoryBean.setDate(new Date());
			crawlerHistoryBean.setTitle(task.getContentBean().getTitle());
			crawlerHistoryBean.setUrl(task.getCrawlURI().getUrl());
			crawlerHistoryBean.setDescription(task.getContentBean().getBrief());
			//组装返回信息
			info = new StringBuilder();
			info.append(LanguageLoader.getString("CrawlerMainFrame.CrawlGatherComplate"));
			info.append(crawlerHistoryBean.getTitle());
			info.append(",");
			info.append(LanguageLoader.getString("RuleList.end_time"));
			info.append(":");
			info.append(DateUtil.dateToStr(crawlerHistoryBean.getDate(), "yyyy-MM-dd HH:mm:ss"));
			crawlerHistoryBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.RuleHistoryTableAddEvent,info.toString()));
			crawlerHistoryService.insert(crawlerHistoryBean, GatherConstant.SQLMAP_ID_INSERT_CRAWLER_CONTENT_HISTORY);
		}
	}
	/**
	 * 检查任务是否已经存在于历史表中
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-3-15 下午10:38:17
	 * @param task
	 * @return
	 * @return boolean
	 */
    private boolean checkHistory(Task task){
    	boolean isExist = false;
    	CrawlerHistoryCriteria q = new CrawlerHistoryCriteria();
		q.setTitle(task.getContentBean().getTitle());
		q.setUrl(task.getCrawlURI().getUrl());
		PaginationSupport<CrawlerHistoryBean> result = crawlerHistoryService.getPaginatedList(q, GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_HISTORY);
		if(CollectionUtils.isNotEmpty(result.getData())){
			isExist = true;
		}
		return isExist;
    }

	/**
	 * 完成采集任务
	 * @param id 采集任务ID
	 */
	public synchronized void finished(Integer id) {
		log.info("完成采集任务--"+id);
		CrawlerTaskBean crawlerTaskBean = new CrawlerTaskBean();
		crawlerTaskBean.setRuleId(id);
		crawlerTaskBean.setType(GatherConstant.TASK_TYPE_1);
		crawlerTaskBean = getCrawlerTaskService().get(crawlerTaskBean, GatherConstant.SQLMAP_ID_GET_BY_RULEID_CRAWLER_TASK);
		//删除采集任务
		deleteTask(crawlerTaskBean);
		
		//采集任务完成,更新规则表
		CrawlerRuleBean crawlerRuleBean = new CrawlerRuleBean();
		crawlerRuleBean.setRuleId(id);
		crawlerRuleBean.setStatus(Constant.TASK_STATUS_STOP);
		crawlerRuleBean.setTotalItem(crawlerTaskBean.getComplete());
		crawlerRuleBean.setEndTime(new Date());
		//组装返回信息
		StringBuilder info = new StringBuilder();
		info.append(LanguageLoader.getString("CrawlerMainFrame.CrawlGatherComplateTask"));
		info.append(crawlerTaskBean.getRuleName());
		info.append(",");
		info.append(LanguageLoader.getString("RuleList.total"));
		info.append(":");
		info.append(crawlerTaskBean.getComplete());
		info.append(",");
		info.append(LanguageLoader.getString("RuleList.end_time"));
		info.append(":");
		info.append(DateUtil.dateToStr(crawlerRuleBean.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
		crawlerRuleBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.RuleTableChangeEvent,info.toString()));
		getCrawlerRuleService().update(crawlerRuleBean, GatherConstant.SQLMAP_ID_STOP_CRAWLER_RULE);
		//处理资源
		//dealWithResource(id);
		//上传资源
		//uploadResource(id);
		//保存内容至远程数据库
		//uploadContent(id);
	}
	/**
	 * 处理资源
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-11 上午11:12:30
	 * @version 1.0
	 * @exception 
	 * @param id
	 */
	private void dealWithResource(Integer id) {
		//开启线程
		Thread currThread = new Thread(new DealWithResource(id,this.getCrawlerContentResourceService(),this.getCrawlerTaskService(),this.getCrawlerRuleService()));
		currThread.start();
	}
	/**
	 * 将指定规则的内容保存到远程数据库
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午9:06:13
	 * @version 1.0
	 * @exception 
	 * @param ruleId
	 */
	private void uploadContent(Integer ruleId){
		//开启线程
		Thread currThread = new Thread(new SaveRemoteContent(crawlerRuleService,
				crawlerContentService,
				crawlerContentCommentService,
				crawlerContentPaginationService,
				crawlerExtendFieldService,
				this.getCrawlerTaskService(),
				crawlerContentResourceService,
				this.getConnectionManager(),
				ruleId));
			currThread.start();
	}
	
	/**
	 * 删除任务
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-29 下午5:00:20
	 * @version 1.0
	 * @exception 
	 * @param ruleId
	 */
	private void deleteTask(CrawlerTaskBean crawlerTaskBean){
		crawlerTaskBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.TaskFinishedEvent,crawlerTaskBean.getRuleId()));
		getCrawlerTaskService().delete(crawlerTaskBean, GatherConstant.SQLMAP_ID_DELETE_BY_TASKID_CRAWLER_TASK);
	}
	/**
	 * 上传资源
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-24 下午11:47:33
	 * @version 1.0
	 * @exception 
	 * @param ruleId
	 */
	private void uploadResource(Integer ruleId){
		//开启线程
		Thread currThread = new Thread(new FtpUploadResource(ruleId,this.getCrawlerContentResourceService(),this.getCrawlerTaskService(),this.getCrawlerRuleService()));
		currThread.start();
	}
	/**
	 * 检查采集任务
	 * 已经采集过 返回true
	 * 
	 * @param isTitle 是否根据标题判断,否则以URL
	 * @param value 值
	 */
	public synchronized boolean check(boolean isTitle,String value){
		CrawlerHistoryCriteria criteria = new CrawlerHistoryCriteria();
		if(isTitle){
			criteria.setTitle(value);
		}else{
			criteria.setUrl(value);
		}
		List<CrawlerHistoryBean> result = crawlerHistoryService.getList(criteria, GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_HISTORY);
		if(CollectionUtils.isNotEmpty(result)){
			return true;
		}
		return false;
	}
	private Date getRandomDate(CrawlerRuleBean rule){
		Date viewDate = new Date();
		if(Boolean.valueOf(rule.getRuleBaseBean().getRandomDateFlag())){
			String format = StringUtils.isNotBlank(rule.getRuleBaseBean().getDateFormat()) ? rule.getRuleBaseBean().getDateFormat() : "yyyy-MM-dd";
			SimpleDateFormat dt = new SimpleDateFormat(format);
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			String statrDate = StringUtils.isNotBlank(rule.getRuleBaseBean().getStartRandomDate()) ? rule.getRuleBaseBean().getStartRandomDate() : Constant.DEFAULT_START_DATE;
			String endDate = StringUtils.isNotBlank(rule.getRuleBaseBean().getEndRandomDate()) ? rule.getRuleBaseBean().getEndRandomDate() : dft.format(new java.util.Date());
			viewDate = DateFormatUtils.randomDate(statrDate, endDate);
			String tempDate = dt.format(viewDate);
			viewDate = DateUtil.strToDate(tempDate,format);
		}
		return viewDate;
	}


    
	public ICrawlerService<CrawlerTaskBean, CrawlerTaskCriteria> getCrawlerTaskService() {
		return crawlerTaskService;
	}
	public void setCrawlerTaskService(
			ICrawlerService<CrawlerTaskBean, CrawlerTaskCriteria> crawlerTaskService) {
		this.crawlerTaskService = crawlerTaskService;
	}
	public ICrawlerService<CrawlerRuleBean, CrawlerRuleCriteria> getCrawlerRuleService() {
		return crawlerRuleService;
	}
	public void setCrawlerRuleService(
			ICrawlerService<CrawlerRuleBean, CrawlerRuleCriteria> crawlerRuleService) {
		this.crawlerRuleService = crawlerRuleService;
	}
	public ICrawlerService<CrawlerContentBean, CrawlerContentCriteria> getCrawlerContentService() {
		return crawlerContentService;
	}
	public void setCrawlerContentService(
			ICrawlerService<CrawlerContentBean, CrawlerContentCriteria> crawlerContentService) {
		this.crawlerContentService = crawlerContentService;
	}
	public ICrawlerService<CrawlerContentCommentBean, CrawlerContentCommentCriteria> getCrawlerContentCommentService() {
		return crawlerContentCommentService;
	}
	public void setCrawlerContentCommentService(
			ICrawlerService<CrawlerContentCommentBean, CrawlerContentCommentCriteria> crawlerContentCommentService) {
		this.crawlerContentCommentService = crawlerContentCommentService;
	}
	public ICrawlerService<CrawlerContentPaginationBean, CrawlerContentPaginationCriteria> getCrawlerContentPaginationService() {
		return crawlerContentPaginationService;
	}
	public void setCrawlerContentPaginationService(
			ICrawlerService<CrawlerContentPaginationBean, CrawlerContentPaginationCriteria> crawlerContentPaginationService) {
		this.crawlerContentPaginationService = crawlerContentPaginationService;
	}
	public ICrawlerService<CrawlerContentResourceBean, CrawlerContentResourceCriteria> getCrawlerContentResourceService() {
		return this.crawlerContentResourceService;
	}
	public void setCrawlerContentResourceService(
			ICrawlerService<CrawlerContentResourceBean, CrawlerContentResourceCriteria> crawlerContentResourceService) {
		this.crawlerContentResourceService = crawlerContentResourceService;
	}
	
	public ICrawlerService<CrawlerExtendFieldBean, CrawlerExtendFieldCriteria> getCrawlerExtendFieldService() {
		return crawlerExtendFieldService;
	}
	public void setCrawlerExtendFieldService(
			ICrawlerService<CrawlerExtendFieldBean, CrawlerExtendFieldCriteria> crawlerExtendFieldService) {
		this.crawlerExtendFieldService = crawlerExtendFieldService;
	}
	public ICrawlerService<CrawlerHistoryBean, CrawlerHistoryCriteria> getCrawlerHistoryService() {
		return crawlerHistoryService;
	}
	public void setCrawlerHistoryService(
			ICrawlerService<CrawlerHistoryBean, CrawlerHistoryCriteria> crawlerHistoryService) {
		this.crawlerHistoryService = crawlerHistoryService;
	}
	public DBConnectionManager getConnectionManager() {
		if(null == connectionManager){
			connectionManager = DBConnectionManager.getInstance();
		}
		return connectionManager;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.event.CowSwingListener#update(org.javacoo.cowswing.core.event.CowSwingEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		//采集完成，开启入库任务
		if (CowSwingEventType.TaskFinishedEvent == event.getEventType()) {
			Object obj = event.getEventObject();
			if(null != obj){
				//保存内容至远程数据库
				uploadContent(Integer.valueOf(obj.toString()));
			}
		}else if (CowSwingEventType.SaveFinishedEvent == event.getEventType()) {//入库完成，开启图片处理
			Object obj = event.getEventObject();
			if(null != obj){
				//处理资源
				dealWithResource(Integer.valueOf(obj.toString()));
			}
		}else if (CowSwingEventType.DealWithImageFinishedEvent == event.getEventType()) {//图片处理完成，开启FTP上传
			Object obj = event.getEventObject();
			if(null != obj){
				//上传资源
				uploadResource(Integer.valueOf(obj.toString()));
			}
		}
	}
	
	
}

