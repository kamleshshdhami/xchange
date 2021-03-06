package com.fx.currency.xchange;

import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fx.currency.xchange.controller.FxTradingController;
import com.fx.currency.xchange.repository.JpaConfig;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = { JpaConfig.class })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class XchangeApplicationTests {

	@Autowired
	private FxTradingController fxTradingController;

	@Test
	void contextLoads() {

		assertTrue(!Objects.isNull(fxTradingController));

	}

}
