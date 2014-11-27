package com.bjm.pms.crawler.plugin.gather.service.beans;

import java.util.Date;
import java.util.List;

import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerBaseBean;


/**
 * 采集内容值对象
 *@author DuanYong
 *@since 2012-11-27上午9:37:47
 *@version 1.0
 */
public class CrawlerContentBean extends CrawlerBaseBean{
	/**内容主键*/
	private Integer contentId;
	/**内容ID集合*/
	private List<Integer> contentIdList;
	/**规则主键*/
	private Integer ruleId;
	/**规则主键集合*/
	private List<Integer> ruleIdList;
	/**标题*/
	private String title;
	/**内容*/
	private String content;
	/**摘要*/
	private String brief;
	/**标题图片*/
	private String titleImg;
	/**保存时间*/
	private Date saveDate;
	/**保存时间str*/
	private String saveDateStr;
	/**显示时间*/
	private Date viewDate;
	/**显示时间Str*/
	private String viewDateStr;
    /**是否已经入库*/
	private String hasSave = Constant.NO;
	/**内容评论*/
	private List<CrawlerContentCommentBean> crawlerContentCommentBeanList;
	/**内容分页*/
	private  List<CrawlerContentPaginationBean> crawlerContentPaginationBeanList;
	/**内容资源*/
	private  List<CrawlerContentResourceBean> crawlerContentResourceBeanList;
	
	
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	
	public List<Integer> getContentIdList() {
		return contentIdList;
	}
	public void setContentIdList(List<Integer> contentIdList) {
		this.contentIdList = contentIdList;
	}
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	
	public List<Integer> getRuleIdList() {
		return ruleIdList;
	}
	public void setRuleIdList(List<Integer> ruleIdList) {
		this.ruleIdList = ruleIdList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}
	public String getSaveDateStr() {
		return saveDateStr;
	}
	public void setSaveDateStr(String saveDateStr) {
		this.saveDateStr = saveDateStr;
	}
	
	public Date getViewDate() {
		return viewDate;
	}
	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}
	public String getViewDateStr() {
		return viewDateStr;
	}
	public void setViewDateStr(String viewDateStr) {
		this.viewDateStr = viewDateStr;
	}
	
	/**
	 * @return the hasSave
	 */
	public String getHasSave() {
		return hasSave;
	}
	/**
	 * @param hasSave the hasSave to set
	 */
	public void setHasSave(String hasSave) {
		this.hasSave = hasSave;
	}
	
	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}
	/**
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	/**
	 * @return the titleImg
	 */
	public String getTitleImg() {
		return titleImg;
	}
	/**
	 * @param titleImg the titleImg to set
	 */
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	public List<CrawlerContentCommentBean> getCrawlerContentCommentBeanList() {
		return crawlerContentCommentBeanList;
	}
	public void setCrawlerContentCommentBeanList(
			List<CrawlerContentCommentBean> crawlerContentCommentBeanList) {
		this.crawlerContentCommentBeanList = crawlerContentCommentBeanList;
	}
	public List<CrawlerContentPaginationBean> getCrawlerContentPaginationBeanList() {
		return crawlerContentPaginationBeanList;
	}
	public void setCrawlerContentPaginationBeanList(
			List<CrawlerContentPaginationBean> crawlerContentPaginationBeanList) {
		this.crawlerContentPaginationBeanList = crawlerContentPaginationBeanList;
	}
	public List<CrawlerContentResourceBean> getCrawlerContentResourceBeanList() {
		return crawlerContentResourceBeanList;
	}
	public void setCrawlerContentResourceBeanList(
			List<CrawlerContentResourceBean> crawlerContentResourceBeanList) {
		this.crawlerContentResourceBeanList = crawlerContentResourceBeanList;
	}
	
    
	
}
