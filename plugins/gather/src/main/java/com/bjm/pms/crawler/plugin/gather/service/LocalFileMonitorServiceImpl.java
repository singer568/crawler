/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.core.data.ContentBean;
import com.bjm.pms.crawler.plugin.gather.constant.GatherConstant;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleCriteria;
import com.bjm.pms.crawler.plugin.gather.service.local.LocalDocParser;
import com.bjm.pms.crawler.plugin.gather.service.local.LocalDocParserFactory;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.core.init.InitService;

/**
 * 本地文件监听
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-23 上午11:19:50
 * @version 1.0
 */
@Component("localFileMonitorService")
public class LocalFileMonitorServiceImpl implements InitService{

	protected Logger logger = Logger.getLogger(this.getClass());
	/**本地文件解析工厂*/
	@Resource(name="localDocParserFactory")
	private LocalDocParserFactory localDocParserFactory;
	/**
	 * 采集规则服务类
	 */
	@Resource(name="crawlerRuleService")
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	/**
	 * 本地采集持久化
	 */
	@Resource(name="localPersistent")
	private LocalPersistentImpl localPersistent;
	/** 目录监听MAP */
	private Map<String,Integer> listeners = new HashMap<String,Integer>();
	/** 监听对象ID采集规则IDMAP */
	private Map<Integer,Integer> watchIDRuleIdMap= new HashMap<Integer,Integer>();
	/** 目录监听文件类型集合 MAP*/
	private Map<String,String> dirTypesMap = new HashMap<String,String>();
	/** 监听事件*/
	private int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED | JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;  
	/** 是否监听子目录 */
	private boolean watchSubtree = true;  
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.init.InitService#init()
	 */
	@Override
	public void init() {
		startListener();
	}
	/**
	 * 启动监听
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-23 上午11:31:05
	 * @version 1.0
	 * @exception
	 */
	private void startListener(){
		CrawlerRuleCriteria criteria = new CrawlerRuleCriteria();
		criteria.setRuleType(Constant.YES);
		criteria.setStatus(Constant.TASK_STATUS_RUN);
		List<CrawlerRuleBean> result = crawlerRuleService.getList(criteria,GatherConstant.SQLMAP_ID_LIST_CRAWLER_RULE);
		if(CollectionUtils.isNotEmpty(result)){
			for(CrawlerRuleBean ruleBean : result){
				if(StringUtils.isNotBlank(ruleBean.getRuleBaseBean().getUrlRepairUrl())){
					addListener(ruleBean.getRuleBaseBean().getUrlRepairUrl(),ruleBean.getRuleId(),Boolean.valueOf(ruleBean.getRuleBaseBean().getSaveResourceFlag()),ruleBean.getRuleBaseBean().getDateFormat());
				}
			}
		}
	}
	/**
	 * 添加监听目录
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-23 上午11:31:12
	 * @version 1.0
	 * @exception 
	 * @param dir
	 * @param ruleId
	 * @param docTypes
	 */
	public void addListener(String dir,Integer ruleId,boolean watchSubtree,String docTypes){
		String dirHashCode = String.valueOf(dir.hashCode());
		if(!listeners.containsKey(dirHashCode)){
			try {
				int watchID = JNotify.addWatch(dir, mask, watchSubtree, new Listener());
				listeners.put(dirHashCode, watchID);
				watchIDRuleIdMap.put(watchID, ruleId);
				dirTypesMap.put(dirHashCode, docTypes);
				logger.info("添加监听目录："+dir+",watchID="+watchID);
			} catch (JNotifyException e) {
				e.printStackTrace();
			}  
		}
	}
	/**
	 * 删除监听目录
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-23 上午11:31:20
	 * @version 1.0
	 * @exception 
	 * @param dir
	 */
	public void removeListener(String dir){
		String dirHashCode = String.valueOf(dir.hashCode());
		if(listeners.containsKey(dirHashCode)){
			try {
				boolean res = JNotify.removeWatch(listeners.get(dirHashCode));
				if (!res) {  
					logger.error("删除目录："+dir+"监听失败!");
			    }  
				listeners.remove(dirHashCode);
				watchIDRuleIdMap.remove(listeners.get(dirHashCode));
				dirTypesMap.remove(dirHashCode);
				logger.info("删除监听目录："+dir);
			} catch (JNotifyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
	/**
	 * 是否包含此类型
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-24 上午10:36:39
	 * @version 1.0
	 * @exception 
	 * @param docType
	 * @return
	 */
	private boolean containsExtension(String docType){
		if(!dirTypesMap.isEmpty()){
			for(String key : dirTypesMap.keySet()){
				if(dirTypesMap.get(key).contains(docType)){
					return true;
				}
			}
		}
		return false;
	}
	/** 
	 * 目录监听类
	 * <p>说明:</p>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-7-22 下午6:14:14
	 * @version 1.0
	 */
	class Listener implements JNotifyListener {  
		
	    public void fileRenamed(int wd, String rootPath, String oldName,  
	            String newName) {  
	    	logger.info("重命名文件： " + rootPath + " : " + oldName + " -> " + newName);  
	    }  

	    public void fileModified(int wd, String rootPath, String name) {  
	    	logger.info("modified " + rootPath + " : " +"hashcode:"+rootPath.hashCode()+",path="+ name);  
	    }  

	    public void fileDeleted(int wd, String rootPath, String name) {  
	    	logger.info("删除文件： " + rootPath + " : " + name);
	    }  

	    public void fileCreated(int wd, String rootPath, String name) {
	    	try {
				String path = rootPath+Constant.SYSTEM_SEPARATOR+name;
				if(containsExtension(FilenameUtils.getExtension(name).toLowerCase()) && isOkFile(path,1)){
					logger.info("监控ID："+wd+"，发现新文件 "+rootPath + " : " + name+",1秒钟后解析,文件类型："+FilenameUtils.getExtension(name));  
					Thread.sleep(1000);
					LocalDocParser localDocParser = localDocParserFactory.getLocalDocParser(FilenameUtils.getExtension(name).toLowerCase());
					if(null != localDocParser){
						localDocParser.parser(path);
						if(watchIDRuleIdMap.containsKey(wd) && 0 != watchIDRuleIdMap.get(wd)){
							ContentBean contentBean = new ContentBean();
							contentBean.setContent(localDocParser.getContent());
							contentBean.setTitle(localDocParser.getTitle());
							contentBean.setOrginHtml(path);
							if(null != localDocParser.getValueMap() && !localDocParser.getValueMap().isEmpty()){
								contentBean.getFieldValueMap().putAll(localDocParser.getValueMap());
							}
							localPersistent.save(contentBean, watchIDRuleIdMap.get(wd));
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }  
	    /**
	     * 检查文件
	     * <p>方法说明:</>
	     * <li>隐藏文件，回收站文件，大小maxSize的文件都不做解析</li>
	     * @author DuanYong
	     * @since 2014-9-24 下午5:56:10
	     * @version 1.0
	     * @exception 
	     * @param path
	     * @return
	     */
	    private boolean isOkFile(String path,int maxSize){
	    	File file = new File(path);
	    	//如果是隐藏文件，则不解析
	    	if(file.isHidden()){
	    		logger.error("是隐藏文件，不做解析 "); 
	    		return false;
	    	}
	    	//如果是回收站文件，则不解析
	    	if(path.contains("RECYCLER")){
	    		logger.error("是回收站文件，不做解析 "); 
	    		return false;
	    	}
	    	long fileSize = file.length();
	    	logger.info("文件大小约： "+fileSize/(1024 * 1024 * 1.0) + "M"); 
	    	if(fileSize < (maxSize * 1024 * 1024)){
	    		file = null;
	    		return true;
	    	}
	    	logger.error("文件： "+path+",大小约："+ fileSize / (1024 * 1024 * 1.0) + "M,大于"+maxSize+"M,不做采集"); 
	    	return false;
	    }
	}  

}
