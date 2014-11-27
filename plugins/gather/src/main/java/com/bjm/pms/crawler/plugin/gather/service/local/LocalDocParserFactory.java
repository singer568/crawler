/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service.local;

/**
 * 获取文档解析器工厂
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-23 上午10:22:00
 * @version 1.0
 */
public interface LocalDocParserFactory {
	/**
	 * 根据文档类型取得相应解析器
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-23 上午10:23:35
	 * @version 1.0
	 * @exception 
	 * @param docType
	 * @return
	 */
	LocalDocParser getLocalDocParser(String docType);
}
