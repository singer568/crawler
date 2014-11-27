/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.cache;

/**
 * 缓存接口
 * <p>说明:</p>
 * <li>初始化缓存</li>
 * <li>根据key得到value</li>
 * <li>清除缓存</li>
 * @author DuanYong
 * @since 2013-4-21 上午9:30:10
 * @version 1.0
 */
public interface ICowSwingCache<T>{
	/**
	 * 加载缓存
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-21 上午9:33:51
	 * @version 1.0
	 * @exception
	 * @return t
	 */
	T load();
    /**
     * 缓存键
     * <p>方法说明:</>
     * <li></li>
     * @author DuanYong
     * @since 2013-4-24 上午10:32:36
     * @version 1.0
     * @exception 
     * @return
     */
    String getCacheKey();
}
