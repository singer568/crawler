package org.javacoo.cowswing.plugin.gather.service.local.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.stereotype.Component;

/**
 * 类型为DOCX的文档
 * <p>
 * 解析类型为DOCX的文档
 * 
 * @author DuanYong
 * @since 2013-07-11
 * @version 1.0
 */
@Component("docxDocumentParser")
public class DOCXDocumentParserImpl extends AbstractLocalDocParser{
	private final Log logger = LogFactory.getLog(DOCXDocumentParserImpl.class);
	@Override
	protected void parserDocument(String filePath){
		if(this.file.length()==0){
			this.content = "";
		}
		try {
			XWPFWordExtractor wordxExtractor = new XWPFWordExtractor(POIXMLDocument.openPackage(filePath));
			this.content = wordxExtractor.getText();
			this.title = this.file.getName();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("解析DOCX文档出错："+e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.plugin.gather.service.local.LocalDocParser#getDocTypeList()
	 */
	@Override
	public List<String> getDocTypeList() {
		List<String> list = new ArrayList<String>();
		list.add("docx");
		return list;
	}
}
