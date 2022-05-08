package com.fx.currency.xchange.service;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fx.currency.xchange.repository.JpaConfig;
import com.fx.currency.xchange.util.FxConstant;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = { JpaConfig.class })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
public class CurrencyConversionServiceTest {

	@Autowired
	CurrencyConversionService currencyConversionService;
	
	private BigDecimal gbpAmt;
	private BigDecimal usdAmt;
	
	@BeforeAll
	void setUp() {
		gbpAmt = new BigDecimal(10);
		usdAmt = new BigDecimal(20);
	}
	
	@Test
	void testConvertGbptoUsd() {
		BigDecimal convertedUsdAmt = currencyConversionService.convert(gbpAmt, FxConstant.GBP, FxConstant.USD);
		assertTrue(usdAmt.compareTo(convertedUsdAmt) ==0);
		
		
	}
}
