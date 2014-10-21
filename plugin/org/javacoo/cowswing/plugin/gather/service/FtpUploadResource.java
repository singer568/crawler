/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.service.ICrawlerService;
import org.javacoo.cowswing.base.utils.FtpUtil;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentResourceBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentResourceCriteria;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleCriteria;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskCriteria;

/**
 * 资源上传
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-24 下午10:56:03
 * @version 1.0
 */
public class FtpUploadResource extends AbstractThreadService{
	/**FTP工具类*/
	private FtpUtil ftpUtil;
	/**规则服务类*/
	private ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService;
	/**资源服务类*/
	private ICrawlerService<CrawlerContentResourceBean,CrawlerContentResourceCriteria> crawlerContentResourceService;
	/**规则ID*/
	private Integer ruleId;
	public FtpUploadResource(Integer ruleId,ICrawlerService<CrawlerContentResourceBean,CrawlerContentResourceCriteria> crawlerContentResourceService,ICrawlerService<CrawlerTaskBean,CrawlerTaskCriteria> crawlerTaskService,ICrawlerService<CrawlerRuleBean,CrawlerRuleCriteria> crawlerRuleService){
		super(crawlerTaskService);
		this.ruleId = ruleId;
		this.crawlerRuleService = crawlerRuleService;
		this.crawlerContentResourceService = crawlerContentResourceService;
		this.crawlerTaskService = crawlerTaskService;
	}
	/**
	 * Ftp上传
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午9:36:05
	 * @version 1.0
	 * @exception
	 */
	protected void doRun(){
		CrawlerRuleBean rule = new CrawlerRuleBean();
		rule.setRuleId(ruleId);
		rule = this.crawlerRuleService.get(rule, GatherConstant.SQLMAP_ID_GET_CRAWLER_RULE);
		if(null != rule.getCrawlerFtpConfigBean() && Constant.YES.equals(rule.getCrawlerFtpConfigBean().getUseFtpFlag())){
			ftpUtil = new FtpUtil(rule.getCrawlerFtpConfigBean().getFtpUrl(),Integer.valueOf(rule.getCrawlerFtpConfigBean().getFtpPort()),rule.getCrawlerFtpConfigBean().getFtpUserName(),rule.getCrawlerFtpConfigBean().getFtpPassword());
			CrawlerContentResourceCriteria criteria = new CrawlerContentResourceCriteria();
			criteria.setRuleId(ruleId);
			criteria.setHasUpload(Constant.NO);
			criteria.setIsLocal(Constant.YES);
			List<CrawlerContentResourceBean> resultList = this.crawlerContentResourceService.getList(criteria,GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_RESOURCE);
			if(CollectionUtils.isNotEmpty(resultList)){
				//插入上传任务
				insertTask(ruleId,resultList.size(),GatherConstant.TASK_TYPE_2,CowSwingEventType.FtpStartEvent);
				//休眠，避免锁表
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Boolean login = ftpUtil.connectServer();
				if(!login){
					log.info("FTP登陆失败");
					return;
				}
				String filePath = "";
				String ftpPath = rule.getCrawlerFtpConfigBean().getFtpDirPath();
				String dirPath = "";
				String ftpFileAllPath = "";
				long result = -1;
				String desc = "";
				for(CrawlerContentResourceBean crawlerContentResourceBean : resultList){
					filePath = Constant.SYSTEM_ROOT_PATH+"/"+crawlerContentResourceBean.getPath();
					dirPath = ftpPath + "/" +FilenameUtils.getFullPath(crawlerContentResourceBean.getPath());
					ftpFileAllPath = ftpPath + "/" + crawlerContentResourceBean.getPath();
					log.info("文件路径："+filePath);
					log.info("FTP目录路径："+dirPath);
					log.info("上传文件FTP全路径："+ftpFileAllPath);
					log.info("检查目录："+!ftpUtil.isDirExist(dirPath));
					if(!ftpUtil.isDirExist(dirPath)){
						log.info("创建目录："+dirPath);
						ftpUtil.createDir(dirPath);
					}
					try {
						result = ftpUtil.uploadFile(filePath, ftpFileAllPath);
						//result = 11;
						if(result >= 0){
							desc = "上传文件："+FilenameUtils.getName(crawlerContentResourceBean.getPath())+"成功,大小："+result+" KB";
							log.info(desc);
							//修改状态
							updateResourceState(crawlerContentResourceBean);
						}else{
							desc = "上传文件："+FilenameUtils.getName(crawlerContentResourceBean.getPath())+"失败";
							log.info(desc);
						}
					} catch (Exception e) {
						desc = "上传文件："+FilenameUtils.getName(crawlerContentResourceBean.getPath())+"发生异常,请查看日志";
						log.info(desc);
						e.printStackTrace();
					}
				    updateTask(crawlerContentResourceBean.getRuleId(),desc,GatherConstant.TASK_TYPE_2,CowSwingEventType.FtpStatusChangeEvent);
				}
				deleteTask(this.ruleId,GatherConstant.TASK_TYPE_2,LanguageLoader.getString("CrawlerMainFrame.CrawlFtpComplateTask"),CowSwingEventType.FtpFinishedEvent);
				ftpUtil.closeServer();
			}
		}
	}

	/**
	 * 修改资源状态
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-25 上午9:55:55
	 * @version 1.0
	 * @exception 
	 * @param crawlerContentResourceBean
	 */
	private void updateResourceState(CrawlerContentResourceBean crawlerContentResourceBean){
		CrawlerContentResourceBean updateBean = new CrawlerContentResourceBean();
		updateBean.setResourceId(crawlerContentResourceBean.getResourceId());
		updateBean.setHasUpload(Constant.YES);
		updateBean.setIsLocal("");
		this.crawlerContentResourceService.update(updateBean, GatherConstant.SQLMAP_ID_UPDATE_CRAWLER_CONTENT_RESOURCE);
	}

	public static void main(String[] args){
		String path = "download/201304/24/20120410170236685.jpg";
		System.out.println(FilenameUtils.getFullPath(path));
	}

}
