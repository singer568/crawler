package com.bjm.pms.crawler.plugin.gather.service.local.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.stereotype.Component;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;


/**
 * 类型为PDF的文档
 * <p>
 * 解析类型为PDF的文档
 * 
 * @author DuanYong
 * @since 2010-03-27
 * @version 1.0
 */
@Component("pdfDocumentParser")
public class PDFDocumentParserImpl extends AbstractLocalDocParser {
	private final static Log logger = LogFactory.getLog(PDFDocumentParserImpl.class);
	@Override
	protected void parserDocument(String filePath){
		if(file.length()==0){
			this.content = "";
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(this.file);
			// 新建一个PDF解析器对象  
	        PDFParser parser = new PDFParser(in);  
	        // 对PDF文件进行解析  
	        parser.parse();  
	        // 获取解析后得到的PDF文档对象  
            PDDocument pdfdocument = parser.getPDDocument();  
            // 新建一个PDF文本剥离器  
            PDFTextStripper stripper = new PDFTextStripper();  
            // 从PDF文档对象中剥离文本  
			this.content =  stripper.getText(pdfdocument);
			this.title = this.file.getName();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("PDF文件解析失败"+e.getMessage(),e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.plugin.gather.service.local.LocalDocParser#getDocTypeList()
	 */
	@Override
	public List<String> getDocTypeList() {
		List<String> list = new ArrayList<String>();
		list.add("pdf");
		return list;
	}
	
	 public static void main ( String [] args ) throws Exception {
	     PdfReader reader = new PdfReader("F:\\p2\\MyKbs\\doc\\study\\crawler\\java网络爬虫.pdf");
	     List list = SimpleBookmark.getBookmark (reader) ;
	     for (Iterator i = list.iterator(); i.hasNext(); ) {
	       showBookmark (( Map ) i.next()) ;
	     }
	   }

	   private static void showBookmark ( Map bookmark ) {
	     System.out.println ( bookmark.get ( "Title" )) ;
	     ArrayList kids = ( ArrayList ) bookmark.get ( "Kids" ) ;
	     if ( kids == null )
	       return ;
	     for ( Iterator i = kids.iterator () ; i.hasNext () ; ) {
	       showBookmark (( Map ) i.next ()) ;
	     }
	   }
	

}
