/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.service.local.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.javacoo.cowswing.plugin.gather.service.local.LocalDocParser;

/**
 * 本地文档解析抽象类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-23 下午5:44:06
 * @version 1.0
 */
public abstract class AbstractLocalDocParser implements LocalDocParser{
	/** 文件对象 */
	protected File file;
	/** 文档内容 */
	protected String content = "";
	/** 文档标题 */
	protected String title = "";
	
	/**
	 * 取得文档内容
	 * 
	 * @return 文档内容
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 取得文档标题
	 * 
	 * @return 文档标题
	 */
	public String getTitle() {
		return this.title;
	}
	
	public Map<String, String> getValueMap() {
		return new HashMap<String, String>();
	}
	
	/**
	 * 解析文档
	 * 
	 * @param 文档路径
	 * @throws Exception
	 */
	public final void parser(String filePath) {
		this.file = new File(filePath);
		parserDocument(filePath);
	}

	/**
	 * 子类实现方法，解析文档
	 */
	protected abstract void parserDocument(String filePath); 
}
