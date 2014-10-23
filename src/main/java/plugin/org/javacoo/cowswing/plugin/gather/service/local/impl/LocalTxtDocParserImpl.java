/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.service.local.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * TXT文档解析器
 * <p>说明:</p>
 * <li>解析TXT类型，或者能够以文本文件打开的类型</li>
 * @author DuanYong
 * @since 2014-9-23 上午9:59:57
 * @version 1.0
 */
@Service("localTxtDocParserImpl")
public class LocalTxtDocParserImpl extends AbstractLocalDocParser{
	private final static Log logger = LogFactory.getLog(LocalTxtDocParserImpl.class);
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.plugin.gather.service.local.LocalDocParser#getDocType()
	 */
	@Override
	public List<String> getDocTypeList() {
		List<String> list = new ArrayList<String>();
		list.add("txt");
		return list;
	}


	@SuppressWarnings("unchecked")
	protected void parserDocument(String filePath){
		if(file.length()==0){
			this.content =  "";
		}
		InputStream in = null;
		try{
			in = new FileInputStream(file);
			List<String> lineList = IOUtils.readLines(in, "GB2312");	
			this.content = populateValue(lineList);
			this.title = this.file.getName();
		}catch (IOException e){
			logger.error("解析TXT文件失败："+e.getMessage());
		}finally{
			if(null != in)
			{
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					logger.error("解析TXT文件失败："+e.getMessage());
				}
			}
		}
	}
	/**
	 * 组装文档内容
	 * @param lineList
	 * @return
	 */
	private String populateValue(List<String> lineList){
		StringBuilder sb = new StringBuilder();
		for(String str : lineList){
			sb.append(str).append("\r\n");
		}
		return sb.toString();
	}
}
