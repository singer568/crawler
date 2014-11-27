/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.runcycle.schedule.processor.support;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.core.cache.ICowSwingCacheManager;
import com.bjm.pms.crawler.view.core.runcycle.schedule.Schedule;
import com.bjm.pms.crawler.view.core.runcycle.schedule.processor.IRunScheduleProcessor;

/**
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-2 下午2:58:46
 * @version 1.0
 */
@Component("cacheScheduleProcessor")
public class CacheScheduleProcessor extends AbstractScheduleProcessor implements IRunScheduleProcessor{
	@Resource(name="cowSwingCacheManager")
	private ICowSwingCacheManager cowSwingCacheManager;
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.processor.IScheduleProcessor#getIndex()
	 */
	@Override
	public int getIndex() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.processor.support.AbstractScheduleProcessor#doExecute(org.javacoo.cowswing.core.runcycle.schedule.Schedule)
	 */
	@Override
	protected void doExecute(Schedule schedule) {
		cowSwingCacheManager.run();
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.runcycle.schedule.processor.support.AbstractScheduleProcessor#isNewThread()
	 */
	@Override
	protected boolean isNewThread() {
		return true;
	}

}
