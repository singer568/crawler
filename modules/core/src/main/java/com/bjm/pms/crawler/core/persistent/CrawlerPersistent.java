package com.bjm.pms.crawler.core.persistent;

import com.bjm.pms.crawler.core.data.Task;


/**
 * 爬虫持久层接口
 * @author javacoo
 * @since 2011-11-12
 */
public interface CrawlerPersistent {
	/**
	 * 保存内容
	 * @param task
	 */
	void save(Task task);
	/**
	 * 完成采集任务
	 * @param id 采集任务ID
	 */
	void finished(Integer id);
	/**
	 * 检查采集任务
	 * 已经采集过 返回true
	 * 
	 * @param isTitle 是否根据标题判断,否则以URL
	 * @param value 值
	 */
	boolean check(boolean isTitle,String value);
}
