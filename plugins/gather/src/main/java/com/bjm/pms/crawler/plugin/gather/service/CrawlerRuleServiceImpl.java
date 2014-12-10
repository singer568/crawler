package com.bjm.pms.crawler.plugin.gather.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.bjm.pms.crawler.persist.PaginationSupport;
import com.bjm.pms.crawler.plugin.core.beans.CrawlerFtpConfigBean;
import com.bjm.pms.crawler.plugin.core.service.beans.RuleBaseBean;
import com.bjm.pms.crawler.plugin.core.service.beans.RuleFieldsBean;
import com.bjm.pms.crawler.plugin.gather.domain.CrawlerRule;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleCriteria;
import com.bjm.pms.crawler.plugin.tool.ui.bean.ImageSettingBean;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.AbstractCrawlerService;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.view.base.service.beans.RuleCommentBean;
import com.bjm.pms.crawler.view.base.service.beans.RuleContentBean;
import com.bjm.pms.crawler.view.base.service.beans.RuleContentPageBean;
import com.bjm.pms.crawler.view.base.service.beans.RuleDataBaseBean;
import com.bjm.pms.crawler.view.base.service.beans.RuleLocalBean;
import com.bjm.pms.crawler.view.base.utils.DateUtil;

/**
 * 采集规则服务类
 *@author DuanYong
 *@since 2012-11-6下午9:20:12
 *@version 1.0
 */
@Service("crawlerRuleService")
public class CrawlerRuleServiceImpl extends AbstractCrawlerService<CrawlerRuleBean,CrawlerRule,CrawlerRuleCriteria>{

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doInsert(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doInsert(CrawlerRuleBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerRule crawlerRule = new CrawlerRule();
		BeanUtils.copyProperties(crawlerRule, bean);
		translateConfingToString(bean,crawlerRule);
		this.getPersistService().insertBySqlMap(sqlMapId, crawlerRule);
		return crawlerRule.getRuleId();
	}
	/**
	 * 转换配置参数对象为json格式字符串
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-15 上午10:55:20
	 * @param bean
	 */
	private void translateConfingToString(CrawlerRuleBean bean,CrawlerRule crawlerRule){
		if(null != bean.getRuleBaseBean()){
			crawlerRule.setRuleBaseConfig(formatObjectToJsonString(bean.getRuleBaseBean()));
		}
		if(null != bean.getRuleContentBean()){
			crawlerRule.setRuleContentConfig(formatObjectToJsonString(bean.getRuleContentBean()));
		}
		if(null != bean.getRuleContentPageBean()){
			crawlerRule.setRuleContentPageConfig(formatObjectToJsonString(bean.getRuleContentPageBean()));
		}
		if(null != bean.getRuleCommentBean()){
			crawlerRule.setRuleCommentConfig(formatObjectToJsonString(bean.getRuleCommentBean()));
		}
		if(null != bean.getRuleFieldsBean()){
			crawlerRule.setRuleFieldsConfig(formatObjectToJsonString(bean.getRuleFieldsBean()));
		}
		if(null != bean.getRuleDataBaseBean()){
			crawlerRule.setRuleDataBaseConfig(formatObjectToJsonString(bean.getRuleDataBaseBean()));
		}
		if(null != bean.getCrawlerFtpConfigBean()){
			crawlerRule.setRuleFtpConfig(formatObjectToJsonString(bean.getCrawlerFtpConfigBean()));
		}
		if(null != bean.getImageSettingBean()){
			crawlerRule.setRuleImageSettingConfig(formatObjectToJsonString(bean.getImageSettingBean()));
		}
		if(null != bean.getRuleLocalBean()){
			crawlerRule.setRuleLocalConfig(formatObjectToJsonString(bean.getRuleLocalBean()));
		}
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doUpdate(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doUpdate(CrawlerRuleBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerRule crawlerRule = new CrawlerRule();
		BeanUtils.copyProperties(crawlerRule, bean);
		translateConfingToString(bean,crawlerRule);
		return this.getPersistService().updateBySqlMap(sqlMapId, crawlerRule);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doDelete(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doDelete(CrawlerRuleBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerRule crawlerRule = new CrawlerRule();
		BeanUtils.copyProperties(crawlerRule, bean);
		return this.getPersistService().deleteBySqlMap(sqlMapId, crawlerRule);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGet(java.lang.String, java.lang.Object)
	 */
	@Override
	protected CrawlerRule doGet(CrawlerRuleBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("ruleId", bean.getRuleId());
		return (CrawlerRule) this.getPersistService().findObjectBySqlMap(sqlMapId, paramMap);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetList(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<CrawlerRule> doGetList(CrawlerRuleCriteria q,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		return (List<CrawlerRule>) this.getPersistService().findListBySqlMap(sqlMapId, q);
	}
    
	protected CrawlerRuleBean translateBean(CrawlerRule crawlerRule) throws IllegalAccessException, InvocationTargetException{
		CrawlerRuleBean crawlerRuleBean = new CrawlerRuleBean();
		BeanUtils.copyProperties(crawlerRuleBean, crawlerRule);
    	if(null != crawlerRuleBean.getStartTime()){
			crawlerRuleBean.setStartTimeStr(DateUtil.dateToStr(crawlerRuleBean.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		if(null != crawlerRuleBean.getEndTime()){
			crawlerRuleBean.setEndTimeStr(DateUtil.dateToStr(crawlerRuleBean.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(crawlerRuleBean.getRuleBaseConfig())){
			crawlerRuleBean.setRuleBaseBean((RuleBaseBean)formatStringToObject(crawlerRuleBean.getRuleBaseConfig(),RuleBaseBean.class));
		}
		if(StringUtils.isNotBlank(crawlerRuleBean.getRuleContentConfig())){
			crawlerRuleBean.setRuleContentBean((RuleContentBean)formatStringToObject(crawlerRuleBean.getRuleContentConfig(),RuleContentBean.class));
		}
		if(StringUtils.isNotBlank(crawlerRuleBean.getRuleContentPageConfig())){
			crawlerRuleBean.setRuleContentPageBean((RuleContentPageBean)formatStringToObject(crawlerRuleBean.getRuleContentPageConfig(),RuleContentPageBean.class));
		}
		if(StringUtils.isNotBlank(crawlerRuleBean.getRuleCommentConfig())){
			crawlerRuleBean.setRuleCommentBean((RuleCommentBean)formatStringToObject(crawlerRuleBean.getRuleCommentConfig(),RuleCommentBean.class));
		}
		if(StringUtils.isNotBlank(crawlerRuleBean.getRuleFieldsConfig())){
			crawlerRuleBean.setRuleFieldsBean((RuleFieldsBean)formatStringToObject(crawlerRuleBean.getRuleFieldsConfig(),RuleFieldsBean.class));
		}
		if(StringUtils.isNotBlank(crawlerRuleBean.getRuleDataBaseConfig())){
			crawlerRuleBean.setRuleDataBaseBean((RuleDataBaseBean)formatStringToObject(crawlerRuleBean.getRuleDataBaseConfig(),RuleDataBaseBean.class));
		}
		if(StringUtils.isNotBlank(crawlerRuleBean.getRuleFtpConfig())){
			crawlerRuleBean.setCrawlerFtpConfigBean((CrawlerFtpConfigBean)formatStringToObject(crawlerRuleBean.getRuleFtpConfig(),CrawlerFtpConfigBean.class));
		}
		if(StringUtils.isNotBlank(crawlerRuleBean.getRuleImageSettingConfig())){
			crawlerRuleBean.setImageSettingBean((ImageSettingBean)formatStringToObject(crawlerRuleBean.getRuleImageSettingConfig(),ImageSettingBean.class));
		}
		if(StringUtils.isNotBlank(crawlerRuleBean.getRuleLocalConfig())){
			crawlerRuleBean.setRuleLocalBean((RuleLocalBean)formatStringToObject(crawlerRuleBean.getRuleLocalConfig(),RuleLocalBean.class));
		}
		if(StringUtils.isNotBlank(crawlerRuleBean.getStatus())){
			if(Constant.TASK_STATUS_RUN.equals(crawlerRuleBean.getStatus())){
				crawlerRuleBean.setStatusStr(LanguageLoader.getString("Task.run"));
			}else if(Constant.TASK_STATUS_PAUSE.equals(crawlerRuleBean.getStatus())){
				crawlerRuleBean.setStatusStr(LanguageLoader.getString("Task.pause"));
			}else if(Constant.TASK_STATUS_COMPLETE.equals(crawlerRuleBean.getStatus())){
				crawlerRuleBean.setStatusStr(LanguageLoader.getString("Task.complete"));
			}else{
				crawlerRuleBean.setStatusStr(LanguageLoader.getString("Task.stop"));
			}
		}
		return crawlerRuleBean;
    }
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetPaginatedList(org.javacoo.crawler.service.beans.CrawlerBaseBean, java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected PaginationSupport<CrawlerRule> doGetPaginatedList(
			CrawlerRuleCriteria q, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		return (PaginationSupport<CrawlerRule>) this.getPersistService().findPaginatedListBySqlMap(sqlMapId, q, q.getStartIndex(), q.getPageSize());
	}
	
}
