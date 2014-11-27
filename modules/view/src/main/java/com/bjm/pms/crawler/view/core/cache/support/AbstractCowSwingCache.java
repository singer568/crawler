/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.cache.support;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.core.cache.ICowSwingCache;

/**
 * 缓存抽象实现类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-21 上午10:10:41
 * @version 1.0
 */
public abstract class AbstractCowSwingCache<T> implements ICowSwingCache<T>{
	protected Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 加载数据到缓存
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-21 上午10:25:52
	 * @version 1.0
	 * @exception
	 */
	protected abstract T loadDataToCache();
	
	public T load(){
		return loadDataToCache();
	}
	

}
