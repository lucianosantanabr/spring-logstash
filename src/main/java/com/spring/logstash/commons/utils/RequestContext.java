package com.spring.logstash.commons.utils;

public final class RequestContext {
	private static final ThreadLocal<String> requestId = new ThreadLocal<>();
	private static final ThreadLocal<String> xSalesTxId = new ThreadLocal<>();
	public static final String X_REQUEST_ID = "X-Request-Id";
	public static final String X_APP_TX_ID = "x-app-tx-id";


	public static String getRequestId() {
		return requestId.get();
	}

	public static void setRequestId(String id) {
		requestId.set(id);
	}
	
	public static String getXSalesTxId() {
	  return xSalesTxId.get();
	}
	
	public static void setXSalesTxId(String id) {
	  xSalesTxId.set(id);
	}

}
