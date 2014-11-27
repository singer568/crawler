/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.cache.support;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.core.cache.ICowSwingCache;
import org.javacoo.cowswing.core.cache.ICowSwingCacheManager;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.core.event.CowSwingObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 缓存管理类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-24 上午10:26:10
 * @version 1.0
 */
@Component("cowSwingCacheManager")
public class CowSwingCacheManager implements ICowSwingCacheManager{
	/**缓存*/
	private Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

	@Autowired
	private Map<String,ICowSwingCache<?>> cowSwingCacheMap;

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		loadCache();
	}
	
	

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.ICrawlerCacheManager#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String key) {
		if(this.cache.containsKey(key)){
			return this.cache.get(key);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.ICrawlerCacheManager#clear()
	 */
	@Override
	public void clear() {
		this.cache.clear();
	}



	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.ICrawlerCacheManager#loadCacheByKey(java.lang.String)
	 */
	@Override
	public Object loadCacheByKey(String cacheKey){
		Object obj = this.cowSwingCacheMap.get(cacheKey).load();
		if(null != obj){
			if(StringUtils.isNotBlank(this.cowSwingCacheMap.get(cacheKey).getCacheKey()) && !this.cache.containsKey(this.cowSwingCacheMap.get(cacheKey).getCacheKey())){
				this.cache.put(this.cowSwingCacheMap.get(cacheKey).getCacheKey(), obj);
			}
		}
		return obj;
	}



	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.ICrawlerCacheManager#loadCache()
	 */
	@Override
	public void loadCache() {
		if(null != this.cowSwingCacheMap && !this.cowSwingCacheMap.isEmpty()){
			String tempKey = "";
			String info = "开始加载信息至缓存";
			CowSwingObserver.getInstance().notifyEvents(new CowSwingEvent(this,CowSwingEventType.CacheConfigChangeEvent,info));
			for(Iterator<String> it = this.cowSwingCacheMap.keySet().iterator();it.hasNext();){
				tempKey = it.next();
				loadCacheByKey(tempKey);
			}
			info = "加载信息至缓存结束";
			CowSwingObserver.getInstance().notifyEvents(new CowSwingEvent(this,CowSwingEventType.CacheConfigChangeEvent,info));
		}
	}
	
	
	
	
	

}
