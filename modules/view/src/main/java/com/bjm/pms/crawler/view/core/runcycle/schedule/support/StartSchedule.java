/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.runcycle.schedule.support;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.core.runcycle.schedule.ScheduleEnum;
import com.bjm.pms.crawler.view.core.runcycle.schedule.processor.IStartScheduleProcessor;

/**
 * 开始计划
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-1 下午9:56:35
 * @version 1.0
 */
@Component("startSchedule")
public class StartSchedule extends AbstractSchedule<IStartScheduleProcessor>{
	@Autowired
	private List<IStartScheduleProcessor> startScheduleProcessor;
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.Schedule#getSchedule()
	 */
	@Override
	public ScheduleEnum getSchedule() {
		return ScheduleEnum.START;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.support.AbstractSchedule#getScheduleProcessors()
	 */
	@Override
	protected Collection<IStartScheduleProcessor> getScheduleProcessors() {
		if(null != this.startScheduleProcessor){
			return new TreeSet<IStartScheduleProcessor>(this.startScheduleProcessor);
		}
		return null;
	}

	/**
	 * @param startScheduleProcessor the startScheduleProcessor to set
	 */
	public void setStartScheduleProcessor(
			List<IStartScheduleProcessor> startScheduleProcessor) {
		this.startScheduleProcessor = startScheduleProcessor;
	}

	
}
