package com.zhihuishu.util;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * OkHttpClient 工厂，封装配置部分代码
 * @author	zlikun
 * @date	2017年5月23日 下午7:53:00
 */
public class OkHttpClientFactory {

	private static OkHttpClient okHttpClient = null ;

	static {
		okHttpClient = new OkHttpClient.Builder()
				.connectionPool(new ConnectionPool(100, 60, TimeUnit.SECONDS))
				.connectTimeout(5000, TimeUnit.MILLISECONDS)
				.readTimeout(3000, TimeUnit.MILLISECONDS)
				.writeTimeout(5000, TimeUnit.MILLISECONDS)
				.build();

	}
	
	/**
	 * 获取OkHttpClient实例(单例)
	 * @return
	 */
	public static final OkHttpClient getInstance() {
		return okHttpClient ;
	}
	
}