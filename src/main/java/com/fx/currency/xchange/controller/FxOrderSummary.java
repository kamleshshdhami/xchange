package com.fx.currency.xchange.controller;

import java.util.List;

public class FxOrderSummary {
	private long orderId;
	private String orderStatus;
	private List<CurrencyAllocationDetail> breakDownDetails;

	public FxOrderSummary(long orderId, String orderStatus, List<CurrencyAllocationDetail> breakDownDetails) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.breakDownDetails = breakDownDetails;
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

	public List<CurrencyAllocationDetail> getBreakDownDetails() {
		return breakDownDetails;
	}

	public void setBreakDownDetails(List<CurrencyAllocationDetail> breakDownDetails) {
		this.breakDownDetails = breakDownDetails;
	}

}
