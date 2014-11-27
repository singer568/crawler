package com.bjm.pms.crawler.core.processor.extractor;


import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;

import com.bjm.pms.crawler.core.data.Task;
import com.bjm.pms.crawler.core.data.queue.UrlQueue;
import com.bjm.pms.crawler.core.data.uri.CrawlLinkURI;
import com.bjm.pms.crawler.core.util.HttpClientHelper;

/**
 * 任务处理器接口-抽取分页内容实现类
 * <li>抽取链 ： 提取分页内容 </li>
 * @author javacoo
 * @since 2012-02-09
 */
public class ExtractorPaginationProcessor extends Extractor{
	/**存放SimpleUrlQueue的ThreadLocal对象*/ 
	private static ThreadLocal<UrlQueue> pageUrlQueueThreadLocal = new ThreadLocal<UrlQueue>(); 
	
	public ExtractorPaginationProcessor() {
		super();
		setDefaultNextProcessor(new ExtractorCommentProcessor());
	}

	@Override
	protected void extract(Task task) {
		initProcessor(task);
		processorHTML(task);
	}
	/**
	 * 抽取第一页内容，得到内容分页
	 */
	private void initProcessor(Task task){
		log.info("=========抽取第一页内容,得到内容分页连接,加入到内容分页队列中=========");
		//第一页已经访问
		getUrlQueue().addAllURI(task.getCrawlURI());
		populatePaginationList(task,task.getContentBean().getOrginHtml());
	}
	/**
	 * 抽取原始HTML中分页链接,加入到内容分页队列中
	 * @param task 当前任务对象
	 * @param orginHtml 原始HTML内容
	 */
	private void populatePaginationList(Task task,String orginHtml) {
		List<CrawlLinkURI> urlList = task.getController().getHtmlParserWrapper().getPaginationList(orginHtml,task.getCrawlURI());
		if(!CollectionUtils.isEmpty(urlList)){
			for(CrawlLinkURI url : urlList){
				getUrlQueue().addUnExecTask(url);
			}
		}
	}
	/**
	 * 处理任务
	 * @param task
	 */
	private void processorHTML(Task task){
		log.info("=========处理任务开始=========");
		try{
			String tempOrginHtml = "";
			CrawlLinkURI crawlURI = null;
			while(!getUrlQueue().isEmpty() && !Thread.interrupted()){
				crawlURI = getUrlQueue().unExecTaskDeQueue();
				tempOrginHtml = fetchHTML(task,crawlURI);
				if(StringUtils.isNotBlank(tempOrginHtml)){
					//提取内容分页中的链接,并加入到队列中
					populatePaginationList(task,tempOrginHtml);
					savePaginationContent(task,tempOrginHtml);
					getUrlQueue().addExecTask(crawlURI);
				}
			}
		}finally{
			log.info("=========处理任务结束=========");
			getUrlQueue().clear();
			pageUrlQueueThreadLocal.set(null);
		}
	}
	/**
	 * 保存内容分页
	 * @param task 任务
	 * @param orginHtml 原始HTML
	 */
	private void savePaginationContent(Task task,String orginHtml){
		//取得指定区域内容
		String html = task.getController().getHtmlParserWrapper().getTargetContentHtml(orginHtml);
		if(StringUtils.isNotBlank(html)){
			html = extractorContentResource(task, html);
			html = filterContentLink(task, html);
			//过滤指定区域内容
			html = task.getController().getHtmlParserWrapper().filterTargetContentHtml(html);
			//关键字替换
			html = replaceWords(task, html);
			task.getContentBean().getContentList().add(html);
		}
	}
	/**
	 * 过滤内容中的连接
	 * @param task 任务
	 * @param html 原始内容
	 * @return 过滤后的内容
	 */
	private String filterContentLink(Task task, String html) {
		//替换掉内容中的超链接
		if(task.getController().getCrawlScope().isReplaceHtmlLink()){
			html = task.getController().getHtmlParserWrapper().replaceHtmlLink(html);
		}
		return html;
	}

	/**
	 * 提取内容中的资源
	 * @param task 任务
	 * @param html 原始内容
	 * @return 提取后的内容
	 */
	private String extractorContentResource(Task task, String html) {
		//替换原图片地址为本地图片地址，并将原图片地址保存在任务中，以备下一步使用
		if(task.getController().getCrawlScope().isExtractContentRes()){
			String savePath = task.getController().getCrawlScope().getSavePath();
			html = task.getController().getHtmlParserWrapper().replaceHtmlResource(html,savePath,task.getContentBean().getResCrawlURIList(),task.getCrawlURI());
		}else{
			html = task.getController().getHtmlParserWrapper().getHtmlResource(html,task.getContentBean().getResCrawlURIList(),task.getCrawlURI());
		}
		return html;
	}
	
	/**
	 * 根据任务URL地址,取得HTML
	 * @param task 任务
	 */
	private String fetchHTML(Task task,CrawlLinkURI crawlURI){
		log.info("=========下载分页内容,提取原始html=========");
		HttpGet httpGet = null;
		HttpHost target = null;
		HttpClientContext context = null;
		String html = "";
		try {
			target = task.getController().getHostCache().getHttpHost(crawlURI);
			httpGet = HttpClientHelper.getHttpGet(crawlURI);
			context = HttpClientHelper.getHttpClientContext();
			html = task.getController().getHttpClient().execute(target, httpGet, task.getController().getHandler(), context);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != httpGet){
				httpGet.abort();
			}
		}
		return html;
	}
	
	/**
	 * 取得当前主线程的UrlQueue对象
	 * @return 当前主线程的UrlQueue对象
	 */
	private static UrlQueue getUrlQueue(){
		if(pageUrlQueueThreadLocal.get() == null){
			UrlQueue pageUrlQueue = new UrlQueue();
			pageUrlQueueThreadLocal.set(pageUrlQueue);
			return pageUrlQueue;
		}else{
		return pageUrlQueueThreadLocal.get();
		}
	} 
	
}
