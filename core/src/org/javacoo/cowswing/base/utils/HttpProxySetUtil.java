/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.base.utils;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * HTTP代理设置工具类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-10-28 下午4:58:26
 * @version 1.0
 */
public class HttpProxySetUtil {
	/**
	 * 设置HTTP代理
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-10-28 下午5:03:16
	 * @version 1.0
	 * @exception 
	 * @param proxyHost 代理地址
	 * @param proxyPort 代理端口
	 * @param domain 域
	 * @param userName 用户名
	 * @param password 密码
	 */
	public static void setHttpProxy(final String proxyHost,final String proxyPort,final String domain,final String userName,final String password){
		if(StringUtils.isBlank(proxyHost) || StringUtils.isBlank(proxyPort)){
			return;
		}
		Properties props = System.getProperties();  
	  	props.setProperty("proxySet", "true");  
	  	props.setProperty("http.proxyHost", proxyHost);  
	  	props.setProperty("http.proxyPort", proxyPort); 
	  	if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
	  		return;
	  	}
	  	Authenticator.setDefault(new Authenticator() {  
	  	    protected PasswordAuthentication getPasswordAuthentication() { 
			  	String userNameWithDomain = userName;
	  	    	if(StringUtils.isNotBlank(domain)){
	  	    		userNameWithDomain = domain + "\\" + userNameWithDomain;
	  	    	}
	  	        return new PasswordAuthentication(userNameWithDomain,  
	  	                password.toCharArray());  
	  	    }  
	  	});
	}

}
