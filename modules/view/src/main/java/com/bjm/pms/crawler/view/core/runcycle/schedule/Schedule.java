/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.runcycle.schedule;

/**
 * 运行计划接口
 * <p>说明:</p>
 * <li>执行当前计划</li>
 * <li>取得当前计划名称</li>
 * @author DuanYong
 * @since 2013-6-1 下午8:48:34
 * @version 1.0
 */
public interface Schedule {

	/**
	 * 执行每个运行计划
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-1 下午8:51:38
	 * @version 1.0
	 * @exception
	 */
	void execute();
	/**
	 * 取得运行计划
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-1 下午9:03:55
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	ScheduleEnum getSchedule();
}
