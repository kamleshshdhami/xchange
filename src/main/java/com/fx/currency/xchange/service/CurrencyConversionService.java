package com.fx.currency.xchange.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class CurrencyConversionService {

	private double conversionValue = 2;

	public BigDecimal convert(BigDecimal quantity, String fromCurrency, String toCurrency) {

		if ("GBP".equals(fromCurrency) && "USD".equals(toCurrency)) {
			return quantity.multiply(new BigDecimal(conversionValue)) ;
		} else {
			return null;
		}

	}

}
