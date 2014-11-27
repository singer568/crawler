/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.runcycle.schedule.processor.support;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.core.dispose.DisposeServiceManager;
import com.bjm.pms.crawler.view.core.runcycle.schedule.Schedule;
import com.bjm.pms.crawler.view.core.runcycle.schedule.processor.IShutDownScheduleProcessor;

/**
 * 停止运行执行任务
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-7-9 下午2:18:57
 * @version 1.0
 */
@Component("shutDownScheduleProcessor")
public class ShutDownScheduleProcessor extends AbstractScheduleProcessor implements IShutDownScheduleProcessor{
	@Resource(name="disposeServiceManager")
	private DisposeServiceManager disposeServiceManager;
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.processor.IScheduleProcessor#getIndex()
	 */
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 3;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.processor.support.AbstractScheduleProcessor#doExecute(org.javacoo.cowswing.core.runcycle.schedule.Schedule)
	 */
	@Override
	protected void doExecute(Schedule schedule) {
		disposeServiceManager.run();
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.processor.support.AbstractScheduleProcessor#isNewThread()
	 */
	@Override
	protected boolean isNewThread() {
		return true;
	}

}
