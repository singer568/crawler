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
import com.bjm.pms.crawler.view.core.runcycle.schedule.processor.IShutDownScheduleProcessor;


/**
 * 停止计划
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-1 下午9:52:56
 * @version 1.0
 */
@Component("shutDownSchedule")
public class ShutDownSchedule extends AbstractSchedule<IShutDownScheduleProcessor>{
	@Autowired
	private List<IShutDownScheduleProcessor> shutDownScheduleProcessor;
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.Schedule#getSchedule()
	 */
	@Override
	public ScheduleEnum getSchedule() {
		return ScheduleEnum.SHUTDOWN;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.support.AbstractSchedule#getScheduleProcessors()
	 */
	@Override
	protected Collection<IShutDownScheduleProcessor> getScheduleProcessors() {
		if(null != this.shutDownScheduleProcessor){
			return new TreeSet<IShutDownScheduleProcessor>(this.shutDownScheduleProcessor);
		}
		return null;
	}

	/**
	 * @param shutDownScheduleProcessor the shutDownScheduleProcessor to set
	 */
	public void setShutDownScheduleProcessor(
			List<IShutDownScheduleProcessor> shutDownScheduleProcessor) {
		this.shutDownScheduleProcessor = shutDownScheduleProcessor;
	}

	
}
