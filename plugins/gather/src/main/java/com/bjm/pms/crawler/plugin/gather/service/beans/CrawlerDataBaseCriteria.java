/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service.beans;

import com.bjm.pms.crawler.persist.PaginationCriteria;

/**
 * 数据库信息查询对象
 *@author DuanYong
 *@since 2013-2-14上午9:27:20
 *@version 1.0
 */
public class CrawlerDataBaseCriteria extends PaginationCriteria{
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer dataBaseId;
	/**描述*/
	private String description;
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
	public Integer getDataBaseId() {
		return dataBaseId;
	}
	public void setDataBaseId(Integer dataBaseId) {
		this.dataBaseId = dataBaseId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	

}
