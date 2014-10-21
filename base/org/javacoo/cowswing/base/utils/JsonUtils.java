/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.base.utils;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

/**
 * JsonUtils工具类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-1 上午11:09:49
 * @version 1.0
 */
public class JsonUtils {
	private static Gson gson = new Gson();
	/**
	 * 格式化任务信息为json格式字符串
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-10-17 上午9:09:24
	 * @param obj
	 * @return String
	 */
	public static String formatObjectToJsonString(Object obj){
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		if(StringUtils.isNotBlank(json)){
			return json;
		}
		return "";
    }
	/**
	 * 将JSON字符串转换为指定对象
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-1 下午8:59:04
	 * @param jsonString
	 * @param beanClass
	 * @return
	 * @return Object
	 */
	public static  Object formatStringToObject(String jsonString,Class<?> beanClass){
		Gson gson = new Gson();
		return gson.fromJson(jsonString, beanClass);
	}
}
