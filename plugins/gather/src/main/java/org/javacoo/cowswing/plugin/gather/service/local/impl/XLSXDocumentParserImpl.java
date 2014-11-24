package org.javacoo.cowswing.plugin.gather.service.local.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.springframework.stereotype.Component;


/**
 * 类型为XLSX的文档
 * <p>
 * 解析类型为XLSX的文档
 * 
 * @author DuanYong
 * @since 2013-07-11
 * @version 1.0
 */
@Component("xlsxDocumentParser")
public class XLSXDocumentParserImpl extends AbstractLocalDocParser{
	private final static Log logger = LogFactory.getLog(XLSXDocumentParserImpl.class);
	@Override
	protected void parserDocument(String filePath){
		if(file.length()==0){
			this.content = "";
		}
	        try {
	        	XSSFExcelExtractor xlsxExtractor = new XSSFExcelExtractor(POIXMLDocument.openPackage(filePath));
	            this.content = xlsxExtractor.getText();
	            this.title = this.file.getName();
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.error("解析XLSX文档出错："+e.getMessage(),e);
	        }
		
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.plugin.gather.service.local.LocalDocParser#getDocTypeList()
	 */
	@Override
	public List<String> getDocTypeList() {
		List<String> list = new ArrayList<String>();
		list.add("xlsx");
		return list;
	}
}
