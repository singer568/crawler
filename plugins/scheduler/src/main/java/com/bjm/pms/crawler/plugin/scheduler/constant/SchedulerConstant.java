/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.scheduler.constant;

/**
 * 定时任务常量定义
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-17 下午3:15:00
 * @version 1.0
 */
public class SchedulerConstant {
	/**
	 * ---------------------定时任务信息SQLMAP----------------------------------------
	 * -----
	 */

	public static final String SQLMAP_ID_INSERT_CRAWLER_SCHEDULING = "insertCrawlerScheduler";// 插入定时任务信息SQLMAP

	public static final String SQLMAP_ID_UPDATE_CRAWLER_SCHEDULING = "updateCrawlerScheduler";// 修改定时任务信息SQLMAP

	public static final String SQLMAP_ID_LIST_CRAWLER_SCHEDULING = "getCrawlerSchedulerList";// 采集定时任务信息SQLMAP

	public static final String SQLMAP_ID_DELETE_CRAWLER_SCHEDULING = "deleteCrawlerScheduler";// 删除定时任务信息SQLMAP

	public static final String SQLMAP_ID_GET_CRAWLER_SCHEDULING = "getCrawlerSchedulerById";// 取得定时任务信息SQLMAP
	
	private SchedulerConstant(){}

}
