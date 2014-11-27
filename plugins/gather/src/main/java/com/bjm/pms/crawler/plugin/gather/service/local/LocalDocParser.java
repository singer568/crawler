/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service.local;

import java.util.List;
import java.util.Map;

/**
 * 本地文档解析
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-23 上午9:50:06
 * @version 1.0
 */
public interface LocalDocParser {
	/**
	 * 取得解析器类型
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-23 上午9:54:13
	 * @version 1.0
	 * @exception 
	 * @return List<String>
	 */
	List<String> getDocTypeList();
	/**
	 * 取得解析后MAP值
	 * <p>方法说明:</>
	 * <li>键为标示字段，值为解析后的值</li>
	 * @author DuanYong
	 * @since 2014-9-23 上午9:54:30
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	Map<String,String> getValueMap();
	/**
	 * 取得文档内容
	 * 
	 * @return 文档内容
	 */
	String getContent();

	/**
	 * 取得文档标题
	 * 
	 * @return 文档标题
	 */
	String getTitle();
	/**
	 * 解析文档
	 * 
	 * @param 文档路径
	 */
	void parser(String filePath);
}
