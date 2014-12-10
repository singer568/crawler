/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerTaskBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerTaskCriteria;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.constant.GatherConstant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.base.utils.DateUtil;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;

/**
 * 抽象线程服务类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-30 下午10:47:35
 * @version 1.0
 */
public abstract class AbstractThreadService implements Runnable{
	protected Logger log = Logger.getLogger(this.getClass());
	/**任务服务*/
	protected ICrawlerService<CrawlerTaskBean,CrawlerTaskCriteria> crawlerTaskService;
	
	public AbstractThreadService(ICrawlerService<CrawlerTaskBean,CrawlerTaskCriteria> crawlerTaskService){
		this.crawlerTaskService = crawlerTaskService;
	}
	/**
	 * 执行方法
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午11:02:05
	 * @version 1.0
	 * @exception
	 */
	protected abstract void doRun();
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public final void run() {
		doRun();
	}
	/**
	 * 插入任务
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午9:10:53
	 * @version 1.0
	 * @exception 
	 * @param ruleId 规则ID
	 * @param total 总数
	 * @param type 任务类型
	 * @param crawlerEventType 任务监听事件
	 */
	protected void insertTask(Integer ruleId,Integer total,String type,CowSwingEventType crawlerEventType){
		//插入任务
		CrawlerTaskBean crawlerTaskBean = new CrawlerTaskBean();
		crawlerTaskBean.setRuleId(ruleId);
		crawlerTaskBean.setType(type);
		crawlerTaskBean.setStatus(Constant.TASK_STATUS_RUN);
		crawlerTaskBean.setTotal(total);
		crawlerTaskBean.setCowSwingEvent(new CowSwingEvent(this,crawlerEventType));
		//插入
		this.crawlerTaskService.insert(crawlerTaskBean, GatherConstant.SQLMAP_ID_INSERT_CRAWLER_TASK);
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
	 * @param desc 描述
	 * @param type 任务类型
	 * @param crawlerEventType 事件类型
	 */
	protected void updateTask(int ruleId,String desc,String type,CowSwingEventType crawlerEventType){
		CrawlerTaskBean crawlerTaskBean = new CrawlerTaskBean();
		crawlerTaskBean.setRuleId(ruleId);
		crawlerTaskBean.setType(type);
		crawlerTaskBean = this.crawlerTaskService.get(crawlerTaskBean,GatherConstant.SQLMAP_ID_GET_BY_RULEID_CRAWLER_TASK);
		crawlerTaskBean.setDesc(desc);
		crawlerTaskBean.setComplete(crawlerTaskBean.getComplete() + 1);
		crawlerTaskBean.setCowSwingEvent(new CowSwingEvent(this,crawlerEventType,crawlerTaskBean));
		this.crawlerTaskService.update(crawlerTaskBean, GatherConstant.SQLMAP_ID_UPDATE_COMPLETE_CRAWLER_TASK);
	}
	/**
	 * 删除任务
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午10:53:02
	 * @version 1.0
	 * @exception 
	 * @param ruleId 规则ID
	 * @param type 任务类型
	 * @param title 标题
	 * @param crawlerEventType 事件类型
	 */
	protected void deleteTask(Integer ruleId,String type,String title,CowSwingEventType crawlerEventType){
		CrawlerTaskBean crawlerTaskBean = new CrawlerTaskBean();
		crawlerTaskBean.setRuleId(ruleId);
		crawlerTaskBean.setType(type);
		crawlerTaskBean = this.crawlerTaskService.get(crawlerTaskBean, GatherConstant.SQLMAP_ID_GET_BY_RULEID_CRAWLER_TASK);
		//组装返回信息
		StringBuilder info = new StringBuilder();
		info.append(title);
		info.append(crawlerTaskBean.getRuleName());
		info.append(",");
	    info.append(LanguageLoader.getString("RuleList.total"));
		info.append(":");
		info.append(crawlerTaskBean.getComplete());
		info.append(",");
		info.append(LanguageLoader.getString("RuleList.end_time"));
		info.append(":");
	    info.append(DateUtil.dateToStr( new Date(), "yyyy-MM-dd HH:mm:ss"));
		crawlerTaskBean.setCowSwingEvent(new CowSwingEvent(this,crawlerEventType,ruleId,info.toString()));
		this.crawlerTaskService.delete(crawlerTaskBean, GatherConstant.SQLMAP_ID_DELETE_BY_TASKID_CRAWLER_TASK);
	}
}
