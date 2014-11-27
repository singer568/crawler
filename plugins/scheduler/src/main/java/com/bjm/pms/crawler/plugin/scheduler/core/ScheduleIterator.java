package com.bjm.pms.crawler.plugin.scheduler.core;

import java.util.Date;
/**
 * 计划框架-时间生成器接口
 * <li>将 SchedulerTask 的计划执行时间指定为一系列 java.util.Date 对象的接口
 * 然后 next() 方法按时间先后顺序迭代 Date 对象,返回值 null 会使任务取消（即它再也不会运行）</li>
 * @author javacoo
 * @since 2011-11-02
 */
public interface ScheduleIterator {
	/**
	 * 返回下次计划执行时间
	 * @return 下次计划执行时间
	 */
	Date next();
}
