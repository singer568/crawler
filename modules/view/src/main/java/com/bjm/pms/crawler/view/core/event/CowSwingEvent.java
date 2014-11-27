/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.event;

import java.util.EventObject;

/**
 * 事件对象
 *@author DuanYong
 *@since 2012-11-4下午10:18:19
 *@version 1.0
 */
public class CowSwingEvent extends EventObject{
	private static final long serialVersionUID = 1L;
	/**事件信息*/
	private String eventInfo;
	/**事件类型*/
	private CowSwingEventType eventType;
	/**事件对象*/
	private Object eventObject;
	public CowSwingEvent(Object source){
		super(source);
	}
	public CowSwingEvent(Object source, CowSwingEventType eventType) {
		super(source);
		this.eventType = eventType;
	}
	public CowSwingEvent(Object source, CowSwingEventType eventType,Object eventObject) {
		super(source);
		this.eventType = eventType;
		this.eventObject = eventObject;
	}
	public CowSwingEvent(Object source, CowSwingEventType eventType,Object eventObject,String eventInfo) {
		super(source);
		this.eventType = eventType;
		this.eventObject = eventObject;
		this.eventInfo = eventInfo;
	}
	public CowSwingEventType getEventType(){
		return eventType;
	}
	public Object getEventObject() {
		return eventObject;
	}
	public String getEventInfo() {
		return eventInfo;
	}
	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}
	
}
