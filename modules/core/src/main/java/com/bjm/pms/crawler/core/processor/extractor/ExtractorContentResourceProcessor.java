package com.bjm.pms.crawler.core.processor.extractor;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;

import com.bjm.pms.crawler.core.constants.Constants;
import com.bjm.pms.crawler.core.data.Task;
import com.bjm.pms.crawler.core.data.uri.CrawlResURI;
import com.bjm.pms.crawler.core.util.HttpClientHelper;


/**
 * 任务处理器接口-抽取内容资源实现类
 * @author javacoo
 * @since 2011-11-15
 */
public class ExtractorContentResourceProcessor extends Extractor{
	
	public ExtractorContentResourceProcessor() {
		super();
		setDefaultNextProcessor(new ExtractorFieldProcessor());
	}
	@Override
	protected void extract(Task task) {
		log.info("=========抽取内容资源=========");
		List<CrawlResURI> resCrawlURIList = task.getContentBean().getResCrawlURIList();
		if(task.getController().getCrawlScope().isExtractContentRes() && !CollectionUtils.isEmpty(resCrawlURIList)){
			CrawlResURI parentCrawlResURI = new CrawlResURI();
			parentCrawlResURI.setPort(task.getCrawlURI().getPort());
			parentCrawlResURI.setHost(task.getCrawlURI().getHost());
			parentCrawlResURI.setRawPath(task.getCrawlURI().getRawPath());
			for(CrawlResURI crawlURI : resCrawlURIList){
				crawlURI.setParentURI(parentCrawlResURI);
				HttpGet httpGet = null;
				HttpHost target = null;
				HttpClientContext context = null;
				String key = crawlURI.getNewResUrl();
				String value = crawlURI.getOriginResUrl();
				try {
					if(StringUtils.isNotEmpty(value)){
						target = task.getController().getHostCache().getHttpHost(crawlURI);
						httpGet = HttpClientHelper.getHttpGet(crawlURI);
						context = HttpClientHelper.getHttpClientContext();
						HttpResponse response = task.getController().getHttpClient().execute(target, httpGet, context);
						HttpEntity entity = response.getEntity();
						if (entity != null) {
							task.getController().getCrawlScope().getFileHelper().saveFile(entity.getContent(), Constants.SYSTEM_ROOT_PATH +key);
						}
					}
				}  catch (IOException e) {
					e.printStackTrace();
				}finally{
					if(null != httpGet){
						httpGet.abort();
					}
				}
			}
		}
		
	}
	

}
