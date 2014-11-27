/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.event;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 事件观察者
 *@author DuanYong
 *@since 2012-11-4下午10:34:22
 *@version 1.0
 */
public class CowSwingObserver {
	private Logger logger = Logger.getLogger(this.getClass());
	private static CowSwingObserver observer;
	private CopyOnWriteArrayList<CowSwingListener> repository = new CopyOnWriteArrayList<CowSwingListener>();
	
	private CowSwingObserver(){}
	
	public static CowSwingObserver getInstance(){
		if( observer== null){
			observer = new CowSwingObserver();
		}
		return observer;
	}
	public void addCrawlerListener(CowSwingListener crawlerListener) {
		repository.add(crawlerListener);
		logger.info("添加监听事件: " + crawlerListener);
	}

	public void removeCrawlerListener(CowSwingListener crawlerListener) {
		logger.info("删除前注册对象数: " + repository.size());
		repository.remove(crawlerListener);
		logger.info("删除后注册对象数: " + repository.size());
		logger.info("删除监听事件: " + crawlerListener);
	}

	@SuppressWarnings("unchecked")
	public void notifyEvents(CowSwingEvent crawlerEvent) {
		CopyOnWriteArrayList<CowSwingListener> tempList = null;
		logger.info("当前注册对象数: " + repository.size());
		logger.info("触发通知事件: " + crawlerEvent);
		synchronized (this) {
			tempList = (CopyOnWriteArrayList<CowSwingListener>) repository.clone();
			if(CollectionUtils.isNotEmpty(tempList)){
				int size = tempList.size();
				for(CowSwingListener listener : tempList){
					 logger.info("通知执行事件 "+ (size--)+ " = " + listener);
					 listener.update(crawlerEvent);
				}
			}
		}

	}
}
