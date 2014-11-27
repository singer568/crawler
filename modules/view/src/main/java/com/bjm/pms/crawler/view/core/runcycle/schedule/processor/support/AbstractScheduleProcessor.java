/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.runcycle.schedule.processor.support;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.core.runcycle.schedule.Schedule;
import com.bjm.pms.crawler.view.core.runcycle.schedule.processor.IScheduleProcessor;

/**
 * 运行计划处理器抽象实现类
 * <p>
 * 说明:
 * </p>
 * <li>定义了execute方法的流程，主体执行流程在doExecute方法中实现</li>
 * <li>如果需要新开线程，则将isNewThread设置为true,反之为false</li>
 * <li>此抽象类同时实现了Comparable接口,根据index的大小实现排序</li>
 * @author DuanYong
 * @since 2013-6-1 下午10:28:32
 * @version 1.0
 */
public abstract class AbstractScheduleProcessor implements IScheduleProcessor,
		Comparable<IScheduleProcessor> {
	protected Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @see ccb.cddev.p2.core.lifecycle.phase.processor.PhaseProcessor#execute()
	 */
	@Override
	public void execute(final Schedule schedule) {
		// 如果是要新开线程
		if (this.isNewThread()) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					doExecute(schedule);
				}
			});
			// 将当前线程设置为守护线程
			thread.setDaemon(false);
			// 设置线程默认名字，建议子类覆写该方法
			thread.setName(this.getThreadName());
			thread.start();
		} else {
			doExecute(schedule);
		}
	}

	/**
	 * 新开线程的name
	 * 
	 * @return 线程名
	 */
	protected String getThreadName() {
		return "defaultThreadName";
	}

	/**
	 * 主要执行方法
	 */
	protected abstract void doExecute(Schedule schedule);

	/**
	 * 是否新开线程
	 * 
	 * @return true/false
	 */
	protected abstract boolean isNewThread();

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(IScheduleProcessor arg0) {
		if (arg0 == null) {
			return -1;
		}
		return new Integer(this.getIndex()).compareTo(new Integer(arg0
				.getIndex()));
	}
}
