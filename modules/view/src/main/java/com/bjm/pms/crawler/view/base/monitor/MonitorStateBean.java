/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.base.monitor;

/**
 * 监控状态值对象
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-25 上午10:14:16
 * @version 1.0
 */
public class MonitorStateBean {
	/**标题*/
	private String title;
	/**主键*/
	private int key;
	/**总数*/
	private int totalNum;
	/**完成数*/
	private int completeNum;
	/**描述*/
    private String desc;
    
	public MonitorStateBean() {
		super();
	}

	/**
	 * @param title
	 * @param key
	 * @param totalNum
	 * @param completeNum
	 * @param desc
	 */
	public MonitorStateBean(String title, int key, int totalNum,
			int completeNum, String desc) {
		super();
		this.title = title;
		this.key = key;
		this.totalNum = totalNum;
		this.completeNum = completeNum;
		this.desc = desc;
	}






	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}



	/**
	 * @param key the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}



	/**
	 * @return the totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @return the completeNum
	 */
	public int getCompleteNum() {
		return completeNum;
	}

	/**
	 * @param completeNum the completeNum to set
	 */
	public void setCompleteNum(int completeNum) {
		this.completeNum = completeNum;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

}
