/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service.cache;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.persist.PaginationSupport;
import com.bjm.pms.crawler.persist.util.DBConnectionManager;
import com.bjm.pms.crawler.plugin.gather.constant.SystemConstant;
import com.bjm.pms.crawler.plugin.gather.service.beans.ColumnBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerDataBaseBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerDataBaseCriteria;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.core.cache.support.AbstractCowSwingCache;
import com.bjm.pms.crawler.view.core.cache.support.CacheKeyConstant;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.core.event.CowSwingObserver;

/**
 * 数据库缓存信息
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-21 上午10:27:40
 * @version 1.0
 */
@Component("dataBaseInfoCache")
public class DataBaseInfoCache extends AbstractCowSwingCache<Map<Integer,Map<String,List<ColumnBean>>>>{

	
	/**
	 * 数据库服务类
	 */
	@Resource(name="crawlerDataBaseService")
	private ICrawlerService<CrawlerDataBaseBean,CrawlerDataBaseCriteria> crawlerDataBaseService;
	/**数据库连接管理类*/
	private DBConnectionManager connectionManager = DBConnectionManager.getInstance();
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.support.AbstractCrawlerCache#loadDataToCache()
	 */
	@Override
	protected Map<Integer,Map<String,List<ColumnBean>>> loadDataToCache() {
		Map<Integer,Map<String,List<ColumnBean>>> dataBaseMap = new HashMap<Integer,Map<String,List<ColumnBean>>>();
		CrawlerDataBaseCriteria criteria = new CrawlerDataBaseCriteria();
		criteria.setStartIndex(0);
		criteria.setPageSize(100);
		PaginationSupport<CrawlerDataBaseBean> result = crawlerDataBaseService.getPaginatedList(criteria,SystemConstant.SQLMAP_ID_LIST_CRAWLER_DATABASE);
		if(null != result && !CollectionUtils.isEmpty(result.getData())){
			Integer dataBaseId = 0;
		    Map<String,List<ColumnBean>> tableNameMap = null;
		    List<ColumnBean> columnList = null;
		    for(CrawlerDataBaseBean crawlerDataBaseBean : result.getData()){
		    	dataBaseId = crawlerDataBaseBean.getDataBaseId();
				logger.info("开始加载数据库信息至缓存："+crawlerDataBaseBean.getDescription());
		    	tableNameMap = getTableName(dataBaseId,tableNameMap,columnList);
		    	if(null != tableNameMap && !tableNameMap.isEmpty()){
		    		dataBaseMap.put(dataBaseId, tableNameMap);
		    	}
			}
		}
		return dataBaseMap;
	}
	/**
	 * 取得表名称
	 * <p>方法说明:</>
	 * <li>根据数据库ID 取得表名称和字段名称</li>
	 * @author DuanYong
	 * @since 2013-4-21 下午7:36:04
	 * @version 1.0
	 * @exception 
	 * @param dataBaseId
	 * @param tableNameMap
	 * @param columnList
	 * @return
	 */
	private Map<String,List<ColumnBean>> getTableName(Integer dataBaseId,Map<String,List<ColumnBean>> tableNameMap,List<ColumnBean> columnList){
		Connection conn = null;
		try{
			conn = connectionManager.getConnection(dataBaseId+"");
			if (null != conn) {
				tableNameMap = new HashMap<String,List<ColumnBean>>();
				DatabaseMetaData dbmd = conn.getMetaData();
				ResultSet tSet = dbmd.getTables(null, "%", "%", new String[]{"TABLE","VIEW"});
				String tableName = "";
				while (tSet.next()) {
					tableName = tSet.getString("TABLE_NAME");
					logger.info("开始加载数据库表："+tableName);
					columnList = getColumnName(conn,tableName,columnList); 
					if(!CollectionUtils.isEmpty(columnList)){
						tableNameMap.put(tableName, columnList);
					}
					StringBuilder info = new StringBuilder();
					info.append("完成加载数据库："+dataBaseId+",表："+tableName);
					CowSwingObserver.getInstance().notifyEvents(new CowSwingEvent(this,CowSwingEventType.CacheDataChangeEvent,info.toString()));
			    }
				tSet.close();
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			if(null != conn){
				connectionManager.freeConnection(dataBaseId+"", conn);
			}
		}
		return tableNameMap;
	}
	/**
	 * 取得字段名称集合
	 * <p>方法说明:</>
	 * <li>根据表名称取得列名称集合</li>
	 * @author DuanYong
	 * @since 2013-4-21 下午7:37:00
	 * @version 1.0
	 * @exception 
	 * @param dataBaseId
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	private List<ColumnBean> getColumnName(Connection conn,String tableName,List<ColumnBean> columnList){
		try{
			if(null != conn && null != tableName && StringUtils.isNotBlank(tableName)){
				String sql = "select * from " + tableName;
                ResultSet rsSet = conn.createStatement().executeQuery(sql);
                ResultSetMetaData rsData = rsSet.getMetaData();
                columnList = new ArrayList<ColumnBean>();
                for (int i = 1; i <= rsData.getColumnCount(); i++) {
 					logger.info("开始加载数据库表列："+rsData.getColumnName(i));
                 	columnList.add(new ColumnBean(rsData.getColumnName(i),rsData.getColumnTypeName(i),rsData.isNullable(i)));
                 }
                rsSet.close();
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return columnList;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.cache.ICrawlerCache#getCacheKey()
	 */
	@Override
	public String getCacheKey() {
		return CacheKeyConstant.CACHE_KEY_DATA_BASE;
	}
	
}
