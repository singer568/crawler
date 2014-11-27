package com.bjm.pms.crawler.plugin.gather.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.bjm.pms.crawler.persist.PaginationSupport;
import com.bjm.pms.crawler.plugin.gather.domain.CrawlerContentComment;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCommentBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCommentCriteria;
import com.bjm.pms.crawler.view.base.service.AbstractCrawlerService;

/**
 * 采集内容评论服务类
 *@author DuanYong
 *@since 2012-11-27上午11:16:27
 *@version 1.0
 */
@Service("crawlerContentCommentService")
public class CrawlerContentCommentServiceImpl extends AbstractCrawlerService<CrawlerContentCommentBean,CrawlerContentComment,CrawlerContentCommentCriteria>{

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doInsert(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doInsert(CrawlerContentCommentBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContentComment crawlerContentComment = new CrawlerContentComment();
		BeanUtils.copyProperties(crawlerContentComment, bean);
		this.getPersistService().insertBySqlMap(sqlMapId, crawlerContentComment);
		return crawlerContentComment.getCommentId();
	}
	

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doUpdate(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doUpdate(CrawlerContentCommentBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContentComment crawlerContentComment = new CrawlerContentComment();
		BeanUtils.copyProperties(crawlerContentComment, bean);
		return this.getPersistService().updateBySqlMap(sqlMapId, crawlerContentComment);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doDelete(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doDelete(CrawlerContentCommentBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContentComment crawlerContentComment = new CrawlerContentComment();
		BeanUtils.copyProperties(crawlerContentComment, bean);
		return this.getPersistService().deleteBySqlMap(sqlMapId, crawlerContentComment);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGet(java.lang.String, java.lang.Object)
	 */
	@Override
	protected CrawlerContentComment doGet(CrawlerContentCommentBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("commentId", bean.getCommentId());
		return (CrawlerContentComment) this.getPersistService().findObjectBySqlMap(sqlMapId, paramMap);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetList(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<CrawlerContentComment> doGetList(CrawlerContentCommentCriteria q,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		if(null != t.getRuleId()){
//			paramMap.put("ruleId", t.getRuleId());
//		}
//		if(null != t.getContentId()){
//			paramMap.put("contentId", t.getContentId());
//		}
		return (List<CrawlerContentComment>) this.getPersistService().findListBySqlMap(sqlMapId, q);
	}

	protected CrawlerContentCommentBean translateBean(CrawlerContentComment crawlerContentComment)throws IllegalAccessException, InvocationTargetException{
    	CrawlerContentCommentBean crawlerContentCommentBean = new CrawlerContentCommentBean();
    	BeanUtils.copyProperties(crawlerContentCommentBean, crawlerContentComment);
    	return crawlerContentCommentBean;
    }


	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetPaginatedList(java.lang.Object, java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected PaginationSupport<CrawlerContentComment> doGetPaginatedList(
			CrawlerContentCommentCriteria q, String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		return (PaginationSupport<CrawlerContentComment>) this.getPersistService().findPaginatedListBySqlMap(sqlMapId, q, q.getStartIndex(), q.getPageSize());
	}
}
