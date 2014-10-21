/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.core.runcycle.ICowSwingRunCycle;
import org.springframework.context.ApplicationContext;

/**
 * 丑牛上下文数据
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-1 下午12:39:34
 * @version 1.0
 */
public final class CowSwingContextData {
	 /** 主线程的线程锁，用于防止主线程关闭 */
    private Object cowSwingTheadLock = new Object();
	/**CowSwing运行周期*/
	private ICowSwingRunCycle cowSwingRunCycle;
    private static CowSwingContextData instance = new CowSwingContextData();
    private Thread swtThread;
    /** spring加载上下文 */
    private ApplicationContext applicationContext;
    /** 插件集合*/
  	private static Map<String, Map<String, String>> plugins;
  	/** 上下文对象*/
  	private static Map<String, Object> contextData = new HashMap<String, Object>();
  	
  	
  	public Map<String, Map<String, String>> getPlugins() {
		return plugins;
	}
	public void setPlugins(Map<String, Map<String, String>> plugins) {
		CowSwingContextData.plugins = plugins;
	}
    public static CowSwingContextData getInstance() {
		return instance;
	}
	private CowSwingContextData(){}
	
	/**
	 * @return the cowSwingRunCycle
	 */
	public ICowSwingRunCycle getCowSwingRunCycle() {
		return cowSwingRunCycle;
	}
	/**
	 * @param cowSwingRunCycle the cowSwingRunCycle to set
	 */
	public void setCowSwingRunCycle(ICowSwingRunCycle cowSwingRunCycle) {
		this.cowSwingRunCycle = cowSwingRunCycle;
	}
	/**
	 * @return the cowSwingTheadLock
	 */
	public Object getCowSwingTheadLock() {
		return cowSwingTheadLock;
	}
	/**
	 * @param cowSwingTheadLock the cowSwingTheadLock to set
	 */
	public void setCowSwingTheadLock(Object cowSwingTheadLock) {
		this.cowSwingTheadLock = cowSwingTheadLock;
	}
	/**
	 * @return the swtThread
	 */
	public Thread getSwtThread() {
		return swtThread;
	}
	/**
	 * @param swtThread the swtThread to set
	 */
	public void setSwtThread(Thread swtThread) {
		this.swtThread = swtThread;
	}
	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	/**
	 * @param applicationContext the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	/**
	 * @return the contextData
	 */
	public Object getContextDataByKey(String key) {
		if(contextData.containsKey(key)){
			return contextData.get(key);
		}
		return null;
	}
	/**
	 * @param contextData the contextData to set
	 */
	public void setContextData(String key,Object obj) {
		if(StringUtils.isNotBlank(key) && null != obj){
			contextData.put(key, obj);
		}
	}
	
}
