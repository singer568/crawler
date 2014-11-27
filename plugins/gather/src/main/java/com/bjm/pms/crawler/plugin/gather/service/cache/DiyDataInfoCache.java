/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service.cache;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.core.beans.CrawlerDiyDataConfigBean;
import com.bjm.pms.crawler.plugin.core.service.beans.CrawlerConfigBean;
import com.bjm.pms.crawler.plugin.core.service.beans.CrawlerConfigCriteria;
import com.bjm.pms.crawler.plugin.gather.constant.SystemConstant;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.core.cache.support.AbstractCowSwingCache;
import com.bjm.pms.crawler.view.core.cache.support.CacheKeyConstant;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.core.event.CowSwingObserver;

/**
 * 自定义数据缓存
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-24 上午9:09:50
 * @version 1.0
 */
@Component("diyDataInfoCache")
public class DiyDataInfoCache extends AbstractCowSwingCache<List<CrawlerDiyDataConfigBean>>{

	/**
	 * FTP配置服务类
	 */
	@Resource(name="crawlerConfigService")
	private ICrawlerService<CrawlerConfigBean,CrawlerConfigCriteria> crawlerConfigService;
	
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.support.AbstractCrawlerCache#loadDataToCache()
	 */
	@Override
	protected List<CrawlerDiyDataConfigBean> loadDataToCache() {
		List<CrawlerDiyDataConfigBean> diyDataConfigList = new ArrayList<CrawlerDiyDataConfigBean>();
		CrawlerConfigCriteria criteria = new CrawlerConfigCriteria();
		criteria.setConfigType(Constant.CRAWLER_CONFIG_TYPE_DIYDATA);
		List<CrawlerConfigBean> result = crawlerConfigService.getList(criteria,SystemConstant.SQLMAP_ID_LIST_CRAWLER_CINFIG);
		if(CollectionUtils.isNotEmpty(result)){
			logger.info("开始加载自定义数据信息至缓存");
			String info = "开始加载自定义数据信息至缓存";
			CowSwingObserver.getInstance().notifyEvents(new CowSwingEvent(this,CowSwingEventType.CacheConfigChangeEvent,info));
			
			for(CrawlerConfigBean crawlerConfigBean : result){
				if(null != crawlerConfigBean.getCrawlerDiyDataConfigBean()){
					diyDataConfigList.add(crawlerConfigBean.getCrawlerDiyDataConfigBean());
				}
			}
			logger.info("加载自定义数据信息至缓存结束");
			info = "加载自定义数据信息至缓存结束";
			CowSwingObserver.getInstance().notifyEvents(new CowSwingEvent(this,CowSwingEventType.CacheConfigChangeEvent,info));
		}
		return diyDataConfigList;
	}

	/**
	 * @param crawlerConfigService the crawlerConfigService to set
	 */
	public void setCrawlerConfigService(
			ICrawlerService<CrawlerConfigBean, CrawlerConfigCriteria> crawlerConfigService) {
		this.crawlerConfigService = crawlerConfigService;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.ICrawlerCache#getCacheKey()
	 */
	@Override
	public String getCacheKey() {
		return CacheKeyConstant.CACHE_KEY_DIYDATA;
	}

	
}
