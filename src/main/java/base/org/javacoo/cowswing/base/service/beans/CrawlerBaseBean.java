package org.javacoo.cowswing.base.service.beans;

import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;



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
