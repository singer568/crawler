package org.javacoo.cowswing.plugin.gather.service.local.impl;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.stereotype.Component;

/**
 * 类型为DOC的文档
 * <p>
 * 解析类型为DOC的文档
 * 
 * @author DuanYong
 * @since 2010-03-27
 * @version 1.0
 */
@Component("docDocumentParser")
public class DOCDocumentParserImpl extends AbstractLocalDocParser{
	private final Log logger = LogFactory.getLog(DOCDocumentParserImpl.class);
	@Override
	protected void parserDocument(String filePath){
		if(this.file.length()==0){
			this.content = "";
		}
		try {
			WordExtractor wordExtractor = new WordExtractor(
					new FileInputStream(this.file));
			this.content = wordExtractor.getText();
			this.title = this.file.getName();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("解析DOC文档出错："+e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.plugin.gather.service.local.LocalDocParser#getDocTypeList()
	 */
	@Override
	public List<String> getDocTypeList() {
		List<String> list = new ArrayList<String>();
		list.add("doc");
		return list;
	}
	

}
