package com.bjm.pms.crawler.view.base.service.beans;

import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;



/**
 * 爬虫值对象基类
 *@author DuanYong
 *@since 2012-11-28下午9:19:16
 *@version 1.0
 */
public class CrawlerBaseBean {
	/**事件对象*/
	private CowSwingEvent cowSwingEvent;
	

	public CowSwingEvent getCowSwingEvent() {
		if(null == cowSwingEvent){
			cowSwingEvent = new CowSwingEvent(this,CowSwingEventType.NoTableChangeEvent);
		}
		return cowSwingEvent;
	}

	public void setCowSwingEvent(CowSwingEvent cowSwingEvent) {
		this.cowSwingEvent = cowSwingEvent;
	}
	
	

}
