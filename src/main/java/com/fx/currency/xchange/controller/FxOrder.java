package com.fx.currency.xchange.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value= Include.NON_NULL)
public class FxOrder {

	private String id;

	@NotBlank(message = "Amount is required")
	@Pattern(regexp = "^(\\d+(\\.\\d{0,2})?|\\.?\\d{1,2})$", message = "Amount is Not valid")
	private String amount;

	@NotBlank(message = "Currency Type is required")
	@Pattern(regexp = "^(USD|GBP)$", message = "Currency Type should be GBP or USD")
	private String currencyType;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FxOrder(String id,
			@NotBlank(message = "Amount is required") @Pattern(regexp = "^(\\d+(\\.\\d{0,2})?|\\.?\\d{1,2})$", message = "Amount is Not valid") String amount,
			@NotBlank(message = "Currency Type is required") @Pattern(regexp = "^(USD|GBP)$", message = "Currency Type should be GBP or USD") String currencyType) {
		super();
		this.id = id;
		this.amount = amount;
		this.currencyType = currencyType;
	}

	public FxOrder() {
		super();
	}
	
	public FxOrder(String id) {
		super();
		this.id = id;
	}

}
