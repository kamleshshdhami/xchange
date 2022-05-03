package com.fx.currency.xchange.exception;

import java.util.List;

public class ErrorDetails {

	private String message;
	private List<String> details;

	public ErrorDetails(String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getDetails() {
		return details;
	}

}
