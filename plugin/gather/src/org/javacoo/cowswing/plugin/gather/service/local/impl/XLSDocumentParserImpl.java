package org.javacoo.cowswing.plugin.gather.service.local.impl;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Component;


/**
 * 类型为XLS的文档
 * <p>
 * 解析类型为XLS的文档
 * 
 * @author DuanYong
 * @since 2010-03-27
 * @version 1.0
 */
@Component("xlsDocumentParser")
public class XLSDocumentParserImpl extends AbstractLocalDocParser{
	private final static Log logger = LogFactory.getLog(XLSDocumentParserImpl.class);
	@Override
	protected void parserDocument(String filePath){
		if(file.length()==0){
			this.content = "";
		}
	        try {
	            ExcelExtractor xlsExtractor = new ExcelExtractor(new POIFSFileSystem(new FileInputStream(this.file)));
	            this.content = xlsExtractor.getText();
	            this.title = this.file.getName();
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.error("解析XLS文档出错："+e.getMessage(),e);
	        }
		
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.plugin.gather.service.local.LocalDocParser#getDocTypeList()
	 */
	@Override
	public List<String> getDocTypeList() {
		List<String> list = new ArrayList<String>();
		list.add("xls");
		return list;
	}
}
