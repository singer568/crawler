/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.base.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.util.CharArrayBuffer;
import org.javacoo.cowswing.base.constant.Config;
import org.javacoo.crawler.core.constants.Constants;
import org.javacoo.crawler.core.data.CrawlScope;
import org.javacoo.crawler.core.data.uri.CrawlURI;

/**
 * HttpClientHelper
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-10-19 下午2:15:32
 * @version 1.0
 */
public class BaseHttpClientHelper {
	private static Log log =  LogFactory.getLog(BaseHttpClientHelper.class);
	
	public static CloseableHttpClient createHttpClient(){
		  HttpHost proxy = null;

		 // Use custom message parser / writer to customize the way HTTP
	        // messages are parsed from and written out to the data stream.
	        HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

	            @Override
	            public HttpMessageParser<HttpResponse> create(
	                SessionInputBuffer buffer, MessageConstraints constraints) {
	                LineParser lineParser = new BasicLineParser() {

	                    @Override
	                    public Header parseHeader(final CharArrayBuffer buffer) {
	                        try {
	                            return super.parseHeader(buffer);
	                        } catch (ParseException ex) {
	                            return new BasicHeader(buffer.toString(), null);
	                        }
	                    }

	                };
	                return new DefaultHttpResponseParser(
	                    buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {

	                    @Override
	                    protected boolean reject(final CharArrayBuffer line, int count) {
	                        // try to ignore all garbage preceding a status line infinitely
	                        return false;
	                    }

	                };
	            }

	        };
	        HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

	        // Use a custom connection factory to customize the process of
	        // initialization of outgoing HTTP connections. Beside standard connection
	        // configuration parameters HTTP connection factory can define message
	        // parser / writer routines to be employed by individual connections.
	        HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
	                requestWriterFactory, responseParserFactory);

	        // Client HTTP connection objects when fully initialized can be bound to
	        // an arbitrary network socket. The process of network socket initialization,
	        // its connection to a remote address and binding to a local one is controlled
	        // by a connection socket factory.

	        // SSL context for secure connections can be created either based on
	        // system or application specific properties.
	        SSLContext sslcontext = SSLContexts.createSystemDefault();
	        // Use custom hostname verifier to customize SSL hostname verification.
	        X509HostnameVerifier hostnameVerifier = new BrowserCompatHostnameVerifier();

	        // Create a registry of custom connection socket factories for supported
	        // protocol schemes.
	        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register(Constants.HTTP_CLIENT_REG_HTTP_KEY, PlainConnectionSocketFactory.INSTANCE)
	            .register(Constants.HTTP_CLIENT_REG_HTTPS_KEY, new SSLConnectionSocketFactory(sslcontext, hostnameVerifier))
	            .build();

	        // Use custom DNS resolver to override the system DNS resolution.
	        DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

	            @Override
	            public InetAddress[] resolve(final String host) throws UnknownHostException {
	                if (host.equalsIgnoreCase("myhost")) {
	                    return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
	                } else {
	                    return super.resolve(host);
	                }
	            }

	        };

	        // Create a connection manager with custom configuration.
	        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
	                socketFactoryRegistry, connFactory, dnsResolver);

	        // Create socket configuration
	        SocketConfig socketConfig = SocketConfig.custom()
	            .setTcpNoDelay(true)
	            .setSoTimeout(Constants.HTTP_SOCKET_TIMEOUT)
	            .build();
	        // Configure the connection manager to use socket configuration either
	        // by default or for a specific host.
	        connManager.setDefaultSocketConfig(socketConfig);
	       // connManager.setSocketConfig(target, socketConfig);

	        // Create message constraints
	        MessageConstraints messageConstraints = MessageConstraints.custom()
	            .setMaxHeaderCount(200)
	            .setMaxLineLength(2000)
	            .build();
	        // Create connection configuration
	        ConnectionConfig connectionConfig = ConnectionConfig.custom()
	            .setMalformedInputAction(CodingErrorAction.IGNORE)
	            .setUnmappableInputAction(CodingErrorAction.IGNORE)
	            .setCharset(Consts.UTF_8)
	            .setMessageConstraints(messageConstraints)
	            .build();
	        // Configure the connection manager to use connection configuration either
	        // by default or for a specific host.
	        connManager.setDefaultConnectionConfig(connectionConfig);
	       // connManager.setConnectionConfig(target, ConnectionConfig.DEFAULT);

	        // Configure total max or per route limits for persistent connections
	        // that can be kept in the pool or leased by the connection manager.
	        connManager.setMaxTotal(Constants.HTTP_CLIENT_MAX_CONN);
	        connManager.setDefaultMaxPerRoute(Constants.HTTP_CLIENT_MAX_ROUTE);
	        //connManager.setMaxPerRoute(new HttpRoute(target), Constants.HTTP_CLIENT_MAX_ROUTE);

	        // Use custom cookie store if necessary.
	        CookieStore cookieStore = new BasicCookieStore();
	        // Use custom credentials provider if necessary.
	        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	        // Create global request configuration
	        RequestConfig defaultRequestConfig = getDefaultRequestConfig();

	        // Create an HttpClient with the given custom dependencies and configuration.
	        CloseableHttpClient httpclient = HttpClients.custom()
	            .setConnectionManager(connManager)
	            .setDefaultCookieStore(cookieStore)
	            .setDefaultCredentialsProvider(credentialsProvider)
	            .setProxy(proxy)
	            .setDefaultRequestConfig(defaultRequestConfig)
	            .build();
	        return httpclient;
	}
	public static RequestConfig getDefaultRequestConfig(){
		RequestConfig defaultRequestConfig = RequestConfig.custom()
	            .setCookieSpec(CookieSpecs.BEST_MATCH)
	            .setExpectContinueEnabled(true)
	            .setStaleConnectionCheckEnabled(true)
	            .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
	            .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
	            .build();
		return defaultRequestConfig;
	}
	
	public static HttpGet getHttpGet(String path){
		Log log =  LogFactory.getLog(HttpGet.class);
		
		log.info("==============当前访问路径========"+path);
		HttpGet httpGet = new HttpGet(path);
		RequestConfig requestConfig = RequestConfig.copy(BaseHttpClientHelper.getDefaultRequestConfig())
                .setSocketTimeout(Constants.HTTP_SOCKET_TIMEOUT)
                .setConnectTimeout(Constants.HTTP_CONN_TIMEOUT)
                .setConnectionRequestTimeout(Constants.HTTP_CONN_TIMEOUT)
                //.setProxy(proxy)
                .build();
		httpGet.setConfig(requestConfig);
		httpGet.setHeader("Accept",Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_ACCEPT)); 
	    httpGet.setHeader("Accept-Language",Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_ACCEPT_LANGUAGE));
	    httpGet.setHeader("User-Agent", Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_USER_AGENT));
	    httpGet.setHeader("Accept-Charset",Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_ACCEPT_CHARSET));
	    httpGet.setHeader("Keep-Alive", Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_KEEP_ALIVE));
	    httpGet.setHeader("Connection", Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_CONNECTION));
	    httpGet.setHeader("Cache-Control", Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_CACHE_CONTROL)); 
		return httpGet;
	}
	/**
	 * 取得URI对象的路径
	 * @param uri
	 * @return
	 */
	private static String getRawPath(String rawPath){
		if(StringUtils.isBlank(rawPath) || rawPath.lastIndexOf("/") == rawPath.indexOf("/")){
			rawPath = "";
		}else {
			int endPos = rawPath.lastIndexOf("/");
			rawPath = rawPath.substring(0, endPos);
		}
		return rawPath;
	}
	
	public static HttpClientContext getHttpClientContext(){
		 // Execution context can be customized locally.
        HttpClientContext context = HttpClientContext.create();
        // Contextual attributes set the local context level will take
        // precedence over those set at the client level.
        CookieStore cookieStore = new BasicCookieStore();
        context.setCookieStore(cookieStore);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        context.setCredentialsProvider(credentialsProvider);
        return context;
	}


}
