/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.core.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.bjm.pms.crawler.persist.PaginationSupport;
import com.bjm.pms.crawler.plugin.core.beans.CrawlerDiyDataConfigBean;
import com.bjm.pms.crawler.plugin.core.beans.CrawlerFtpConfigBean;
import com.bjm.pms.crawler.plugin.core.domain.CrawlerConfig;
import com.bjm.pms.crawler.plugin.core.service.beans.CrawlerConfigBean;
import com.bjm.pms.crawler.plugin.core.service.beans.CrawlerConfigCriteria;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.service.AbstractCrawlerService;

/**
 * 系统配置服务类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午4:04:48
 * @version 1.0
 */
@Service("crawlerConfigService")
public class CrawlerConfigServiceImpl extends AbstractCrawlerService<CrawlerConfigBean,CrawlerConfig,CrawlerConfigCriteria>{

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doInsert(org.javacoo.crawler.service.beans.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected int doInsert(CrawlerConfigBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		CrawlerConfig crawlerConfig = new CrawlerConfig();
		BeanUtils.copyProperties(crawlerConfig, t);
		translateConfingToString(t,crawlerConfig);
		this.getPersistService().insertBySqlMap(sqlMapId, crawlerConfig);
		return crawlerConfig.getConfigId();
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doUpdate(org.javacoo.crawler.service.beans.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected int doUpdate(CrawlerConfigBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		CrawlerConfig crawlerConfig = new CrawlerConfig();
		BeanUtils.copyProperties(crawlerConfig, t);
		translateConfingToString(t,crawlerConfig);
		return this.getPersistService().updateBySqlMap(sqlMapId, crawlerConfig);
	}
	/**
	 * 转换配置参数对象为json格式字符串
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-22 下午4:32:02
	 * @version 1.0
	 * @exception 
	 * @param bean
	 * @param crawlerConfig
	 */
	private void translateConfingToString(CrawlerConfigBean bean,CrawlerConfig crawlerConfig){
		if(Constant.CRAWLER_CONFIG_TYPE_FTP == bean.getConfigType()){
			crawlerConfig.setConfigContent(formatObjectToJsonString(bean.getCrawlerFtpConfigBean()));
		}else if(Constant.CRAWLER_CONFIG_TYPE_DIYDATA == bean.getConfigType()){
			crawlerConfig.setConfigContent(formatObjectToJsonString(bean.getCrawlerDiyDataConfigBean()));
		}
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doDelete(org.javacoo.crawler.service.beans.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected int doDelete(CrawlerConfigBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		CrawlerConfig crawlerConfig = new CrawlerConfig();
		BeanUtils.copyProperties(crawlerConfig, t);
		return this.getPersistService().deleteBySqlMap(sqlMapId, crawlerConfig);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGet(org.javacoo.crawler.service.beans.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected CrawlerConfig doGet(CrawlerConfigBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("configId", t.getConfigId());
		return (CrawlerConfig)this.getPersistService().findObjectBySqlMap(sqlMapId, paramMap);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetList(java.lang.Object, java.lang.String)
	 */
	@Override
	protected List<CrawlerConfig> doGetList(CrawlerConfigCriteria q,
			String sqlMapId) throws IllegalAccessException,
			InvocationTargetException {
		return (List<CrawlerConfig>) this.getPersistService().findListBySqlMap(sqlMapId, q);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetPaginatedList(java.lang.Object, java.lang.String)
	 */
	@Override
	protected PaginationSupport<CrawlerConfig> doGetPaginatedList(
			CrawlerConfigCriteria q, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		return (PaginationSupport<CrawlerConfig>) this.getPersistService().findPaginatedListBySqlMap(sqlMapId, q, q.getStartIndex(), q.getPageSize());
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#translateBean(java.lang.Object)
	 */
	@Override
	protected CrawlerConfigBean translateBean(CrawlerConfig e)
			throws IllegalAccessException, InvocationTargetException {
		CrawlerConfigBean crawlerConfigBean = new CrawlerConfigBean();
		BeanUtils.copyProperties(crawlerConfigBean, e);
		if(Constant.CRAWLER_CONFIG_TYPE_FTP == e.getConfigType()){
			crawlerConfigBean.setCrawlerFtpConfigBean((CrawlerFtpConfigBean)formatStringToObject(e.getConfigContent(),CrawlerFtpConfigBean.class));
		}else if(Constant.CRAWLER_CONFIG_TYPE_DIYDATA == e.getConfigType()){
			crawlerConfigBean.setCrawlerDiyDataConfigBean((CrawlerDiyDataConfigBean)formatStringToObject(e.getConfigContent(),CrawlerDiyDataConfigBean.class));
		}
		return crawlerConfigBean;
	}

}
