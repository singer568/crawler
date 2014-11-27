package org.javacoo.crawler.core;

/**
 * 采集控制接口
 *@author DuanYong
 *@since 2012-11-24下午7:39:03
 *@version 1.0
 */
public interface CrawlerService {
	/***
	 * 开始采集
	 * @param id 任务ID
	 * @param isAuto 是否是自动采集
	 * @return
	 */
	public boolean start(Integer id,boolean isAuto);
	/***
	 * 停止采集
	 * @param id 任务ID
	 * @return
	 */
	public boolean stop(Integer id);
	/***
	 * 暂停采集
	 * @param id 任务ID
	 * @return
	 */
	public boolean pause(Integer id);
	/***
	 * 继续采集
	 * @param id 任务ID
	 * @return
	 */
	public boolean resume(Integer id);
}
