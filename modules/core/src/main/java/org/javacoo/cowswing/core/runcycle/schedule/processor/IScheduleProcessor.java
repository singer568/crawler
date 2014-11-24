/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.runcycle.schedule.processor;

import org.javacoo.cowswing.core.runcycle.schedule.Schedule;

/**
 * 运行计划处理器接口
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-1 下午9:20:07
 * @version 1.0
 */
public interface IScheduleProcessor {
	/**
	 * 执行当前运行计划
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-1 下午9:24:58
	 * @version 1.0
	 * @exception 
	 * @param schedule
	 */
	void execute(Schedule schedule);
	/**
	 * 处理器当前位置
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-1 下午9:25:30
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	int getIndex();

}
