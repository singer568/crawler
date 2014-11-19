package org.javacoo.cowswing.plugin.scheduler.core;

import java.util.TimerTask;
/**
 * 计划任务抽象类
 * <li>
 * SchedulerTask 在其生命周期中要经历一系列的状态。创建后，它处于 VIRGIN 状态，
 * 这表明它从没有计划过。计划以后，它就变为 SCHEDULED 状态，
 * 再用下面描述的方法之一取消任务后，它就变为 CANCELLED 状态。
 * 管理正确的状态转变 —— 如保证不对一个非 VIRGIN 状态的任务进行两次计划 —— 
 * 增加了 Scheduler 和 SchedulerTask 类的复杂性。在进行可能改变任务状态的操作时，
 * 代码必须同步任务的锁对象
 * </li>
 * @author javacoo
 * @since 2011-11-02
 */
public abstract class SchedulerTask implements Runnable {
	/**同步任务的锁对象*/
	final Object lock = new Object();
    /**状态*/
	int state = VIRGIN;
	/**初始状态*/
	static final int VIRGIN = 0;
	/**任务状态*/
	static final int SCHEDULED = 1;
	/**取消状态*/
	static final int CANCELLED = 2;
	/**TimerTask 对象*/
	TimerTask timerTask;

	protected SchedulerTask() {
	}
    /**执行的任务，由子类实现*/
	public abstract void run();
    /**取消任务
     * <li>
     * 任务再也不会运行了，不过已经运行的任务仍会运行完成
     * </li>
     */
	public boolean cancel() {
		synchronized (lock) {
			if (timerTask != null) {
				timerTask.cancel();
			}
			boolean result = (state == SCHEDULED);
			state = CANCELLED;
			return result;
		}
	}

	public long scheduledExecutionTime() {
		synchronized (lock) {
			return timerTask == null ? 0 : timerTask.scheduledExecutionTime();
		}
	}

}
