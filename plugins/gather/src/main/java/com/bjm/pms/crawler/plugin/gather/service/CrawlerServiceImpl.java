package com.bjm.pms.crawler.plugin.gather.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bjm.pms.crawler.core.CrawlerController;
import com.bjm.pms.crawler.core.CrawlerService;
import com.bjm.pms.crawler.core.constants.Constants;
import com.bjm.pms.crawler.core.data.CrawlScope;
import com.bjm.pms.crawler.core.filter.BriefAreaFilter;
import com.bjm.pms.crawler.core.filter.CommentAreaFilter;
import com.bjm.pms.crawler.core.filter.CommentFilter;
import com.bjm.pms.crawler.core.filter.CommentIndexFilter;
import com.bjm.pms.crawler.core.filter.CommentLinkFilter;
import com.bjm.pms.crawler.core.filter.ContentAreaFilter;
import com.bjm.pms.crawler.core.filter.FieldFilter;
import com.bjm.pms.crawler.core.filter.Filter;
import com.bjm.pms.crawler.core.filter.LinkAreaFilter;
import com.bjm.pms.crawler.core.filter.PaginationAreaFilter;
import com.bjm.pms.crawler.core.persistent.CrawlerPersistent;
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
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerTaskBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerTaskCriteria;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.constant.GatherConstant;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.view.base.service.beans.ExtendFieldsBean;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
/**
 * 采集控制接口实现类
 * @author javacoo
 * @since 2011-11-02
 * @version 1.0 
 */
@Service("crawlerService")
public class CrawlerServiceImpl implements CrawlerService {
	protected Logger log = Logger.getLogger(this.getClass());
	/**爬虫持久化对象*/
	@Resource(name="crawlerPersistent")
	private CrawlerPersistent crawlerPersistent;
	
	/**规则服务类*/
	@Resource(name="crawlerRuleService")
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	
	/**采集任务服务类*/
	@Resource(name="crawlerTaskService")
	private ICrawlerService<CrawlerTaskBean,CrawlerTaskCriteria> crawlerTaskService;
	/**
	 * 采集内容服务类
	 */
	@Resource(name="crawlerContentService")
	private ICrawlerService<CrawlerContentBean,CrawlerContentCriteria> crawlerContentService;
	/**
	 * 采集内容评论服务类
	 */
	@Resource(name="crawlerContentCommentService")
	private ICrawlerService<CrawlerContentCommentBean,CrawlerContentCommentCriteria> crawlerContentCommentService;
	/**
	 * 采集内容分页服务类
	 */
	@Resource(name="crawlerContentPaginationService")
	private ICrawlerService<CrawlerContentPaginationBean,CrawlerContentPaginationCriteria> crawlerContentPaginationService;
	/**
	 * 采集内容资源服务类
	 */
	@Resource(name="crawlerContentResourceService")
	private ICrawlerService<CrawlerContentResourceBean,CrawlerContentResourceCriteria> crawlerContentResourceService;
	/**扩展字段服务类*/
	@Resource(name="crawlerExtendFieldService")
	private ICrawlerService<CrawlerExtendFieldBean,CrawlerExtendFieldCriteria> crawlerExtendFieldService;
	
	/**存放CrawlController的Map对象*/
	private static ConcurrentHashMap<Integer,CrawlerController> crawlControllerMap = new ConcurrentHashMap<Integer,CrawlerController>();
	

	public boolean start(Integer id,boolean isAuto) {
		CrawlerRuleBean rule = new CrawlerRuleBean();
		rule.setRuleId(id);
		rule = this.getCrawlerRuleService().get(rule, GatherConstant.SQLMAP_ID_GET_CRAWLER_RULE);
		if (Constant.TASK_STATUS_STOP.equals(rule.getStatus())) {
			//先删除原始数据
			doDeleteByRuleId(id);
			//插入任务
			insertTask(rule,isAuto);
			//启动爬虫
			startCrawker(rule);
			//更新规则表
			updateRule(rule.getRuleId(),Constant.TASK_STATUS_RUN,isAuto);
			return true;
		}
		return false;
	}
	/**
	 * 启动爬虫
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-5-12 下午2:26:10
	 * @version 1.0
	 * @exception 
	 * @param rule
	 */
	private void startCrawker(CrawlerRuleBean rule){
		CrawlerController crawlController = new CrawlerController();
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new LinkAreaFilter(rule.getRuleContentBean().getLinksetStart(),rule.getRuleContentBean().getLinksetEnd()));
		filters.add(new ContentAreaFilter(rule.getRuleContentBean().getContentStart(),rule.getRuleContentBean().getContentEnd()));
		filters.add(new BriefAreaFilter(rule.getRuleContentBean().getDescriptionStart(),rule.getRuleContentBean().getDescriptionEnd()));
		filters.add(new PaginationAreaFilter(rule.getRuleContentPageBean().getPaginationStart(),rule.getRuleContentPageBean().getPaginationEnd()));
		filters.add(new CommentIndexFilter(rule.getRuleCommentBean().getCommentIndexStart(),rule.getRuleCommentBean().getCommentIndexEnd()));
		filters.add(new CommentAreaFilter(rule.getRuleCommentBean().getCommentAreaStart(),rule.getRuleCommentBean().getCommentAreaEnd()));
		filters.add(new CommentFilter(rule.getRuleCommentBean().getCommentStart(),rule.getRuleCommentBean().getCommentEnd()));
		filters.add(new CommentLinkFilter(rule.getRuleCommentBean().getCommentLinkStart(),rule.getRuleCommentBean().getCommentLinkEnd()));
		
		
		List<Filter> midFilters = new ArrayList<Filter>();
		//添加过度连接过滤器
		if(null != rule.getRuleContentBean() && CollectionUtils.isNotEmpty(rule.getRuleContentBean().getMidExtendFields())){
			addFilter(rule.getRuleContentBean().getMidExtendFields(),midFilters);
		}
		
		
		List<Filter> multeityFilters = new ArrayList<Filter>();
		//添加扩展字段过滤器
		if(null != rule.getRuleFieldsBean() && CollectionUtils.isNotEmpty(rule.getRuleFieldsBean().getExtendFields())){
			addFilter(rule.getRuleFieldsBean().getExtendFields(),multeityFilters);
		}
		CrawlScope crawlScope = new CrawlScope();
		crawlScope.setCrawlerPersistent(this.getCrawlerPersistent());
		crawlScope.setEncoding(rule.getRuleBaseBean().getPageEncoding());
		crawlScope.setId(rule.getRuleId());
		crawlScope.setFilterList(filters);
		crawlScope.setMidFilterList(midFilters);
		crawlScope.setMulteityFilterList(multeityFilters);
		//评论内容列表是否与内容页分离，如果填写了,则为true
		if(null != rule.getRuleCommentBean() && StringUtils.isNotEmpty(rule.getRuleCommentBean().getCommentIndexStart())){
			crawlScope.setCommentListIsAlone(true);
		}
		crawlScope.setRepairPageUrl(rule.getRuleBaseBean().getUrlRepairUrl());
		crawlScope.setRepairImageUrl(rule.getRuleBaseBean().getResourceRepairUrl());
		crawlScope.setPaginationRepairUrl(rule.getRuleBaseBean().getPaginationRepairUrl());
		//设置休眠时间
		crawlScope.setSleepTime(rule.getRuleBaseBean().getPauseTime());
		//是否下载图片至本地
		crawlScope.setExtractContentRes(Boolean.valueOf(rule.getRuleBaseBean().getSaveResourceFlag()));
		//是否去掉内容中连接
		crawlScope.setReplaceHtmlLink(Boolean.valueOf(rule.getRuleBaseBean().getReplaceLinkFlag()));
		crawlScope.setAllowRepeat(Boolean.valueOf(rule.getRuleBaseBean().getRepeatCheckFlag()));
		crawlScope.setUseProxy(Boolean.valueOf(rule.getRuleBaseBean().getUseProxyFlag()));
		crawlScope.setGatherOrder(Boolean.valueOf(rule.getRuleBaseBean().getGatherOrderFlag()));
		
		crawlScope.setProxyAddress(rule.getRuleBaseBean().getProxyAddress());
		crawlScope.setProxyPort(rule.getRuleBaseBean().getProxyPort());
		crawlScope.setReplaceWords(rule.getRuleBaseBean().getReplaceWords());

		//随机生成日期
		crawlScope.setDateFormat(rule.getRuleBaseBean().getDateFormat());
		crawlScope.setRandomDateFlag(Boolean.valueOf(rule.getRuleBaseBean().getRandomDateFlag()));
		crawlScope.setStartRandomDate(rule.getRuleBaseBean().getStartRandomDate());
		crawlScope.setEndRandomDate(rule.getRuleBaseBean().getEndRandomDate());
		crawlScope.setGatherNum(rule.getRuleBaseBean().getGatherNum());
		
		crawlScope.addSeeds(rule.getRuleContentBean().getAllPlans());
		
		crawlController.initialize(crawlScope);
		crawlController.start();
		crawlControllerMap.put(rule.getRuleId(), crawlController);
	}
	private void addFilter(List<ExtendFieldsBean> extendFields,List<Filter> filters){
		for(ExtendFieldsBean extendFieldsBean : extendFields){
			filters.add(new FieldFilter(extendFieldsBean.getFields(),extendFieldsBean.getFilterStart(),extendFieldsBean.getFilterEnd()));
		}
	}
	public boolean stop(Integer id) {
		CrawlerController crawlController = crawlControllerMap.get(id);
		if(null != crawlController && (Constants.CRAWL_STATE_RUNNING.equals(crawlController.getState()) || Constants.CRAWL_STATE_PAUSE.equals(crawlController.getState()))){
			crawlController.shutdownNow();
		}else{
			CrawlerRuleBean rule = new CrawlerRuleBean();
			rule.setRuleId(id);
			rule = this.getCrawlerRuleService().get(rule, GatherConstant.SQLMAP_ID_GET_CRAWLER_RULE);
			if (!Constant.TASK_STATUS_STOP.equals(rule.getStatus())) {
				//更新规则表
				updateRule(rule.getRuleId(),Constant.TASK_STATUS_STOP,false);
				return true;
			}
		}
		return true;
	}
	public boolean pause(Integer id) {
		CrawlerController crawlController = crawlControllerMap.get(id);
		if(null != crawlController && Constants.CRAWL_STATE_RUNNING.equals(crawlController.getState())){
			crawlController.pause();
		}
		return true;
	}
	public boolean resume(Integer id) {
		CrawlerController crawlController = crawlControllerMap.get(id);
		if(null != crawlController && Constants.CRAWL_STATE_PAUSE.equals(crawlController.getState())){
			crawlController.resume();
		}
		return false;
	}
	
	
	
	/**
	 * 插入任务
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-5-12 下午1:21:34
	 * @version 1.0
	 * @exception 
	 * @param crawlerRuleBean
	 */
	private void insertTask(CrawlerRuleBean crawlerRuleBean,boolean isAuto){
		//插入任务
		CrawlerTaskBean crawlerTaskBean = new CrawlerTaskBean();
		crawlerTaskBean.setRuleId(crawlerRuleBean.getRuleId());
		crawlerTaskBean.setType(GatherConstant.TASK_TYPE_1);
		crawlerTaskBean.setStatus(Constant.TASK_STATUS_RUN);
		crawlerTaskBean.setTotal(crawlerRuleBean.getRuleContentBean().getAllPlans().length);
		if(!isAuto){
			crawlerTaskBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.TaskStartEvent));
			
		}
		crawlerTaskService.insert(crawlerTaskBean, GatherConstant.SQLMAP_ID_INSERT_CRAWLER_TASK);
	}
	/**
	 * 更新规则表
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-5-12 下午1:27:48
	 * @version 1.0
	 * @exception 
	 * @param ruleId
	 */
	private void updateRule(Integer ruleId,String status,boolean isAuto){
		List<Integer> ruleIdList = new ArrayList<Integer>();
		ruleIdList.add(ruleId);
		//更新状态
		CrawlerRuleBean crawlerRule = new CrawlerRuleBean();
		crawlerRule.setStatus(status);
		crawlerRule.setStartTime(new Date());
		crawlerRule.setRuleIdList(ruleIdList);
		if(!isAuto){
			crawlerRule.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.RuleTableUpdateEvent));
		}
		crawlerRuleService.update(crawlerRule,GatherConstant.SQLMAP_ID_START_CRAWLER_RULE);
	}
	/**
	 * 根据规则ID删除内容相关数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2013-5-12 上午9:52:28
	 * @param contentIdList
	 * @return void
	 */
	public void doDeleteByRuleId(Integer ruleId){
		List<Integer> ruleIdList = new ArrayList<Integer>();
		ruleIdList.add(ruleId);
		//根据规则ID删除评论
		CrawlerContentCommentBean crawlerContentCommentBean = new CrawlerContentCommentBean();
		crawlerContentCommentBean.setRuleIdList(ruleIdList);
		crawlerContentCommentBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentCommentTableDeleteEvent));
		crawlerContentCommentService.delete(crawlerContentCommentBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_COMMENT);
		//根据规则ID删除内容分页
		CrawlerContentPaginationBean crawlerContentPaginationBean = new CrawlerContentPaginationBean();
		crawlerContentPaginationBean.setRuleIdList(ruleIdList);
		crawlerContentPaginationBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentPaginationTableDeleteEvent));
		crawlerContentPaginationService.delete(crawlerContentPaginationBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_PAGINATION);
		//根据规则ID删除内容资源
		CrawlerContentResourceBean crawlerContentResourceBean = new CrawlerContentResourceBean();
		crawlerContentResourceBean.setRuleIdList(ruleIdList);
		crawlerContentResourceBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentResourceTableDeleteEvent));
		crawlerContentResourceService.delete(crawlerContentResourceBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_RESOURCE);
		
		//根据规则ID删除扩展字段
		CrawlerExtendFieldBean crawlerExtendFieldBean = new CrawlerExtendFieldBean();
		crawlerExtendFieldBean.setRuleIdList(ruleIdList);
		crawlerExtendFieldBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentExtendFieldDeleteEvent));
		crawlerExtendFieldService.delete(crawlerExtendFieldBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_EXTEND_FIELD);
		
		//根据规则ID删除内容
		CrawlerContentBean crawlerContentBean = new CrawlerContentBean();
		crawlerContentBean.setRuleIdList(ruleIdList);
		crawlerContentBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ContentTableDeleteEvent));
		crawlerContentService.delete(crawlerContentBean,GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT);
		
		//根据规则ID删除
		CrawlerTaskBean crawlerTaskBean = new CrawlerTaskBean();
		crawlerTaskBean.setRuleId(ruleId);
		crawlerTaskService.delete(crawlerTaskBean, GatherConstant.SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_TASK);
	}
	
	
	
	
	public CrawlerPersistent getCrawlerPersistent() {
		return crawlerPersistent;
	}
	public void setCrawlerPersistent(CrawlerPersistent crawlerPersistent) {
		this.crawlerPersistent = crawlerPersistent;
	}
	
	public ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> getCrawlerRuleService() {
		return crawlerRuleService;
	}
	public void setCrawlerRuleService(
			ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService) {
		this.crawlerRuleService = crawlerRuleService;
	}
	
	
}
