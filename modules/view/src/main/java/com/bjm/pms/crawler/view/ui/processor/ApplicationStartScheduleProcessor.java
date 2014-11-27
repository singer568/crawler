/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.processor;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.core.context.CowSwingContextData;
import com.bjm.pms.crawler.view.core.runcycle.schedule.Schedule;
import com.bjm.pms.crawler.view.core.runcycle.schedule.processor.IStartScheduleProcessor;
import com.bjm.pms.crawler.view.core.runcycle.schedule.processor.support.AbstractScheduleProcessor;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;


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
