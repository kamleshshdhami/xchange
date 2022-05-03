package com.fx.currency.xchange.controller;

public class CurrencyAllocationDetail {
	private long gbpOrderId;
	private long usdOrderId;

	public long getGbpOrderId() {
		return gbpOrderId;
	}

	public void setGbpOrderId(long gbpOrderId) {
		this.gbpOrderId = gbpOrderId;
	}

	public long getUsdOrderId() {
		return usdOrderId;
	}

	public void setUsdOrderId(long usdOrderId) {
		this.usdOrderId = usdOrderId;
	}

	public CurrencyAllocationDetail(long gbpOrderId, long usdOrderId) {
		super();
		this.gbpOrderId = gbpOrderId;
		this.usdOrderId = usdOrderId;
	}

	
}
