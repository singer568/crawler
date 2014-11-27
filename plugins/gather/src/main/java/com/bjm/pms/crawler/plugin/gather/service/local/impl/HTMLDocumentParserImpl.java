package com.bjm.pms.crawler.plugin.gather.service.local.impl;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.base.constant.Constant;


/**
 * 类型为HTML的文档
 * <p>
 * 解析类型为HTML的文档
 * @author DuanYong
 * @since 2010-04-26
 * @version 1.0
 */
@Component("htmlDocumentParser")
public class HTMLDocumentParserImpl extends AbstractLocalDocParser{
	private final static Log logger = LogFactory.getLog(HTMLDocumentParserImpl.class);
	
	/** StringBuffer的缓冲区大小 */
	protected final int TRANSFER_SIZE = 4096;
	@Override
	protected void parserDocument(String filePath){
		InputStream in = null;
		try
		{
			in = getFileInputStream(filePath);
			String[] result = parseHtml(filePath,autoDetectCharset(in));
			this.content = result[0];
			this.title = result[1];
		}
		catch (Exception e)
		{
			logger.error("解析HTML文件失败："+e.getMessage(),e);
		}
		finally
		{
			if(null != in)
			{
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					logger.error("解析HTML文件失败："+e.getMessage(),e);
				}
			}
		}
	}
	/**
	 * 自动探测页面编码，避免中文乱码的出现
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	private String autoDetectCharset(InputStream in){
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		/**
		 * ParsingDetector 可用于检查HTML、XML等文件或字符流的编码 构造方法中的参数用于指示是否显示探测过程的详细信息
		 * 为false则不显示
		 */
		detector.add(new ParsingDetector(true));
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		Charset charset = null;
		try {
			charset = detector.detectCodepage(in, 1024);
			logger.debug("HTML页面编码："+charset);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.error("解析HTML文件失败："+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("解析HTML文件失败："+e.getMessage());
		}
		if (charset == null)
			charset = Charset.defaultCharset();
		return charset.name();
	}
	/**
	 * 按照指定编码解析标准的html页面
	 * @param url 文件地址
	 * @param charset 字符集
	 * @return 包括内容和标题的字符窜数组
	 * @throws Exception 
	 */
	private String[] parseHtml(String url, String charset) throws Exception {
		String result[] = null;
		String content = null;
		content = getHtmlContent(url,charset);
		if (content != null) {
			Parser myParser = Parser.createParser(content, charset);
			HtmlPage visitor = new HtmlPage(myParser);
			try {
				myParser.visitAllNodesWith(visitor);
				String body = null;
				String title = "Untitled";
				if (visitor.getBody() != null) {
					NodeList nodelist = visitor.getBody();
					body = nodelist.asString().trim();
				}
				if (visitor.getTitle() != null)
					title = visitor.getTitle();
				result = new String[] { body, title };
			} catch (org.htmlparser.util.ParserException e) {
				e.printStackTrace();
				logger.error("解析HTML文件失败："+e.getMessage());
				throw new Exception("解析HTML文件失败");
			}
		}
		return result;
	}
	/**
	 * 得到HTML文件内容
	 * @param in
	 * @param charset
	 * @return
	 * @throws Exception 
	 */
	private String getHtmlContent(String url, String charset){
		BufferedReader reader = null;
		InputStream in = null;
		try {
			if(url.contains(Constant.HTTP)){
				logger.debug("该地址是网络文件："+url);
				URL source = new URL(url);
				in = source.openStream();
			}else{
				in = new FileInputStream(url);
				logger.debug("该地址是本地文件："+url);
			}
			reader = new BufferedReader(new InputStreamReader(
					in, charset));
			String line = new String();
			StringBuffer temp = new StringBuffer(TRANSFER_SIZE);
			while ((line = reader.readLine()) != null) {
				temp.append(line).append(Constant.SYSTEM_LINE_SEPARATOR);
			}
			return temp.toString();
		} catch (MalformedURLException mue) {
			logger.error("解析HTML文件失败:无效的URL地址"+url);
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
			logger.error("解析HTML文件失败:无效的编码"+url);
		} catch (IOException ie) {
			if (ie instanceof UnknownHostException) {
				logger.error("解析HTML文件失败:主机不存在："+ie.getMessage());
			} else if (ie instanceof SocketException) {
				logger.error("解析HTML文件失败:网络错误："+ie.getMessage());
			} else if (ie instanceof SocketTimeoutException) {
				logger.error("解析HTML文件失败:链接超时："+ie.getMessage());
			} else{
				ie.printStackTrace();
				logger.error("解析HTML文件失败："+ie.getMessage());
			}
				
		}finally {
			try {
				if(null != reader){
					reader.close();
				}
				if(null != in){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("解析HTML文件失败："+e.getMessage());
			}
		}
		return "";
	}
	/**
	 * 判断URL是本地HTML文件还是链接地址，并返回输入流
	 * @param url 文件地址
	 * @return 输入流
	 * @throws IOException
	 */
	private InputStream getFileInputStream(String url) throws IOException{
		InputStream in = null;
		if(url.contains(Constant.HTTP)){
			logger.debug("该地址是网络文件："+url);
			URL source = new URL(url);
			in = source.openStream();
		}else{
			in = new FileInputStream(url);
			logger.debug("该地址是本地文件："+url);
		}
		return in;
	}
	
	public void parserHtmlCode(String content){
			content="<body>"+content+"</body>";
		if (content != null) {
			Parser myParser = Parser.createParser(content, "UTF-8");
			HtmlPage visitor = new HtmlPage(myParser);
			try {
				myParser.visitAllNodesWith(visitor);
				String body = "";
				if (visitor.getBody() != null) {
					NodeList nodelist = visitor.getBody();
					body = nodelist.asString().trim();
				}
				this.content = body;
			} catch (org.htmlparser.util.ParserException e) {
				e.printStackTrace();
				logger.error("解析HTML文件失败："+e.getMessage());
			}
		}
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.plugin.gather.service.local.LocalDocParser#getDocTypeList()
	 */
	@Override
	public List<String> getDocTypeList() {
		List<String> list = new ArrayList<String>();
		list.add("html");
		list.add("htm");
		return list;
	}
}
