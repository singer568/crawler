/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.bjm.pms.crawler.core.data.ContentBean;
import com.bjm.pms.crawler.core.data.uri.CrawlResURI;
import com.bjm.pms.crawler.persist.util.DBConnectionManager;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCommentBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCommentCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentPaginationBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentPaginationCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentResourceBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentResourceCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerExtendFieldBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerExtendFieldCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerTaskBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerTaskCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.SelectValueBean;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.constant.GatherConstant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.base.service.beans.ColumnBean;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.view.base.utils.DateFormatUtils;
import com.bjm.pms.crawler.view.base.utils.DateUtil;
import com.bjm.pms.crawler.view.base.utils.JsonUtils;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;

/**
 * 保存内容至远程数据库
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-30 下午10:29:17
 * @version 1.0
 */
public class SaveRemoteContent extends AbstractThreadService{
	protected Logger log = Logger.getLogger(this.getClass());
	/**规则服务类*/
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	/**采集内容服务类*/
	private ICrawlerService<CrawlerContentBean,CrawlerContentCriteria> crawlerContentService;
	/**采集内容评论服务类*/
	private ICrawlerService<CrawlerContentCommentBean,CrawlerContentCommentCriteria> crawlerContentCommentService;
	/**采集内容分页服务类*/
	private ICrawlerService<CrawlerContentPaginationBean,CrawlerContentPaginationCriteria> crawlerContentPaginationService;
	/**扩展字段服务类*/
	private ICrawlerService<CrawlerExtendFieldBean,CrawlerExtendFieldCriteria> crawlerExtendFieldService;
	/**扩展字段服务类*/
	private ICrawlerService<CrawlerContentResourceBean,CrawlerContentResourceCriteria> crawlerContentResourceService;
	/**数据库连接管理*/
	private DBConnectionManager connectionManager;
	/**规则ID*/
	private Integer ruleId;
	/**随机静态值MAP*/
	private Map<String,SelectValueBean> randomValueMap = new HashMap<String,SelectValueBean>();
	/**随机静态值*/
	private Map<String,String> randomValue = new HashMap<String,String>();
	
	
	/**
	 * @param crawlerRuleService
	 * @param crawlerContentService
	 * @param crawlerContentCommentService
	 * @param crawlerContentPaginationService
	 * @param crawlerExtendFieldService
	 * @param connectionManager
	 * @param ruleId
	 */
	public SaveRemoteContent(
			ICrawlerService<CrawlerRuleBean, CrawlerRuleCriteria> crawlerRuleService,
			ICrawlerService<CrawlerContentBean, CrawlerContentCriteria> crawlerContentService,
			ICrawlerService<CrawlerContentCommentBean, CrawlerContentCommentCriteria> crawlerContentCommentService,
			ICrawlerService<CrawlerContentPaginationBean, CrawlerContentPaginationCriteria> crawlerContentPaginationService,
			ICrawlerService<CrawlerExtendFieldBean, CrawlerExtendFieldCriteria> crawlerExtendFieldService,
			ICrawlerService<CrawlerTaskBean,CrawlerTaskCriteria> crawlerTaskService,
			ICrawlerService<CrawlerContentResourceBean,CrawlerContentResourceCriteria> crawlerContentResourceService,
			DBConnectionManager connectionManager, 
			Integer ruleId) {
		super(crawlerTaskService);
		this.crawlerRuleService = crawlerRuleService;
		this.crawlerContentService = crawlerContentService;
		this.crawlerContentCommentService = crawlerContentCommentService;
		this.crawlerContentPaginationService = crawlerContentPaginationService;
		this.crawlerExtendFieldService = crawlerExtendFieldService;
		this.crawlerContentResourceService = crawlerContentResourceService;
		this.connectionManager = connectionManager;
		this.ruleId = ruleId;
	}
	
	/**
	 * 将指定规则的内容保存到远程数据库
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午9:06:13
	 * @version 1.0
	 * @exception 
	 * @param ruleId
	 */
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.plugin.gather.service.AbstractThreadService#doRun()
	 */
	@Override
	protected void doRun(){
		CrawlerRuleBean rule = new CrawlerRuleBean();
		rule.setRuleId(this.ruleId);
		rule = this.crawlerRuleService.get(rule, GatherConstant.SQLMAP_ID_GET_CRAWLER_RULE);
		if(Boolean.valueOf(rule.getRuleDataBaseBean().getSaveToDataBaseFlag())){
			CrawlerContentCriteria crawlerContentCriteria = new CrawlerContentCriteria();
			crawlerContentCriteria.setRuleId(ruleId);
			crawlerContentCriteria.setHasSave(Constant.NO);
			List<CrawlerContentBean> resultList = this.crawlerContentService.getList(crawlerContentCriteria, GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT);
			if(CollectionUtils.isNotEmpty(resultList)){
				//插入任务
				insertTask(ruleId,resultList.size(),GatherConstant.TASK_TYPE_3,CowSwingEventType.SaveStartEvent);
				//休眠，避免锁表
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				ContentBean contentBean = null;
				List<CrawlerContentCommentBean> commentList = null;
				CrawlerContentCommentCriteria crawlerContentCommentCriteria = new CrawlerContentCommentCriteria();
				
				List<CrawlerContentPaginationBean> contentPageList = null;
				CrawlerContentPaginationCriteria crawlerContentPaginationCriteria = new CrawlerContentPaginationCriteria();
				
				List<CrawlerContentResourceBean> contentResList = null;
				CrawlerContentResourceCriteria crawlerContentResourceCriteria = new CrawlerContentResourceCriteria();
				
				
				List<CrawlerExtendFieldBean> extendFieldList = null;
				CrawlerExtendFieldCriteria crawlerExtendFieldCriteria = new CrawlerExtendFieldCriteria();
				String desc = "";
				for(CrawlerContentBean crawlerContentBean : resultList){
					contentBean = new ContentBean();
					contentBean.setBrief(crawlerContentBean.getBrief());
					contentBean.setContent(crawlerContentBean.getContent());
					contentBean.setTitle(crawlerContentBean.getTitle());
					contentBean.setTitleImg(crawlerContentBean.getTitleImg());
					
					crawlerContentCommentCriteria.setContentId(crawlerContentBean.getContentId());
					commentList = this.crawlerContentCommentService.getList(crawlerContentCommentCriteria, GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_COMMENT);
					if(CollectionUtils.isNotEmpty(commentList)){
						for(CrawlerContentCommentBean crawlerContentCommentBean : commentList){
							contentBean.getCommentList().add(crawlerContentCommentBean.getContent());
						}
					}
					crawlerContentPaginationCriteria.setContentId(crawlerContentBean.getContentId());
					contentPageList = this.crawlerContentPaginationService.getList(crawlerContentPaginationCriteria, GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_PAGINATION);
					if(CollectionUtils.isNotEmpty(contentPageList)){
						for(CrawlerContentPaginationBean crawlerContentPaginationBean : contentPageList){
							contentBean.getContentList().add(crawlerContentPaginationBean.getContent());
						}
					}
					crawlerContentResourceCriteria.setContentId(crawlerContentBean.getContentId());
					contentResList = this.crawlerContentResourceService.getList(crawlerContentResourceCriteria,GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_RESOURCE);
					if(CollectionUtils.isNotEmpty(contentResList)){
						CrawlResURI crawlResURI = null;
						for(CrawlerContentResourceBean crawlerContentResourceBean : contentResList){
							crawlResURI = new CrawlResURI();
							crawlResURI.setNewResUrl(crawlerContentResourceBean.getPath());
							crawlResURI.setName(crawlerContentResourceBean.getName());
							crawlResURI.setDesc(crawlerContentResourceBean.getName());
							contentBean.getResCrawlURIList().add(crawlResURI);
						}
					}
					crawlerExtendFieldCriteria.setContentId(crawlerContentBean.getContentId());
					extendFieldList = this.crawlerExtendFieldService.getList(crawlerExtendFieldCriteria, GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_EXTEND_FIELD);
					if(CollectionUtils.isNotEmpty(extendFieldList)){
						for(CrawlerExtendFieldBean crawlerExtendFieldBean : extendFieldList){
							contentBean.getFieldValueMap().put(crawlerExtendFieldBean.getFieldName(), crawlerExtendFieldBean.getFieldValue());
						}
					}
					//插入内容
					insertContentToTargetDataBase(contentBean,rule);
					randomValueMap.clear();
					randomValue.clear();
					//修改状态
					updateContentState(crawlerContentBean);
					desc = "保存内容："+contentBean.getTitle()+",至指定数据库 成功!";
					updateTask(this.ruleId,desc,GatherConstant.TASK_TYPE_3,CowSwingEventType.SaveStatusChangeEvent);
				}
				deleteTask(this.ruleId,GatherConstant.TASK_TYPE_3,LanguageLoader.getString("CrawlerMainFrame.CrawlSaveRemoteComplateTask"),CowSwingEventType.SaveFinishedEvent);
			}
		}
	}
	
	/**
	 * 修改内容状态为已保存
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-5-1 下午5:18:44
	 * @version 1.0
	 * @exception 
	 * @param contentBean
	 */
	private void updateContentState(CrawlerContentBean crawlerContentBean) {
		CrawlerContentBean contentBean = new CrawlerContentBean();
		contentBean.setContentId(crawlerContentBean.getContentId());
		contentBean.setHasSave(Constant.YES);
		crawlerContentService.update(contentBean, GatherConstant.SQLMAP_ID_UPDATE_CRAWLER_CONTENT);
	}

	/**
	 * 插入内容至指定数据库
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-2-24 上午10:17:20
	 * @param task
	 * @return void
	 */
	private void insertContentToTargetDataBase(ContentBean contentBean,CrawlerRuleBean rule) {
		Map<String,Map<String,ColumnBean>> tempMap = rule.getRuleDataBaseBean().getHasSelectedValueMap();
		Map<String,Map<String,ColumnBean>> tableMap = new HashMap<String,Map<String,ColumnBean>>();
		if(!tempMap.isEmpty()){
			String key = "";
			for(Iterator<String> it = tempMap.keySet().iterator();it.hasNext();){
				key = it.next();
				tableMap.put(key, tempMap.get(key));
			}
		}
		//主表，生成主键的表
		String primaryTableName = rule.getRuleDataBaseBean().getPrimaryTable();
		//内容表
		String contentTableName = findTableNameByType(tableMap,GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT);
		//内容分页表
		String contentPageTableName = findTableNameByType(tableMap,GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_PAGE);
		//内容评论表
		String commentTableName = findTableNameByType(tableMap,GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_COMMENT);
		//内容资源
		String contentResourceTableName = findTableNameByType(tableMap,GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_RESOURCE);
		
		int ky = 0;
		if(StringUtils.isNotBlank(primaryTableName) && StringUtils.isNotBlank(contentTableName)){
			//如果没有单独分页表
			if(StringUtils.isBlank(contentPageTableName)){
				dealWithContentPageation(contentBean,rule);
			}
			//生成主键
			ky = createPrimaryKey(primaryTableName,rule,contentBean,ky);
			if(ky == -1){
				return;
			}
			if(!primaryTableName.equals(contentTableName)){
				//插入内容
				insertValue(contentTableName,rule,contentBean,ky);
			}
			if(StringUtils.isNotBlank(commentTableName)){
				//插入评论
				insertLisValue(commentTableName,rule,contentBean,ky,contentBean.getCommentList());
				tableMap.remove(commentTableName);
			}
			if(StringUtils.isNotBlank(contentPageTableName)){
				//插入内容分页
				insertLisValue(contentPageTableName,rule,contentBean,ky,contentBean.getContentList());
				tableMap.remove(contentPageTableName);
			}
			if(StringUtils.isNotBlank(contentResourceTableName)){
				//插入资源
				insertResValue(contentResourceTableName,rule,contentBean,ky,contentBean.getResCrawlURIList());
				tableMap.remove(contentResourceTableName);
			}
			tableMap.remove(contentTableName);
			tableMap.remove(primaryTableName);
		}
		
		if(!tableMap.isEmpty()){
			String temp = "";
			for(Iterator<String> it = tableMap.keySet().iterator();it.hasNext();){
				temp = it.next();
				log.info("表名："+temp);
				insertValue(temp,rule,contentBean,ky);
			}
		}
	}
	
	/**
	 * 处理内容分页
	 * <p>方法说明:</>
	 * <li>单表内容分页</li>
	 * @author DuanYong
	 * @since 2014-8-26 下午3:54:13
	 * @version 1.0
	 * @exception 
	 * @param contentBean
	 * @param rule
	 */
	private void dealWithContentPageation(ContentBean contentBean,CrawlerRuleBean rule){
		if(StringUtils.isNotBlank(rule.getRuleDataBaseBean().getContentPageTag()) && !contentBean.getContentList().isEmpty()){
			StringBuilder str = new StringBuilder();
			str.append(contentBean.getContent());
			for(String content : contentBean.getContentList()){
				str.append(rule.getRuleDataBaseBean().getContentPageTag()).append(content);
			}
			contentBean.setContent(str.toString());
		}
	}
	private void insertLisValue(String tableName,CrawlerRuleBean rule,ContentBean contentBean,int ky,List<String> contentList){
		PreparedStatement pstm = null; 
		Connection conn = null;
		String dataBaseId = rule.getRuleDataBaseBean().getDataBaseId();
		Map<String, ColumnBean> columnMap = rule.getRuleDataBaseBean().getHasSelectedValueMap().get(tableName);
		try{
			conn = this.connectionManager.getConnection(dataBaseId);
			if(CollectionUtils.isNotEmpty(contentList)){
				for(String content : contentList){
					int pstmKy = 1;
					pstm = conn.prepareStatement(this.populateSql(tableName,columnMap),Statement.RETURN_GENERATED_KEYS);
					ColumnBean columnBean = null;
					for(Iterator<String> it = columnMap.keySet().iterator();it.hasNext();){
						columnBean = columnMap.get(it.next());
						setValueByType(contentBean,rule,pstm,columnBean.getColumnType(),pstmKy++,columnBean.getColumnValue(),columnBean.getColumnValueType(),ky,content);
					}
					pstm.execute();
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			this.connectionManager.freeConnection(dataBaseId, conn);
			try {
				if(null != pstm){
					pstm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private void insertResValue(String tableName,CrawlerRuleBean rule,ContentBean contentBean,int ky,List<CrawlResURI> contentList){
		PreparedStatement pstm = null; 
		Connection conn = null;
		String dataBaseId = rule.getRuleDataBaseBean().getDataBaseId();
		Map<String, ColumnBean> columnMap = rule.getRuleDataBaseBean().getHasSelectedValueMap().get(tableName);
		try{
			conn = this.connectionManager.getConnection(dataBaseId);
			if(CollectionUtils.isNotEmpty(contentList)){
				for(CrawlResURI res : contentList){
					int pstmKy = 1;
					pstm = conn.prepareStatement(this.populateSql(tableName,columnMap),Statement.RETURN_GENERATED_KEYS);
					ColumnBean columnBean = null;
					String value = "";
					for(Iterator<String> it = columnMap.keySet().iterator();it.hasNext();){
						columnBean = columnMap.get(it.next());
						if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_RESOURCE.equals(columnBean.getColumnValue())){
							value = res.getNewResUrl();
						}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_RESOURCE_NAME.equals(columnBean.getColumnValue())){
							value = res.getName();
						}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_RESOURCE_DESC.equals(columnBean.getColumnValue())){
							value = res.getDesc();
						}
						setValueByType(contentBean,rule,pstm,columnBean.getColumnType(),pstmKy++,columnBean.getColumnValue(),columnBean.getColumnValueType(),ky,value);
					}
					pstm.execute();
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			this.connectionManager.freeConnection(dataBaseId, conn);
			try {
				if(null != pstm){
					pstm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String populateSql(String tableName,Map<String, ColumnBean> columnMap){
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ").append(tableName); 
		sql.append(" (");
		int i = 0;
		for(Iterator<String> it = columnMap.keySet().iterator();it.hasNext();){
			if(i < columnMap.keySet().size() - 1){
				sql.append(it.next()+",");
			}else{
				sql.append(it.next());
			}
			i++;
		}
		sql.append(")");
		sql.append("VALUES (");
		i = 0;
		for(Iterator<String> it = columnMap.keySet().iterator();it.hasNext();){
			it.next();
			if(i < columnMap.keySet().size() - 1){
				sql.append("?,");
			}else{
				sql.append("?");
			}
			i++;
		}
		sql.append(")");
		log.info("=======SQL:"+sql);
		return sql.toString();
	} 
	private String findTableNameByType(Map<String,Map<String,ColumnBean>> tableMap,String teblaType){
		String table = "";
		//Map<String,Map<String,ColumnBean>> tableMap = rule.getRuleDataBaseBean().getHasSelectedValueMap();
		Map<String,ColumnBean> columnMap = null;
		boolean isFind = false;
		for(Iterator<String> it = tableMap.keySet().iterator();it.hasNext();){
			table = it.next();
			columnMap = tableMap.get(table);
			for(Iterator<String> column = columnMap.keySet().iterator();column.hasNext();){
				ColumnBean columnBean = columnMap.get(column.next());
				if(teblaType.equals(columnBean.getColumnValue())){
					isFind = true;
					break;
				}
			}
			if(isFind){
				break;
			}else{
				table = "";
			}
		}
		return table;
	}
	/**
	 * 根据字段类型设置值
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-3-4 上午8:54:29
	 * @param pstm
	 * @param type
	 * @param pos
	 * @param value
	 * @param valueType
	 * @return void
	 */
	private void setValueByType(ContentBean contentBean,CrawlerRuleBean rule,PreparedStatement pstm,String type,int pos,String value,String valueType,int ky,String staticValue){
		log.info("字段类型:"+type);
		log.info("字段值:"+value);
		log.info("值类型:"+valueType);
		log.info("静态值:"+staticValue);
		try {
			//如果是动态值
			if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC.equals(valueType)  || GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_KEY.equals(valueType) || GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_VALUE.equals(valueType)){
				if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT.equals(value)){
					value = contentBean.getContent();
				}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_ID.equals(value)){
					value = ky+"";
				}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_TITLE.equals(value)){
					value = contentBean.getTitle();
				}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_BRIEF.equals(value)){
					value = contentBean.getBrief();
				}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_VIEW_DATE.equals(value)){
					value = String.valueOf(getRandomDate(rule).getTime());
				}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_TITLE_IMG.equals(value)){
					value = contentBean.getTitleImg();
				}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_RESOURCE.equals(value)){
					value = staticValue;
				}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_RESOURCE_NAME.equals(value)){
					value = staticValue;
				}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_RESOURCE_DESC.equals(value)){
					value = staticValue;
				}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_PAGE.equals(value)){
					value = staticValue;
				}else if(GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_COMMENT.equals(value)){
					value = staticValue;
				}else if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_KEY.equals(valueType)){
					
				}else if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_VALUE.equals(valueType)){
					if(null != contentBean.getFieldValueMap() && StringUtils.isNotBlank(contentBean.getFieldValueMap().get(value))){
						value = contentBean.getFieldValueMap().get(value);
					}
				}else{
					if(null != contentBean.getFieldValueMap() && StringUtils.isNotBlank(contentBean.getFieldValueMap().get(value))){
						value = contentBean.getFieldValueMap().get(value);
					}
				}
			}else{//静态值
				if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC.equals(valueType) || GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MULIT.equals(valueType)){
					value = getRandomValue(value);
				}else{
					value = getMapRandomValue(value,valueType);
				}
			}
			log.info("动态值:"+value);
//			if(null != value){
//				value =  new String(value.getBytes("UTF-8"), "ISO-8859-1");
//				log.info("转码后的值:"+value);
//			}
			if(GatherConstant.BIGINT.equals(type)){
				pstm.setBigDecimal(pos, new BigDecimal(value));
			}else if(GatherConstant.VARCHAR.equals(type)){
				pstm.setString(pos,value);
			}else if(GatherConstant.DATE.equals(type)){
				Date date = new Date(Long.valueOf(value));
				pstm.setDate(pos,new java.sql.Date(date.getTime()));
			}else if(GatherConstant.TIMESTAMP.equals(type)){
				Date date = new Date(Long.valueOf(value));
				pstm.setTimestamp(pos,new java.sql.Timestamp(date.getTime()));
			}else if(GatherConstant.DATETIME.equals(type)){
				Date date = new Date(Long.valueOf(value));
				pstm.setTimestamp(pos,new java.sql.Timestamp(date.getTime()));
			}else if(GatherConstant.INTEGER.equals(type)){
				pstm.setInt(pos,Integer.valueOf(value));
			}else if(GatherConstant.INT.equals(type)){
				pstm.setInt(pos,Integer.valueOf(value));
			}else if(GatherConstant.TINYINT.equals(type)){
				pstm.setInt(pos,Integer.valueOf(value));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 取得MAP静态值随机值
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-1 下午5:21:48
	 * @version 1.0
	 * @exception 
	 * @param value
	 * @param type
	 * @return
	 */
	private String getMapRandomValue(String value,String type){
		String key = String.valueOf(value.hashCode());
		if(!randomValueMap.containsKey(key)){
			List<SelectValueBean> staticValueList = new ArrayList<SelectValueBean>();
			if(StringUtils.isNotBlank(value)){
				List tempList = (ArrayList)JsonUtils.formatStringToObject(value, ArrayList.class);
				for(Object obj : tempList){
					staticValueList.add((SelectValueBean)JsonUtils.formatStringToObject(obj.toString(), SelectValueBean.class));
				}
			}
			if(!CollectionUtils.isEmpty(staticValueList)){
				randomValueMap.put(key, staticValueList.get((int)(Math.random()*(staticValueList.size()))+0));	
			}
		}
		if(!randomValueMap.isEmpty()){
			if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MAP_VALUE.equals(type)){
				return randomValueMap.get(key).getValue();
			}
			return randomValueMap.get(key).getValueName();
		}
		return value;
	}
	/**
	 * 取得简单值
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-1 下午5:37:53
	 * @version 1.0
	 * @exception 
	 * @param value
	 * @return
	 */
	private String getRandomValue(String value){
		if(!value.contains(",")){
			return value;
		}
		String key = String.valueOf(value.hashCode());
		if(!randomValue.containsKey(key)){
			//如果值是以逗号分隔 则随机取其中一个数据
			if(StringUtils.isNotBlank(value)){
				String[] tempValues = value.split(",");
				if(tempValues.length > 1){
					value = tempValues[(int)(Math.random()*(tempValues.length))+0];
				}else{
					value = tempValues[0];
				}
				tempValues = null;
			}
			randomValue.put(key, value);
		}
		if(!randomValue.isEmpty()){
			return randomValue.get(key);
		}
		return value;
	}
	
	
	private int createPrimaryKey(String tableName,CrawlerRuleBean rule,ContentBean contentBean,int ky){
		PreparedStatement pstm = null; 
		Connection conn = null;
		ResultSet rs = null;
		String dataBaseId = rule.getRuleDataBaseBean().getDataBaseId();
		Map<String, ColumnBean> columnMap = rule.getRuleDataBaseBean().getHasSelectedValueMap().get(tableName);
		int keyValue = -1;
		int pstmKy = 1;
		try{
			conn = this.connectionManager.getConnection(dataBaseId);
			pstm = conn.prepareStatement(this.populateSql(tableName,columnMap),Statement.RETURN_GENERATED_KEYS);
			ColumnBean columnBean = null;
			for(Iterator<String> it = columnMap.keySet().iterator();it.hasNext();){
				columnBean = columnMap.get(it.next());
				setValueByType(contentBean,rule,pstm,columnBean.getColumnType(),pstmKy++,columnBean.getColumnValue(),columnBean.getColumnValueType(),ky,"");
			}
			pstm.execute();
			rs = pstm.getGeneratedKeys();
			if (rs.next()) {
			    keyValue = rs.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			this.connectionManager.freeConnection(dataBaseId, conn);
			try {
				if(null != pstm){
					pstm.close();
				}
				if(null != rs){
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return keyValue;
	}
	
	private void insertValue(String tableName,CrawlerRuleBean rule,ContentBean contentBean,int ky){
		PreparedStatement pstm = null; 
		Connection conn = null;
		String dataBaseId = rule.getRuleDataBaseBean().getDataBaseId();
		Map<String, ColumnBean> columnMap = rule.getRuleDataBaseBean().getHasSelectedValueMap().get(tableName);
		int pstmKy = 1;
		try{
			conn = this.connectionManager.getConnection(dataBaseId);
			pstm = conn.prepareStatement(this.populateSql(tableName,columnMap),Statement.RETURN_GENERATED_KEYS);
			//是否有一对多，保存扩展字段的情况
			List<Map<String, ColumnBean>> newColumnMapLis = getNewColumnBeanMapList(columnMap);
			ColumnBean columnBean = null;
			if(newColumnMapLis.isEmpty()){
				for(Iterator<String> it = columnMap.keySet().iterator();it.hasNext();){
					columnBean = columnMap.get(it.next());
					setValueByType(contentBean,rule,pstm,columnBean.getColumnType(),pstmKy++,columnBean.getColumnValue(),columnBean.getColumnValueType(),ky,"");
				}
				pstm.execute();
			}else{
				for(Map<String, ColumnBean> newColumnMap : newColumnMapLis){
					pstmKy = 1;
					for(Iterator<String> it = newColumnMap.keySet().iterator();it.hasNext();){
						columnBean = newColumnMap.get(it.next());
						setValueByType(contentBean,rule,pstm,columnBean.getColumnType(),pstmKy++,columnBean.getColumnValue(),columnBean.getColumnValueType(),ky,"");
					}
					pstm.execute();
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			this.connectionManager.freeConnection(dataBaseId, conn);
			try {
				if(null != pstm){
					pstm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 取得新的字段MAP对象集合
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-26 下午10:06:31
	 * @version 1.0
	 * @exception 
	 * @param columnMap
	 * @return
	 */
	private List<Map<String, ColumnBean>> getNewColumnBeanMapList(Map<String, ColumnBean> columnMap){
		//新字段对象集合
		List<Map<String, ColumnBean>> newColumnMapLis = new ArrayList<Map<String, ColumnBean>>();
		ColumnBean columnBean = null;
		//字段值中最大长度
		int maxLen = 0;
		//字段值中最大长度时 字段名称
		String maxColumnName = "";
		//单值MAP对象
		Map<String, ColumnBean> columnSingleValueMap = new HashMap<String, ColumnBean>();
		//多值MAP对象
		Map<String,ColumnBean> columnMultValueMap = new HashMap<String,ColumnBean>();
		//多值MAP与字段名称对照
		Map<String,String[]> columnMultValuesMap = new HashMap<String,String[]>();
		for(String key : columnMap.keySet()){
			columnBean = columnMap.get(key);
			if((GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_KEY.equals(columnBean.getColumnValueType()) || GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_VALUE.equals(columnBean.getColumnValueType())) && columnBean.getColumnValue().contains(",")){
				columnMultValueMap.put(key, columnBean);
				columnMultValuesMap.put(key, columnBean.getColumnValue().split(","));
				if(maxLen < (columnBean.getColumnValue().split(",")).length){
					maxLen = (columnBean.getColumnValue().split(",")).length;
					maxColumnName = columnBean.getColumnName();
				}
			}else{
				columnSingleValueMap.put(key, columnBean);
			}
		}
		//如果有多值
		if(!columnMultValueMap.isEmpty()){
			Map<String, ColumnBean> newClumnMap = null;
			ColumnBean newColumnBean = null;
			ColumnBean maxColumnBean = columnMultValueMap.get(maxColumnName);
			columnMultValueMap.remove(maxColumnName);
			//则按最长字段值 来重新组合
			for(int i = 0 ; i < maxLen ;i++){
				newClumnMap = new HashMap<String, ColumnBean>();
				newClumnMap.putAll(columnSingleValueMap);
				//设置最大字段值
				newColumnBean = new ColumnBean();
				BeanUtils.copyProperties(maxColumnBean, newColumnBean);
				newColumnBean.setColumnValue(columnMultValuesMap.get(maxColumnName)[i]);
				newClumnMap.put(maxColumnName, newColumnBean);
				//设置其他多值字段
				for(String key : columnMultValueMap.keySet()){
					newColumnBean = new ColumnBean();
					BeanUtils.copyProperties(columnMap.get(key), newColumnBean);
					if(null == columnMultValuesMap.get(key) || null == columnMultValuesMap.get(key)[i]){
						newColumnBean.setColumnValue("");
					}else{
						newColumnBean.setColumnValue(columnMultValuesMap.get(key)[i]);
					}
					newClumnMap.put(key, newColumnBean);
				}
				newColumnMapLis.add(newClumnMap);
			}
		}
		return newColumnMapLis;
	}
	
	private Date getRandomDate(CrawlerRuleBean rule){
		Date viewDate = new Date();
		if(Boolean.valueOf(rule.getRuleBaseBean().getRandomDateFlag())){
			String format = StringUtils.isNotBlank(rule.getRuleBaseBean().getDateFormat()) ? rule.getRuleBaseBean().getDateFormat() : "yyyy-MM-dd";
			SimpleDateFormat dt = new SimpleDateFormat(format);
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			String statrDate = StringUtils.isNotBlank(rule.getRuleBaseBean().getStartRandomDate()) ? rule.getRuleBaseBean().getStartRandomDate() : Constant.DEFAULT_START_DATE;
			String endDate = StringUtils.isNotBlank(rule.getRuleBaseBean().getEndRandomDate()) ? rule.getRuleBaseBean().getEndRandomDate() : dft.format(new java.util.Date());
			viewDate = DateFormatUtils.randomDate(statrDate, endDate);
			String tempDate = dt.format(viewDate);
			viewDate = DateUtil.strToDate(tempDate,format);
		}
		return viewDate;
	}
	
	public static void main(String[] args){
		String value = "1,2";
		if(StringUtils.isNotBlank(value) && value.contains(",")){
			String[] tempValues = value.split(",");
			if(tempValues.length > 1){
				value = tempValues[(int)(Math.random()*(tempValues.length))+0];
			}else{
				value = tempValues[0];
			}
			tempValues = null;
		}
		System.out.println(value);
	}


}
