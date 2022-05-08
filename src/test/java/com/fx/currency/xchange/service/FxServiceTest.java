package com.fx.currency.xchange.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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

import com.fx.currency.xchange.controller.FxOrder;
import com.fx.currency.xchange.controller.FxOrderSummary;
import com.fx.currency.xchange.repository.JpaConfig;
import com.fx.currency.xchange.util.FxConstant;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = { JpaConfig.class })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
public class FxServiceTest {

	@Autowired
	private FxService fxService;

	private FxOrder fxOrderOne;
	private FxOrder fxOrderTwo;

	@BeforeAll
	void setUp() {
		fxOrderOne = new FxOrder();
		fxOrderOne.setAmount("100.00");
		fxOrderOne.setCurrencyType(FxConstant.GBP);

		
		fxOrderTwo = new FxOrder();
		fxOrderTwo.setAmount("200.00");
		fxOrderTwo.setCurrencyType(FxConstant.USD);

	}

	@Test
	void testCreatefxOrder() {
		String orderId = fxService.createfxOrder(fxOrderOne);
		FxOrder savedFxOrder = fxService.retrieveOrderById(orderId);

		assertEquals(orderId, savedFxOrder.getId());
		assertEquals(fxOrderOne.getAmount(), savedFxOrder.getAmount());
		assertEquals(fxOrderOne.getCurrencyType(), savedFxOrder.getCurrencyType());

	}

	@Test
	void testRetrieveAllOrderSummary() {
		fxService.createfxOrder(fxOrderOne);
		fxService.createfxOrder(fxOrderTwo);
		List<FxOrderSummary>  fxOrderSummaryList = fxService.retrieveAllOrderSummary();

		assertEquals(fxOrderSummaryList.size(), 2);
		for (FxOrderSummary fxOrderSummary : fxOrderSummaryList) {
			assertNotNull(fxOrderSummary);
		}

	}
	
	@Test
	void testMatchOrder() {
		fxService.createfxOrder(fxOrderOne);
		fxService.createfxOrder(fxOrderTwo);
		fxService.matchOrder();
		
		List<FxOrderSummary>  fxOrderSummaryList = fxService.retrieveAllOrderSummary();

		for (FxOrderSummary fxOrderSummary : fxOrderSummaryList) {
			assertEquals(FxConstant.FULLY_MATCHED, fxOrderSummary.getOrderStatus());
		}

	}
}
