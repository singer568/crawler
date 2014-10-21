/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.ui.processor;

import org.javacoo.cowswing.core.context.CowSwingContextData;
import org.javacoo.cowswing.core.runcycle.schedule.Schedule;
import org.javacoo.cowswing.core.runcycle.schedule.processor.IStartScheduleProcessor;
import org.javacoo.cowswing.core.runcycle.schedule.processor.support.AbstractScheduleProcessor;
import org.javacoo.cowswing.main.CowSwingMainFrame;
import org.springframework.stereotype.Component;


/**
 * 运行开始计划处理器
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-2 下午1:10:52
 * @version 1.0
 */
@Component("applicationStartScheduleProcessor")
public class ApplicationStartScheduleProcessor extends AbstractScheduleProcessor implements IStartScheduleProcessor{

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.processor.IScheduleProcessor#getIndex()
	 */
	@Override
	public int getIndex() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.processor.support.AbstractScheduleProcessor#doExecute(org.javacoo.cowswing.core.runcycle.schedule.Schedule)
	 */
	@Override
	protected void doExecute(Schedule schedule) {
		CowSwingMainFrame cowSwingMainFrame = (CowSwingMainFrame)CowSwingContextData.getInstance().getApplicationContext().getBean("cowSwingMainFrame");
		cowSwingMainFrame.init();
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.processor.support.AbstractScheduleProcessor#isNewThread()
	 */
	@Override
	protected boolean isNewThread() {
		return false;
	}

}
