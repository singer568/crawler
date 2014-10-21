/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库信息
 *@author DuanYong
 *@since 2013-2-14上午9:16:12
 *@version 1.0
 */
public class CrawlerDataBase implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer dataBaseId;
	private List<Integer> dataBaseIdList;
	private String description;
	private String url;
	private String userName;
	private String password;
	private String className;
	private String type;
	public Integer getDataBaseId() {
		return dataBaseId;
	}
	public void setDataBaseId(Integer dataBaseId) {
		this.dataBaseId = dataBaseId;
	}
	
	public List<Integer> getDataBaseIdList() {
		return dataBaseIdList;
	}
	public void setDataBaseIdList(List<Integer> dataBaseIdList) {
		this.dataBaseIdList = dataBaseIdList;
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
