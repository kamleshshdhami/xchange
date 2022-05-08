package com.fx.currency.xchange.controller;

import java.util.List;

public class FxOrderSummary {
	private long orderId;
	private String orderStatus;
	private List<CurrencyAllocationDetail> currencyAllocationDetails;

	public FxOrderSummary(long orderId, String orderStatus, List<CurrencyAllocationDetail> currencyAllocationDetails) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.currencyAllocationDetails = currencyAllocationDetails;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<CurrencyAllocationDetail> getCurrencyAllocationDetails() {
		return currencyAllocationDetails;
	}

	public void setCurrencyAllocationDetails(List<CurrencyAllocationDetail> currencyAllocationDetails) {
		this.currencyAllocationDetails = currencyAllocationDetails;
	}

}
