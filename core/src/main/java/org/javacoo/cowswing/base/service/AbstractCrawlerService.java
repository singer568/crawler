package org.javacoo.cowswing.base.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.javacoo.cowswing.base.service.beans.CrawlerBaseBean;
import org.javacoo.cowswing.base.utils.JsonUtils;
import org.javacoo.cowswing.core.event.CowSwingObserver;
import org.javacoo.persistence.PaginationSupport;
import org.javacoo.persistence.PersistService;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

/**
 * 丑牛服务公共接口抽象实现类
 *  <p>说明：</p>
 *  所有服务都基础此抽象类,实现其抽象方法
 *@author DuanYong
 *@since 2012-12-1下午8:52:06
 *@param <T> 值对象类型
 *@param <E> 持久对象类型
 *@param <Q> 查询对象类型
 *@version 1.0
 */
public abstract class AbstractCrawlerService<T extends CrawlerBaseBean,E,Q> implements ICrawlerService<T,Q>{
	protected Logger logger = Logger.getLogger(this.getClass());
	@Resource(name="persistService")
	private PersistService persistService;
	public final int insert(T t,String sqlMapId){
		int result = 0;
		try {
			logger.info("插入数据："+t);
			result = doInsert(t,sqlMapId);
			CowSwingObserver.getInstance().notifyEvents(t.getCowSwingEvent());
		} catch (IllegalAccessException e) {
			logger.error("无法设置值:");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error("调用目标方法失败:");
			e.printStackTrace();
		}
		return result;
	}
	public final int update(T t,String sqlMapId){
		int result = 0;
		try {
			logger.info("修改数据："+t);
			result = doUpdate(t,sqlMapId);
			CowSwingObserver.getInstance().notifyEvents(t.getCowSwingEvent());
		} catch (IllegalAccessException e) {
			logger.error("无法设置值:");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error("调用目标方法失败:");
			e.printStackTrace();
		} 
		return result;
	}
	public final int delete(T t,String sqlMapId){
		int result = 0;
		try {
			logger.info("删除数据："+t);
			result = doDelete(t,sqlMapId);
			CowSwingObserver.getInstance().notifyEvents(t.getCowSwingEvent());
		} catch (IllegalAccessException e) {
			logger.error("无法设置值:");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error("调用目标方法失败:");
			e.printStackTrace();
		}
		return result;
	}
	public final T get(T t,String sqlMapId){
		logger.info("查询数据："+t);
		try {
			E e = doGet(t,sqlMapId);
			if(null != e){
				t = translateBean(e);
			}
			return t;
		} catch (IllegalAccessException e) {
			logger.error("无法设置值:");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error("调用目标方法失败:");
			e.printStackTrace();
		}
		return t;
	}
	public final List<T> getList(Q q,String sqlMapId){
		logger.info("查询集合数据："+q);
		try {
			List<E> eList = doGetList(q,sqlMapId);
			List<T> resultT = new ArrayList<T>();
			if(CollectionUtils.isNotEmpty(eList)){
				for(E tempE : eList){
					resultT.add(translateBean(tempE));
				}
			}
			return resultT;
		} catch (IllegalAccessException e) {
			logger.error("无法设置值:");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error("调用目标方法失败:");
			e.printStackTrace();
		}
		return new ArrayList<T>();
	}
	public final PaginationSupport<T> getPaginatedList(Q q,String sqlMapId){
		logger.info("查询集合数据："+q);
		List<T> resultT = new ArrayList<T>();
		PaginationSupport<T> paginationResult = new PaginationSupport<T>(resultT,0,1,20);
		try {
			PaginationSupport<E> eList = doGetPaginatedList(q,sqlMapId);
			if(CollectionUtils.isNotEmpty(eList.getData())){
				for(E tempE : eList.getData()){
					resultT.add(translateBean(tempE));
				}
			}
			paginationResult.setTotalCount(eList.getTotalCount());
			paginationResult.setFirstPage(eList.isFirstPage());
			paginationResult.setHasNextPage(eList.hasNextPage());
			paginationResult.setLastPage(eList.isLastPage());
			paginationResult.setNavigatePageNumbers(eList.getNavigatePageNumbers());
			paginationResult.setCurrPageNumber(eList.getCurrPageNumber());
			paginationResult.setPageSize(eList.getPageSize());
			paginationResult.setPageTotalCount(eList.getPageTotalCount());
			paginationResult.setHasPreviousPage(eList.hasPreviousPage());
		} catch (IllegalAccessException e) {
			logger.error("无法设置值:");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error("调用目标方法失败:");
			e.printStackTrace();
		}
		return paginationResult;
	}
	/**
	 * 插入数据
	 * <p>方法说明:</p>
	 * 由子类实现
	 * @auther DuanYong
	 * @since 2012-12-1 下午9:01:58
	 * @param t
	 * @param sqlMapId
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return int
	 */
	protected abstract int doInsert(T t,String sqlMapId) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 修改数据
	 * <p>方法说明:</p>
	 * 由子类实现
	 * @auther DuanYong
	 * @since 2012-12-1 下午9:02:29
	 * @param t
	 * @param sqlMapId
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return int
	 */
	protected abstract int doUpdate(T t,String sqlMapId) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 删除数据
	 * <p>方法说明:</p>
	 * 由子类实现
	 * @auther DuanYong
	 * @since 2012-12-1 下午9:02:51
	 * @param t
	 * @param sqlMapId
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return int
	 */
	protected abstract int doDelete(T t,String sqlMapId) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 取得数据
	 * <p>方法说明:</p>
	 * 由子类实现
	 * @auther DuanYong
	 * @since 2012-12-1 下午9:03:07
	 * @param t
	 * @param sqlMapId
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return E
	 */
	protected abstract E doGet(T t,String sqlMapId) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 取得集合数据
	 * <p>方法说明:</p>
	 * 由子类实现
	 * @auther DuanYong
	 * @since 2012-12-1 下午9:03:23
	 * @param q
	 * @param sqlMapId
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return List<E>
	 */
	protected abstract List<E> doGetList(Q q,String sqlMapId) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 取得分页集合数据
	 * <p>方法说明:</p>
	 * 由子类实现
	 * @auther DuanYong
	 * @since 2012-12-1 下午9:03:23
	 * @param q
	 * @param sqlMapId
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return PaginationSupport<E>
	 */
	protected abstract PaginationSupport<E> doGetPaginatedList(Q q,String sqlMapId) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 将持久对象转换为值对象
	 * <p>方法说明:</p>
	 * 由子类实现,完成日期,标准数据,字符串格式化等操作
	 * @auther DuanYong
	 * @since 2012-12-1 下午8:35:37
	 * @param e 持久对象
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return T 值对象
	 */
	protected abstract T translateBean(E e) throws IllegalAccessException, InvocationTargetException;
	
	
	/**
	 * 格式化任务信息为json格式字符串
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-10-17 上午9:09:24
	 * @param obj
	 * @return String
	 */
	protected String formatObjectToJsonString(Object obj){
		return JsonUtils.formatObjectToJsonString(obj);
    }
	/**
	 * 将JSON字符串转换为指定对象
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-1 下午8:59:04
	 * @param jsonString
	 * @param beanClass
	 * @return
	 * @return Object
	 */
	protected Object formatStringToObject(String jsonString,Class<?> beanClass){
		return JsonUtils.formatStringToObject(jsonString, beanClass);
	}
	public PersistService getPersistService() {
		return persistService;
	}
	public void setPersistService(PersistService persistService) {
		this.persistService = persistService;
	}
	
}
