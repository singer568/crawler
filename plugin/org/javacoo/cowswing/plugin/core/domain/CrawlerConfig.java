/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.core.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 爬虫相关配置信息持久对象
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午3:34:49
 * @version 1.0
 */
public class CrawlerConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer configId;
	private Integer configType;
	private List<Integer> configIdList;
	private String configContent;
	/**
	 * @return the configId
	 */
	public Integer getConfigId() {
		return configId;
	}
	/**
	 * @param configId the configId to set
	 */
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	/**
	 * @return the configType
	 */
	public Integer getConfigType() {
		return configType;
	}
	/**
	 * @param configType the configType to set
	 */
	public void setConfigType(Integer configType) {
		this.configType = configType;
	}
	/**
	 * @return the configIdList
	 */
	public List<Integer> getConfigIdList() {
		return configIdList;
	}
	/**
	 * @param configIdList the configIdList to set
	 */
	public void setConfigIdList(List<Integer> configIdList) {
		this.configIdList = configIdList;
	}
	/**
	 * @return the configContent
	 */
	public String getConfigContent() {
		return configContent;
	}
	/**
	 * @param configContent the configContent to set
	 */
	public void setConfigContent(String configContent) {
		this.configContent = configContent;
	}
	
	
}
