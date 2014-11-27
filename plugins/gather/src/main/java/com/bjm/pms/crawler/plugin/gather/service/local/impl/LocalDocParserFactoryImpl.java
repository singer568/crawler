/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service.local.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjm.pms.crawler.plugin.gather.service.local.LocalDocParser;
import com.bjm.pms.crawler.plugin.gather.service.local.LocalDocParserFactory;

/**
 * 文档解析器工厂
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-23 上午10:24:04
 * @version 1.0
 */
@Service("localDocParserFactory")
public class LocalDocParserFactoryImpl implements LocalDocParserFactory{
	
	/**自动组装文档解析器LIST*/
	@Autowired
	private List<LocalDocParser> localDocParserList;

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.plugin.gather.service.local.LocalTxtDocParserFactory#getLocalDocParser(java.lang.String)
	 */
	@Override
	public LocalDocParser getLocalDocParser(String docType) {
		if(CollectionUtils.isNotEmpty(localDocParserList)){
			for(LocalDocParser parser : localDocParserList){
				if(parser.getDocTypeList().contains(docType)){
					return parser;
				}
			}
		}
		return null;
	}
}
