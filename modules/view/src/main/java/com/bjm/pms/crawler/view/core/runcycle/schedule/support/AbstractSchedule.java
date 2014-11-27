/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.runcycle.schedule.support;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.core.runcycle.schedule.Schedule;
import com.bjm.pms.crawler.view.core.runcycle.schedule.processor.IScheduleProcessor;

/**
 * 运行计划 抽象实现类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-1 下午9:05:35
 * @version 1.0
 */
public abstract class AbstractSchedule<T extends IScheduleProcessor> implements Schedule{
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public void execute() {
        //取出当前的processors并执行
        for (T t : getScheduleProcessors()) {
            t.execute(this);
        }
    }
	/**
	 * 取得执行计划处理器集合
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-1 下午9:32:04
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	protected abstract Collection<T> getScheduleProcessors();
}
