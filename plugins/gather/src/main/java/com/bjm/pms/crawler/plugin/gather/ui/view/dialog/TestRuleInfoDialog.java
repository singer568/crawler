/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.view.dialog;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.core.constants.Constants;
import com.bjm.pms.crawler.core.data.CrawlScope;
import com.bjm.pms.crawler.core.data.uri.CrawlLinkURI;
import com.bjm.pms.crawler.core.filter.BriefAreaFilter;
import com.bjm.pms.crawler.core.filter.ContentAreaFilter;
import com.bjm.pms.crawler.core.filter.FieldFilter;
import com.bjm.pms.crawler.core.filter.Filter;
import com.bjm.pms.crawler.core.filter.LinkAreaFilter;
import com.bjm.pms.crawler.core.filter.factory.DefaultFilterFactory;
import com.bjm.pms.crawler.core.filter.factory.FilterFactory;
import com.bjm.pms.crawler.core.util.CharsetHandler;
import com.bjm.pms.crawler.core.util.DefaultURIHelper;
import com.bjm.pms.crawler.core.util.URIHelper;
import com.bjm.pms.crawler.core.util.parser.HtmlParserWrapper;
import com.bjm.pms.crawler.core.util.parser.HtmlParserWrapperImpl;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.ExtendFieldsBean;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.utils.BaseHttpClientHelper;
import com.bjm.pms.crawler.view.base.utils.MsgDialogUtil;
import com.bjm.pms.crawler.view.ui.view.dialog.AbstractDialog;

/**
 * 测试采集规则信息dialog
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-29 下午2:34:32
 * @version 1.0
 */
@Component("testRuleInfoDialog")
public class TestRuleInfoDialog extends AbstractDialog{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	private JTextArea testInfoOutput;
    private CrawlerRuleBean crawlerRuleBean;
	private TestRuleInfoDialog testRuleInfoDialog;
	
	/**HttpClient对象*/
	private  CloseableHttpClient httpClient = BaseHttpClientHelper.createHttpClient();
	
	public TestRuleInfoDialog(){
		super(800,600,true);
	}
	
	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			testInfoOutput = new JTextArea();
			testInfoOutput.setMargin(new Insets(1, 1, 1, 1));
			testInfoOutput.setEditable(false);
			testInfoOutput.setAutoscrolls(true);
			JScrollPane scrollPane = new JScrollPane(testInfoOutput);
			centerPane = scrollPane;
		}
		return centerPane;
	}
	public JButton getCancelButton(){
		return null;
	}
	public void startTest(){
		testRuleInfoDialog = this;
		if(null == crawlerRuleBean){
			MsgDialogUtil.createMessageDialog(LanguageLoader.getString("RuleContentSetting.testRuleIsEmprt"));
			this.dispose();
		}else{
			final RunTestRule runTestRule = new RunTestRule(crawlerRuleBean);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//开启线程执行测试
					Thread currThread = new Thread(runTestRule);
					currThread.start();
				}
			});
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.cowswing.ui.view.dialog.AbstractDialog#
	 * finishButtonActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void finishButtonActionPerformed(ActionEvent event) {
		testInfoOutput.setText("");
		testInfoOutput.removeAll();
		this.dispose();
	}
	

	protected void initData(String type) {
		
	}
	public void dispose(){
		super.dispose();
		centerPane = null;
	}
	
	/**
	 * @return the crawlerRuleBean
	 */
	public CrawlerRuleBean getCrawlerRuleBean() {
		return crawlerRuleBean;
	}
	/**
	 * @param crawlerRuleBean the crawlerRuleBean to set
	 */
	public void setCrawlerRuleBean(CrawlerRuleBean crawlerRuleBean) {
		this.crawlerRuleBean = crawlerRuleBean;
	}

	class RunTestRule implements Runnable{
        private CrawlerRuleBean crawlerRuleBean;
        private String orginHtml = "";
        /**过滤器工厂*/
    	private transient FilterFactory filterFactory;
    	/**爬虫配置参数*/
    	private transient CrawlScope crawlScope;
    	/**HTML解析器包装类类*/
    	private transient HtmlParserWrapper htmlParserWrapper;
    	/**URIHelper*/
    	private transient URIHelper uriHelper;
        public RunTestRule(CrawlerRuleBean crawlerRuleBean){
        	this.crawlerRuleBean = crawlerRuleBean;
        }
        private void init(){
        	List<Filter> filters = new ArrayList<Filter>();
    		filters.add(new LinkAreaFilter(crawlerRuleBean.getRuleContentBean().getLinksetStart(),crawlerRuleBean.getRuleContentBean().getLinksetEnd()));
    		filters.add(new ContentAreaFilter(crawlerRuleBean.getRuleContentBean().getContentStart(),crawlerRuleBean.getRuleContentBean().getContentEnd()));
    		filters.add(new BriefAreaFilter(crawlerRuleBean.getRuleContentBean().getDescriptionStart(),crawlerRuleBean.getRuleContentBean().getDescriptionEnd()));
//    		filters.add(new PaginationAreaFilter(crawlerRuleBean.getRuleContentPageBean().getPaginationStart(),crawlerRuleBean.getRuleContentPageBean().getPaginationEnd()));
//    		filters.add(new CommentIndexFilter(crawlerRuleBean.getRuleCommentBean().getCommentIndexStart(),crawlerRuleBean.getRuleCommentBean().getCommentIndexEnd()));
//    		filters.add(new CommentAreaFilter(crawlerRuleBean.getRuleCommentBean().getCommentAreaStart(),crawlerRuleBean.getRuleCommentBean().getCommentAreaEnd()));
//    		filters.add(new CommentFilter(crawlerRuleBean.getRuleCommentBean().getCommentStart(),crawlerRuleBean.getRuleCommentBean().getCommentEnd()));
//    		filters.add(new CommentLinkFilter(crawlerRuleBean.getRuleCommentBean().getCommentLinkStart(),crawlerRuleBean.getRuleCommentBean().getCommentLinkEnd()));
    		
    		crawlScope = new CrawlScope();
    		crawlScope.setEncoding(crawlerRuleBean.getRuleBaseBean().getPageEncoding());
    		crawlScope.setFilterList(filters);
    		crawlScope.addSeeds(crawlerRuleBean.getRuleContentBean().getAllPlans());
    		crawlScope.setRepairPageUrl(crawlerRuleBean.getRuleBaseBean().getUrlRepairUrl());
    		
    		List<Filter> midFilters = new ArrayList<Filter>();
    		//添加过度连接过滤器
    		if(null != crawlerRuleBean.getRuleContentBean() && CollectionUtils.isNotEmpty(crawlerRuleBean.getRuleContentBean().getMidExtendFields())){
    			addFilter(crawlerRuleBean.getRuleContentBean().getMidExtendFields(),midFilters);
    		}
    		crawlScope.setMidFilterList(midFilters);
    		
    		
        	filterFactory = new DefaultFilterFactory();
            filterFactory.register(crawlScope.getFilterList());
            
            uriHelper = new DefaultURIHelper(crawlScope);
            this.htmlParserWrapper = new HtmlParserWrapperImpl(filterFactory,uriHelper);
            writeLog("初始化采集配置完成");
        }
        private void addFilter(List<ExtendFieldsBean> extendFields,List<Filter> filters){
    		for(ExtendFieldsBean extendFieldsBean : extendFields){
    			filters.add(new FieldFilter(extendFieldsBean.getFields(),extendFieldsBean.getFilterStart(),extendFieldsBean.getFilterEnd()));
    		}
    	}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			init();
			test();
		}
		
		private void test(){
			long startTime = System.currentTimeMillis();
			writeLog("开始提取原始URL连接");
			writeLog("提取到"+crawlScope.getSeeds().size()+"个原始连接");
		    for(String url : crawlScope.getSeeds()){
		    	writeLog(url+"");
		    }
		    writeLog("取第一个URL:"+crawlScope.getSeeds().get(0)+",作为采集目标连接测试");
		    writeLog("开始采集连接："+crawlScope.getSeeds().get(0)+"的原始HTML内容");
		    String orginHtml = getTargetUrlHtml(crawlScope.getSeeds().get(0));
		    if(StringUtils.isBlank(orginHtml)){
		    	writeLog("没有采集到该连接的原始HTML内容,请检查配置参数");
		    	return;
		    }
		    //writeLog("采集到该连接的原始HTML内容如下");
	    	//writeLog(orginHtml+"");
		    if(StringUtils.isBlank(crawlerRuleBean.getRuleContentBean().getLinksetStart())){
		    	writeLog("没有配置采集连接区域标签参数,测试结束");
		    	return;
		    }
		    writeLog("开始提取该HTML内容中的目标连接");
		    List<CrawlLinkURI> areaLinkList = htmlParserWrapper.getLinkAreaUrlList(orginHtml, null);
		    if(CollectionUtils.isEmpty(areaLinkList)){
		    	writeLog("没有采集到该参数区域的目标连接,请检查连接区域配置参数");
		    	return;
		    }
		    writeLog("提取到"+areaLinkList.size()+"个目标连接");
		    for(CrawlLinkURI url : areaLinkList){
		    	writeLog(url.getUrl()+"");
		    }
		    String contentUrl = areaLinkList.get(0).getUrl();
		    writeLog("取第一个URL:"+contentUrl+",作为采集内容测试");
		    contentUrl = repair(contentUrl);
		    String contentHtml = "";
		  //如果有中间连接
			if(CollectionUtils.isNotEmpty(crawlScope.getMidFilterList())){
				CrawlLinkURI crawlLinkURI = new CrawlLinkURI(contentUrl,"","0","");
				writeLog("=========取得中间连接---进入地址：========="+crawlLinkURI);
				for(Filter<String,Map<String, String>> fieldfilter : crawlScope.getMidFilterList()){
					String field = null;
					List<CrawlLinkURI> tempLinkList =null;
					contentHtml = getTargetUrlHtml(contentUrl);
					for(Iterator<String> it = fieldfilter.getFetchAreaTagMap().keySet().iterator(); it.hasNext();){
						field = it.next();
						writeLog("=========取得中间连接========="+field);
					    // 取得、过滤指定属性/标签内容
						tempLinkList = htmlParserWrapper.getCrawlLinkURIByFilterMap(contentHtml,fieldfilter.getFetchAreaTagMap().get(field),fieldfilter.getDeleteAreaTagMap().get(field),crawlLinkURI);
						if(CollectionUtils.isNotEmpty(tempLinkList)){
							crawlLinkURI = tempLinkList.get(0);
							crawlLinkURI.setUrl(repair(crawlLinkURI.getUrl()));
							contentUrl = crawlLinkURI.getUrl();
							writeLog("=========取得中间连接地址：========="+crawlLinkURI);
						}
					}
				}
				writeLog("=========取得中间连接---结果地址：========="+crawlLinkURI);
			}
			  contentHtml = getTargetUrlHtml(contentUrl);
			    if(StringUtils.isBlank(contentHtml)){
			    	writeLog("没有采集到目标连接原始HTML,请检查配置参数");
			    	return;
			    }
		    
		    //writeLog("采集到该连接的原始HTML内容如下");
	    	//writeLog(contentHtml+"");
		    if(StringUtils.isNotBlank(crawlerRuleBean.getRuleContentBean().getDescriptionStart())){
		    	writeLog("开始提取内容中的描述内容");
		    	String brief = htmlParserWrapper.getContentBrief(contentHtml);
		    	 if(StringUtils.isBlank(contentHtml)){
		    		 writeLog("没有提取到描述信息,请检查描述区域配置参数");
				 }else{
					 writeLog("提取到描述信息,如下");
					 writeLog(brief);
				 }
		    }
		    if(StringUtils.isNotBlank(crawlerRuleBean.getRuleContentBean().getContentStart())){
		    	writeLog("开始提取目标内容");
		    	String content = htmlParserWrapper.getTargetContentHtml(contentHtml);
		    	 if(StringUtils.isBlank(contentHtml)){
		    		 writeLog("没有提取到目标内容信息,请检查内容区域配置参数");
				 }else{
					 writeLog("提取到目标内容信息,如下");
					 writeLog(content);
				 }
		    }
		    long endTime = System.currentTimeMillis();
		    writeLog("本次测试结束,耗时："+(endTime - startTime)+"毫秒");
		}
		/**
		 * 补全URL
		 * <p>方法说明:</>
		 * <li></li>
		 * @author DuanYong
		 * @since 2014-10-13 下午4:43:35
		 * @version 1.0
		 * @exception 
		 * @param url
		 */
		private String repair(String url){
			 if(!url.contains(Constants.HTTP_FILL_KEY)){
			    	if(StringUtils.isBlank(crawlScope.getRepairPageUrl())){
			    		writeLog("当前URL:"+url+"是相对地址，需要设置页面链接补全URL");
			    		return "";
			    	}
			    	url = crawlScope.getRepairPageUrl() + url;
			    	writeLog("补全后URL:"+url+"");
			  }
			 return url;
		}
		/**
		 * 打印日志
		 * <p>方法说明:</>
		 * <li></li>
		 * @author DuanYong
		 * @since 2014-9-29 下午9:10:06
		 * @version 1.0
		 * @exception 
		 * @param log
		 */
		private void writeLog(String log){
			testInfoOutput.append(log+"\n");
		}
		/**
		 * 获取指定页面原始内容
		 * <p>方法说明:</>
		 * <li></li>
		 * @author DuanYong
		 * @since 2014-9-29 下午3:40:19
		 * @version 1.0
		 * @exception 
		 * @param url
		 * @return
		 */
		private String getTargetUrlHtml(String url){
			HttpGet httpGet = null;
			HttpHost target = null;
			HttpClientContext context = null;
			try {
				target = URIUtils.extractHost(new URI(url));
				httpGet = BaseHttpClientHelper.getHttpGet(url);
				context = BaseHttpClientHelper.getHttpClientContext();
				String html = httpClient.execute(target,httpGet,new CharsetHandler(crawlerRuleBean.getRuleBaseBean().getPageEncoding()), context);
				logger.info("连接："+url+",的原始HTML内容=");
				logger.info(html);
				return html;
			}  catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}finally{
				if(null != httpGet){
					httpGet.abort();
				}
			}
			return "";
		}
		/**
		 * 测试成功
		 * <p>方法说明:</>
		 * <li></li>
		 * @author DuanYong
		 * @since 2014-9-29 下午3:08:26
		 * @version 1.0
		 * @exception
		 */
		private void testComplate(){
//			//创建待更新标志文件
//			try {
//				FileUtils.createFile(Constant.UPDATE_FILE);
//			} catch (IOException e) {
//				e.printStackTrace();
//				logger.error("创建待更新标志文件失败："+e.getMessage());
//			}
//			testRuleInfoDialog.dispose();
//			SwingUtilities.invokeLater(new Runnable() {
//				public void run() {
//					int result = JOptionPane.showConfirmDialog(
//							crawlerMainFrame, LanguageLoader.getString("Core.version_update_complete"),
//							LanguageLoader.getString("Common.confirm"),
//							JOptionPane.YES_NO_OPTION);
//					if (result == 0) {
//						crawlerMainFrame.dispose();
//					}
//				}
//			});
		}
	}
	
}
