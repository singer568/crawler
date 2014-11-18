/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.webservice.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * WebService工具类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-10-26 下午3:07:34
 * @version 1.0
 */
public class WebServiceUtil {
	private static WebServiceUtil instance = new WebServiceUtil();
	private ApplicationContext wsCtx = null;
	
	public static WebServiceUtil getInstance(){
		return instance;
	}
	
	
	/**
	 * @return the wsCtx
	 */
	public ApplicationContext getWsCtx() {
		return wsCtx;
	}


	private WebServiceUtil(){
		wsCtx = new ClassPathXmlApplicationContext("resources/spring/spring-cxf-client.xml");  
	}
	
	

}
