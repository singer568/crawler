/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.runcycle.schedule.support;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.javacoo.cowswing.core.runcycle.schedule.ScheduleEnum;
import org.javacoo.cowswing.core.runcycle.schedule.processor.IRunScheduleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 运行中计划
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-1 下午9:36:17
 * @version 1.0
 */
@Component("runSchedule")
public class RunSchedule extends AbstractSchedule<IRunScheduleProcessor>{

	@Autowired
    private List<IRunScheduleProcessor> runScheduleProcessor;
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.RunSchedule#getRunSchedule()
	 */
	@Override
	public ScheduleEnum getSchedule() {
		return ScheduleEnum.RUN;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.support.AbstractRunSchedule#getRunScheduleProcessors()
	 */
	@Override
	protected Collection<IRunScheduleProcessor> getScheduleProcessors() {
		return new TreeSet<IRunScheduleProcessor>(this.runScheduleProcessor);
	}

	/**
	 * @param runScheduleProcessor the runScheduleProcessor to set
	 */
	public void setRunScheduleProcessor(
			List<IRunScheduleProcessor> runScheduleProcessor) {
		this.runScheduleProcessor = runScheduleProcessor;
	}
	
	

}
