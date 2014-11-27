/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.domain;

import java.io.Serializable;

/**
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-10-22 上午9:20:13
 * @version 1.0
 */
public class RemoteDataBase implements Serializable{
	private static final long serialVersionUID = 1L;
	private String dataBaseName;
	private String dataBaseType;
	/**
	 * @return the dataBaseName
	 */
	public String getDataBaseName() {
		return dataBaseName;
	}
	/**
	 * @param dataBaseName the dataBaseName to set
	 */
	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}
	/**
	 * @return the dataBaseType
	 */
	public String getDataBaseType() {
		return dataBaseType;
	}
	/**
	 * @param dataBaseType the dataBaseType to set
	 */
	public void setDataBaseType(String dataBaseType) {
		this.dataBaseType = dataBaseType;
	}
	
	
	

}
