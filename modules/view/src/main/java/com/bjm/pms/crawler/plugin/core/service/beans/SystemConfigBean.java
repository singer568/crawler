/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.core.service.beans;

/**
 * 系统基本参数配置bean
 *@author DuanYong
 *@since 2013-3-11下午3:36:54
 *@version 1.0
 */
public class SystemConfigBean {
	/**主窗口宽*/
	private int frameWidth;
	/**主窗口高*/
	private int frameHeight;
	/**是否显示广告*/
	private String showAdvertisement;
	/**广告地址*/
	private String advertisementPath;
	/**是否播放音乐*/
	private String showMusic;
	/**欢迎音乐*/
	private String welocmeMusicName;
	/**提醒音乐*/
	private String msgMusicName;
	/**版本*/
	private String version;
	public int getFrameWidth() {
		return frameWidth;
	}
	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}
	public int getFrameHeight() {
		return frameHeight;
	}
	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}
	public String getShowAdvertisement() {
		return showAdvertisement;
	}
	public void setShowAdvertisement(String showAdvertisement) {
		this.showAdvertisement = showAdvertisement;
	}
	public String getShowMusic() {
		return showMusic;
	}
	public void setShowMusic(String showMusic) {
		this.showMusic = showMusic;
	}
	public String getWelocmeMusicName() {
		return welocmeMusicName;
	}
	public void setWelocmeMusicName(String welocmeMusicName) {
		this.welocmeMusicName = welocmeMusicName;
	}
	public String getMsgMusicName() {
		return msgMusicName;
	}
	public void setMsgMusicName(String msgMusicName) {
		this.msgMusicName = msgMusicName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAdvertisementPath() {
		return advertisementPath;
	}
	public void setAdvertisementPath(String advertisementPath) {
		this.advertisementPath = advertisementPath;
	}
	
	
	

}
