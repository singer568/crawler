/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.persistence.util;

/**
 * 数据库参数配置对象
 *@author DuanYong
 *@since 2013-2-17下午12:19:17
 *@version 1.0
 */
public class ConnectionConfig {
	/**ID*/
	private String id;
	/**URL*/
	private String url;
	/**用户名*/
	private String userName;
	/**密码*/
	private String password;
	/**驱动名称*/
	private String className;
	/**数据库类型*/
	private String type;
	/**连接数*/
	private String maxconn = "10";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMaxconn() {
		return maxconn;
	}
	public void setMaxconn(String maxconn) {
		this.maxconn = maxconn;
	}
	
	
	

}
