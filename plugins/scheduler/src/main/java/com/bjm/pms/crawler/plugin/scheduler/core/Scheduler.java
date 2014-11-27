package com.bjm.pms.crawler.plugin.scheduler.core;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
/**
 * 计划框架-任务调度
 * <li>
 * 用于提供必要的计划,Scheduler 的每一个实例都拥有 Timer 的一个实例，用于提供底层计划
 * 它将一组单次定时器串接在一起，以便在由 ScheduleIterator 指定的各个时间执行 SchedulerTask 类
 * </li>
 * @author javacoo
 * @since 2011-11-02
 */
public class Scheduler {
	/**Timer实例*/
	private final Timer timer = new Timer();
	/**
	 * 定时任务计划
	 * @author javacoo
	 * @since 2011-11-02
	 */
	class SchedulerTimerTask extends TimerTask {
		private SchedulerTask schedulerTask;
		private ScheduleIterator iterator;

		public SchedulerTimerTask(SchedulerTask schedulerTask,
				ScheduleIterator iterator) {
			this.schedulerTask = schedulerTask;
			this.iterator = iterator;
		}

		public void run() {
			schedulerTask.run();
			reschedule(schedulerTask, iterator);
		}
	}

	public Scheduler() {
	}
    /**
     * 取消执行
     */
	public void cancel() {
		timer.cancel();
	}
	/**
	 * 计划的入口点
	 * <li>
	 * 通过调用 ScheduleIterator 接口的 next()，发现第一次执行 SchedulerTask 的时间。
	 * 然后通过调用底层 Timer 类的单次 schedule() 方法，启动计划在这一时刻执行。
	 * 为单次执行提供的 TimerTask 对象是嵌入的 SchedulerTimerTask 类的一个实例，
	 * 它包装了任务和迭代器（iterator）。在指定的时间，调用嵌入类的 run() 方法，
	 * 它使用包装的任务和迭代器引用以便重新计划任务的下一次执行
	 * </li>
	 * @param schedulerTask SchedulerTimerTask 类的一个实例
	 * @param iterator ScheduleIterator 接口的一个实例
	 */
	public void schedule(SchedulerTask schedulerTask, ScheduleIterator iterator) {
		Date time = iterator.next();
		if (time == null) {
			schedulerTask.cancel();
		} else {
			synchronized (schedulerTask.lock) {
				if (schedulerTask.state != SchedulerTask.VIRGIN) {
					throw new IllegalStateException("任务已经执行/取消");
				}
				schedulerTask.state = SchedulerTask.SCHEDULED;
				schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask,iterator);
				timer.schedule(schedulerTask.timerTask, time);
			}
		}
	}
    /**
     * 重新制定计划
     * @param schedulerTask SchedulerTimerTask 类的一个实例
     * @param iterator ScheduleIterator 接口的一个实例
     */
	private void reschedule(SchedulerTask schedulerTask,
			ScheduleIterator iterator) {
		Date time = iterator.next();
		if (time == null) {
			schedulerTask.cancel();
		} else {
			synchronized (schedulerTask.lock) {
				if (schedulerTask.state != SchedulerTask.CANCELLED) {
					schedulerTask.timerTask = new SchedulerTimerTask(
							schedulerTask, iterator);
					timer.schedule(schedulerTask.timerTask, time);
				}
			}
		}
	}

}
