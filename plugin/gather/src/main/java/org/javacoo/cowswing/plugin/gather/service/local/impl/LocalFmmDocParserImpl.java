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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * FMM文档解析器
 * <p>说明:</p>
 * <li>解析FMM类型，定制</li>
 * @author DuanYong
 * @since 2014-9-23 上午9:59:57
 * @version 1.0
 */
//@Service("localFmmDocParserImpl")
public class LocalFmmDocParserImpl extends AbstractLocalDocParser{
	private final static Log logger = LogFactory.getLog(LocalFmmDocParserImpl.class);
	/**预警地区*/
	private final static String PARSER_KEY_AREA = "warn_area";
	/**预警类型*/
	private final static String PARSER_KEY_STATUS = "warn_status";
	/**预警级别*/
	private final static String PARSER_KEY_COLOR = "warn_color";
	/**预警时间*/
	private final static String PARSER_KEY_TIME = "warn_time";
	/**预警图片*/
	private final static String PARSER_KEY_PIC = "warn_pic";
	/**预警文本*/
	private final static String PARSER_KEY_TXT = "warn_txt";
	//灾害类型
	private final static Map<String,String> typeMap = new HashMap<String,String>();
	//灾害级别
	private final static Map<String,String> levalMap = new HashMap<String,String>();
	//站点
	private final static Map<String,String> siteMap = new HashMap<String,String>();
	//字段MAP对象
	private Map<String, String> fields = new HashMap<String, String>();
	
	static{
		typeMap.put("A", "台风");
		typeMap.put("B", "暴雨");
		typeMap.put("C", "高温");
		typeMap.put("D", "寒冷");
		typeMap.put("E", "大雾");
		typeMap.put("F", "灰霾");
		typeMap.put("G", "雷雨大风");
		typeMap.put("H", "道路结冰");
		typeMap.put("I", "冰雹");
		typeMap.put("J", "森林火险");
		
		levalMap.put("0", "解除");
		levalMap.put("1", "白色");
		levalMap.put("2", "蓝色");
		levalMap.put("3", "黄色");
		levalMap.put("4", "橙色");
		levalMap.put("5", "红色");
		
		siteMap.put("BFMM", "茂名");
		siteMap.put("GDHZ", "化州");
		siteMap.put("GDGZ", "高州");
		siteMap.put("GDXY", "信宜");
		siteMap.put("GDDB", "电白");
		
		
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.plugin.gather.service.local.LocalDocParser#getDocType()
	 */
	@Override
	public List<String> getDocTypeList() {
		List<String> list = new ArrayList<String>();
		list.add("fmm");
		list.add("dhz");
		list.add("dgz");
		list.add("dxy");
		list.add("ddb");
		return list;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.plugin.gather.service.local.LocalDocParser#getValueMap()
	 */
	@Override
	public Map<String, String> getValueMap() {
		if(fields.isEmpty()){
			Map<String, String> tempFields = new HashMap<String, String>();
			tempFields.put(PARSER_KEY_AREA, "");
			tempFields.put(PARSER_KEY_STATUS, "");
			tempFields.put(PARSER_KEY_COLOR, "");
			tempFields.put(PARSER_KEY_TIME, "");
			tempFields.put(PARSER_KEY_PIC, "");
			tempFields.put(PARSER_KEY_TXT, "");
			return tempFields;
		}
		return fields;
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
			logger.error("解析fmm文件失败："+e.getMessage());
		}finally{
			if(null != in)
			{
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					logger.error("解析fmm文件失败："+e.getMessage());
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
		for(int i = 0;i<lineList.size();i++){
			if(i == 2){
				parserLine(lineList.get(i));
			}
			sb.append(lineList.get(i)).append("\r\n");
		}
		return sb.toString();
	}
	/**
	 * 解析第三行
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-24 下午6:39:46
	 * @version 1.0
	 * @exception 
	 * @param str
	 */
	private void parserLine(String str){
		if(StringUtils.isNotBlank(str) && str.contains(" ")){
			String[] strArray = str.split(" ");
			if(strArray.length != 3){
				logger.error("解析fmm文件失败：报文格式不正确");
				return;
			}
			parserTime(strArray[0]);
			parserSite(strArray[1]);
			parserOther(strArray[2]);
			populateTxt();
		}
	}
	/**
	 * 组装预警信息
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-26 上午9:24:55
	 * @version 1.0
	 * @exception 
	 */
	private void populateTxt() {
		StringBuilder txt = new StringBuilder();
		txt.append(fields.get(PARSER_KEY_AREA));
		if("解除".equals(fields.get(PARSER_KEY_COLOR))){
			txt.append(fields.get(PARSER_KEY_COLOR));
		}else{
			txt.append("发布");
		}
		txt.append(fields.get(PARSER_KEY_STATUS));
		if(!"解除".equals(fields.get(PARSER_KEY_COLOR))){
			txt.append(fields.get(PARSER_KEY_COLOR));
		}
		txt.append("预警信号   ");
		txt.append(fields.get(PARSER_KEY_TIME));
		logger.info("组装预警信息："+txt.toString());
		fields.put(PARSER_KEY_TXT, txt.toString());
	}

	/**
	 * 解析时间，并转换为本地时间
	 * <p>方法说明:</>
	 * <li>加8小时</li>
	 * @author DuanYong
	 * @since 2014-9-24 下午6:53:22
	 * @version 1.0
	 * @exception 
	 * @param timeStr
	 */
	private void parserTime(String timeStr){
		if(StringUtils.isNotBlank(timeStr)){
			StringBuilder time = new StringBuilder();
			time.append(timeStr.substring(0, 4)).append("-").append(timeStr.substring(4, 6)).append("-").append(timeStr.substring(6, 8));
			time.append(" ");
			time.append(Integer.valueOf(timeStr.substring(8, 10))+8).append(":").append(timeStr.substring(10, 12)).append(":").append("00");
			logger.info("解析时间，并转换为本地时间："+time.toString());
			fields.put(PARSER_KEY_TIME, time.toString());
		}
	}
	/**
	 * 解析地区
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-24 下午6:56:24
	 * @version 1.0
	 * @exception 
	 * @param site
	 */
	private void parserSite(String site){
		if(StringUtils.isNotBlank(site)){
			fields.put(PARSER_KEY_AREA, siteMap.get(site));
		}
	}
	/**
	 * 解析级别和类型等
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-24 下午7:00:20
	 * @version 1.0
	 * @exception 
	 * @param other
	 */
	private void parserOther(String other){
		if(StringUtils.isNotBlank(other)){
			fields.put(PARSER_KEY_STATUS, typeMap.get(other.substring(0, 1)));
			fields.put(PARSER_KEY_COLOR, levalMap.get(other.substring(1, 2)));
			fields.put(PARSER_KEY_PIC, other.substring(0, 2));
		}
	}
}
