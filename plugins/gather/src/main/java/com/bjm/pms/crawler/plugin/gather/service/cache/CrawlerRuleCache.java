/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleCriteria;
import com.bjm.pms.crawler.view.base.constant.GatherConstant;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.view.core.cache.support.AbstractCowSwingCache;
import com.bjm.pms.crawler.view.core.cache.support.CacheKeyConstant;

/**
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-6 上午9:08:51
 * @version 1.0
 */
@Service("crawlerRuleCache")
public class CrawlerRuleCache extends AbstractCowSwingCache<List<CrawlerRuleBean>>{
	/**规则服务类*/
	@Resource(name="crawlerRuleService")
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.ICrawlerCache#getCacheKey()
	 */
	@Override
	public String getCacheKey() {
		return CacheKeyConstant.CACHE_KEY_RULE;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.support.AbstractCrawlerCache#loadDataToCache()
	 */
	@Override
	protected List<CrawlerRuleBean> loadDataToCache() {
		List<CrawlerRuleBean> result = crawlerRuleService.getList(new CrawlerRuleCriteria(),GatherConstant.SQLMAP_ID_LIST_CRAWLER_RULE);
		return result;
	}

	/**
	 * @param crawlerRuleService the crawlerRuleService to set
	 */
	public void setCrawlerRuleService(
			ICrawlerService<CrawlerRuleBean, CrawlerRuleCriteria> crawlerRuleService) {
		this.crawlerRuleService = crawlerRuleService;
	}
    
}
