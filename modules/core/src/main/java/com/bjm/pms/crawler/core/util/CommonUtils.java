package com.bjm.pms.crawler.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bjm.pms.crawler.core.config.CrawlerConfig;
/**
 * 工具类
 * @author javacoo
 * @since 2012-05-02
 */
public class CommonUtils {
	/**
	 * 组装全局字符串替换mao
	 * @param commonReplaceWords
	 * @return
	 */
	public static Map<String, String> populateWordsMap(String str) {
		if(StringUtils.isNotBlank(str)){
			Map<String, String> wordsMap = new HashMap<String, String>();
			String[] keyValueArr = str.split(",");
			String[] tempArr = null;
			for(String keyValue : keyValueArr){
				if(keyValue.contains("=")){
					tempArr = keyValue.split("=");
					wordsMap.put(tempArr[0], tempArr[1]);
				}else{
					wordsMap.put(keyValue, CrawlerConfig.defaultWords);
				}
			}
			return wordsMap;
		}
		return null;
	}
	
	/**
	 * 组装代理服务器列表
	 * @param proxyServerStr
	 * @return
	 */
	public static List<Map<String,Integer>> populateProxyServer(String proxyServerStr){
		List<Map<String,Integer>> resultList = new ArrayList<Map<String,Integer>>();
		String[] proxyArr = proxyServerStr.split(",");
		String[] tempArr = null;
		Map<String,Integer> proxyMap = null;
		for(String proxy : proxyArr){
			tempArr = proxy.split(":");
			if(tempArr.length == 2 && !StringUtils.isBlank(tempArr[0]) && !StringUtils.isBlank(tempArr[1])){
				proxyMap = new HashMap<String, Integer>();
				proxyMap.put(StringUtils.trim(tempArr[0]), Integer.parseInt(StringUtils.trim(tempArr[1])));
				resultList.add(proxyMap);
			}
		}
		return resultList;
	}

}
