/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.cache;

/**
 * 缓存管理接口
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-24 上午10:43:53
 * @version 1.0
 */
public interface ICowSwingCacheManager extends Runnable{
	/**
	 * 加载所有缓存
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-24 下午3:47:59
	 * @version 1.0
	 * @exception
	 */
	void loadCache();
	/**
	 * 根据缓存KEY，加载指定缓存
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-24 下午3:46:18
	 * @version 1.0
	 * @exception 
	 * @param cacheKey
	 */
	Object loadCacheByKey(String cacheKey);
	/**
	 * 根据键取得缓存值
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-21 上午9:34:02
	 * @version 1.0
	 * @exception 
	 * @param key
	 * @return
	 */
	Object getValue(String key);
	/**
	 * 清空缓存
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-21 上午9:34:18
	 * @version 1.0
	 * @exception
	 */
    void clear();
}
