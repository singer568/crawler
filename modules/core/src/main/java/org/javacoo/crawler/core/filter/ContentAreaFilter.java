package org.javacoo.crawler.core.filter;

import org.javacoo.crawler.core.constants.Constants;


/**
 * 过滤器接口实现类-内容区域过滤
 * @author javacoo
 * @since 2011-11-10
 * @LastModify 2012-05-12
 */
public class ContentAreaFilter extends AbstractFilter<String,String>{
	/**
	 * <li>约定采集参数格式如下</li>
	 * <li>1，标签属性/值形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN</li>
	 * <li>2，标签名称形式，如：div,p,span</li>
	 * <li>3，混合形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN,div,p,span</li>
	 * @param fethAreaTagStr 要采集的区域标签字符串
	 * @param deleteAreaTagStr 要过滤的区域标签字符串
	 */
	public ContentAreaFilter(String fethAreaTagStr, String deleteAreaTagStr){
		super(fethAreaTagStr,deleteAreaTagStr);
		this.filterName = Constants.FILTER_NAME_CONTENT_AREA;
	}
	/**
	 * 构建标签属性Map
	 */
	public void buildFilterMap(){
		this.fetchAreaTagMap = parserAreaTagStrToBuildFilterMap(this.fethAreaTagStr);
		this.deleteAreaTagMap = parserAreaTagStrToBuildFilterMap(this.deleteAreaTagStr);
	}
}
