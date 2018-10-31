package com.tevid.jbox.http;

import com.tevid.jbox.contant.Const;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * 
 * Copyright (c) 2018,www.vip.com All rights reserved.
 *
 * <p>
 * Http工具类
 * </p>
 * 
 * @author  2018年8月17日
 */
public class HttpClientManager {

	private static Logger logger = LoggerFactory.getLogger(HttpClientManager.class);

	private final static String DEFAULT_CONTENT_TYPE="application/json";
	private final static String DEFAULT_CHARSET="UTF-8";
	private final static String CONTENT_TYPE="Content-Type";

	private HttpClient httpClient;
	private static class HttpClientManagerHandler {
		private static final HttpClientManager instance = new HttpClientManager();
	}

	public static HttpClientManager getInstance() {
		return HttpClientManagerHandler.instance;
	}

	public HttpClientManager() {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setMaxTotalConnections(1);// 总的连接数
		params.setDefaultMaxConnectionsPerHost(2);// 每个host的最大连接数
		connectionManager.setParams(params);
		httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, true));
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10 * 1000);
		httpClient.getParams().setSoTimeout(60 * 1000);
	}

	/**
	 * post请求,返回字符串
	 * @param url
	 * @param bodyContent
	 * @return
	 * @throws HttpBizException
	 * @throws IOException
	 */
	public String post(String url, String bodyContent) throws HttpBizException, IOException {
		PostMethod method = new PostMethod(url);
		try {
			// Content-Type请求设置
			method.addRequestHeader(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
			// body内容体
			if (StringUtils.isNotBlank(bodyContent)) {
				RequestEntity entity = new StringRequestEntity(bodyContent, DEFAULT_CONTENT_TYPE, DEFAULT_CHARSET);
				method.setRequestEntity(entity);
			}
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
				throw new HttpBizException(Const.RETURN_CODE.HTTP_FAIL.value,"Http服务链路异常:服务器状态码为" + statusCode);
			}
			return method.getResponseBodyAsString();
		}  finally {
			method.releaseConnection();
		}
	}

	/**
	 * get请求,返回字符串
	 * @param url
	 * @param parameters
	 * @return
	 * @throws HttpBizException
	 * @throws IOException
	 */
	public String get(String url, List<NameValuePair> parameters) throws HttpBizException, IOException {
		GetMethod method = new GetMethod(url);
		try {
			// Content-Type请求设置
			method.addRequestHeader(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
			if(!CollectionUtils.isEmpty(parameters)){
				method.setQueryString(parameters.toArray(new NameValuePair[]{}));
			}
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
				throw new HttpBizException(Const.RETURN_CODE.HTTP_FAIL.value,"Http服务链路异常:服务器状态码为" + statusCode);
			}
			return method.getResponseBodyAsString();
		}  finally {
			method.releaseConnection();
		}
	}
}
