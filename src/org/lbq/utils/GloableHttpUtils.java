package org.lbq.utils;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class GloableHttpUtils
{
	private static GloableHttpUtils instance; // 唯一实例
	/**
	 * 同步，初始化唯一实例
	 * @return
	 */
	public synchronized static GloableHttpUtils getInstance() {
		if (instance == null) {
			instance = new GloableHttpUtils();
		}
		return instance;
	}
	/**
	 * 建构函数私有以防止其它对象创建本类实例
	 */
	private GloableHttpUtils() {
	}
	
	public CloseableHttpClient getCloseableHttpClient()
	{
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD)
		        .setConnectionRequestTimeout(6000).setConnectTimeout(6000).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
		return httpClient;
	}
	
	public HttpGet getEmptyHttpGet()
	{
		HttpGet httpGet = new HttpGet();
		httpGet.addHeader("User-Agent",
		        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36 OPR/44.0.2510.857 (Edition C");
		httpGet.addHeader("Cookie", "ASP.NET_SessionId=vadfby45xumzskqvocww2m55");
		httpGet.addHeader("Connection", "keep-alive");
		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		return httpGet;

	}
	public HttpGet getHttpGetByUri(String uri)
	{
		HttpGet httpGet = new HttpGet(uri);
		httpGet.addHeader("User-Agent",
		        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36 OPR/44.0.2510.857 (Edition C");
		httpGet.addHeader("Cookie", "ASP.NET_SessionId=vadfby45xumzskqvocww2m55");
		httpGet.addHeader("Connection", "keep-alive");
		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch ,GBK");
		httpGet.addHeader("Content-Encoding", "GBK");
		httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		return httpGet;
	}
	
}
