/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.runcycle;

import org.javacoo.cowswing.core.runcycle.schedule.Schedule;


/**
 * CowSwing运行周期接口
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-1 下午7:33:55
 * @version 1.0
 */
public interface ICowSwingRunCycle {
	/**
	 * 开始运行周期
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-1 下午7:56:35
	 * @version 1.0
	 * @exception
	 */
	void start();
	/**
	 * 结束运行周期
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-1 下午7:56:59
	 * @version 1.0
	 * @exception
	 */
	void end();
	/**
	 * 取得当前运行计划
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-1 下午8:55:28
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	Schedule getCurrentSchedule();

}
