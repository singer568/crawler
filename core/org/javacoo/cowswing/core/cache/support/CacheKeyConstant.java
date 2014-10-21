/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.cache.support;

/**
 * 缓存KEY常量定义
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-21 上午10:57:35
 * @version 1.0
 */
public class CacheKeyConstant {
	private CacheKeyConstant(){}
	/**数据库缓存KEY,与Spring配置文件中id值一致*/
	public static final String CACHE_KEY_DATA_BASE = "dataBaseInfoCache";
	/**FTP缓存KEY,与Spring配置文件中id值一致*/
	public static final String CACHE_KEY_FTP = "ftpInfoCache";
	/**自定义数据缓存KEY,与Spring配置文件中id值一致*/
	public static final String CACHE_KEY_DIYDATA = "diyDataInfoCache";
	/**采集规则缓存KEY,与Spring配置文件中id值一致*/
	public static final String CACHE_KEY_RULE = "crawlerRuleCache";
}
