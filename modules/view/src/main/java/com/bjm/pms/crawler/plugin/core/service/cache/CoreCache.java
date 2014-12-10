/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.core.service.cache;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.core.service.beans.SystemConfigBean;
import com.bjm.pms.crawler.view.core.cache.support.AbstractCowSwingCache;


/**
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-26 下午9:50:29
 * @version 1.0
 */
@Component("coreCache")
public class CoreCache extends AbstractCowSwingCache<SystemConfigBean>{

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.ICrawlerCache#getCacheKey()
	 */
	@Override
	public String getCacheKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.support.AbstractCrawlerCache#loadDataToCache()
	 */
	@Override
	protected SystemConfigBean loadDataToCache() {
		// TODO Auto-generated method stub
		return null;
	}

}
