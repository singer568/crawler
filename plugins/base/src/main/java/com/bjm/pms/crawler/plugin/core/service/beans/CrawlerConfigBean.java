/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.core.service.beans;

import java.util.List;

import com.bjm.pms.crawler.plugin.core.beans.CrawlerDiyDataConfigBean;
import com.bjm.pms.crawler.plugin.core.beans.CrawlerFtpConfigBean;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerBaseBean;


/**
 * 配置值对象
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午3:38:48
 * @version 1.0
 */
public class CrawlerConfigBean extends CrawlerBaseBean{
	/**配置ID*/
	private Integer configId;
	/**配置类型*/
	private Integer configType;
	/**配置主键集合*/
	private List<Integer> configIdList;
	/**配置内容:JSON格式*/
	private String configContent;
	/**FTP配置*/
	private CrawlerFtpConfigBean crawlerFtpConfigBean;
	/**FTP配置*/
	private CrawlerDiyDataConfigBean crawlerDiyDataConfigBean;
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
	/**
	 * @return the crawlerFtpConfigBean
	 */
	public CrawlerFtpConfigBean getCrawlerFtpConfigBean() {
		return crawlerFtpConfigBean;
	}
	/**
	 * @param crawlerFtpConfigBean the crawlerFtpConfigBean to set
	 */
	public void setCrawlerFtpConfigBean(CrawlerFtpConfigBean crawlerFtpConfigBean) {
		this.crawlerFtpConfigBean = crawlerFtpConfigBean;
	}
	/**
	 * @return the crawlerDiyDataConfigBean
	 */
	public CrawlerDiyDataConfigBean getCrawlerDiyDataConfigBean() {
		return crawlerDiyDataConfigBean;
	}
	/**
	 * @param crawlerDiyDataConfigBean the crawlerDiyDataConfigBean to set
	 */
	public void setCrawlerDiyDataConfigBean(
			CrawlerDiyDataConfigBean crawlerDiyDataConfigBean) {
		this.crawlerDiyDataConfigBean = crawlerDiyDataConfigBean;
	}
	
	

}
