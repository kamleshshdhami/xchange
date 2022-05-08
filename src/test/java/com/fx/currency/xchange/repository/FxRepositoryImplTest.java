package com.fx.currency.xchange.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.util.CollectionUtils;

import com.fx.currency.xchange.entity.FxOrderEntity;
import com.fx.currency.xchange.entity.FxOrderMatchStatusEntity;
import com.fx.currency.xchange.entity.FxOrderMatchStatusEntityPk;
import com.fx.currency.xchange.exception.ResourceNotFoundException;
import com.fx.currency.xchange.util.FxConstant;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = { JpaConfig.class })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
public class FxRepositoryImplTest {

	@Autowired
	private FxRepository fxRepository;

	private FxOrderEntity fxOrderOne;
	private List<FxOrderEntity> fxOrders;
	private List<String> statusList;
	private List<String> currencyTypeList;
	private FxOrderMatchStatusEntity fxOrderMatchStatusEntity;
	private FxOrderMatchStatusEntityPk fxOrderMatchStatusEntityPk;
	private List<FxOrderMatchStatusEntity> fxOrderMatchStatusEntityList;

	@BeforeAll
	void setUp() {
		fxOrderOne = new FxOrderEntity();
		fxOrderOne.setAmount(new BigDecimal(100));
		fxOrderOne.setCurrency("GBP");
		fxOrderOne.setStatus(FxConstant.PENDING);
		fxOrderOne.setMatchedUsdAmt(new BigDecimal(0));
		fxOrderOne.setUsdAmt(new BigDecimal(200));

		FxOrderEntity fxOrderTwo = new FxOrderEntity();
		fxOrderTwo.setAmount(new BigDecimal(100));
		fxOrderTwo.setCurrency("USD");
		fxOrderTwo.setStatus(FxConstant.PENDING);
		fxOrderTwo.setMatchedUsdAmt(new BigDecimal(0));
		fxOrderTwo.setUsdAmt(new BigDecimal(100));

		fxOrders = new ArrayList<>();
		fxOrders.add(fxOrderOne);
		fxOrders.add(fxOrderTwo);

		statusList = new ArrayList<>();
		statusList.add(FxConstant.PENDING);

		currencyTypeList = new ArrayList<>();
		currencyTypeList.add("GBP");

		fxOrderMatchStatusEntity = new FxOrderMatchStatusEntity();
		fxOrderMatchStatusEntityPk = new FxOrderMatchStatusEntityPk();
		fxOrderMatchStatusEntityPk.setGbpId(1L);
		fxOrderMatchStatusEntityPk.setUsdId(2L);
		fxOrderMatchStatusEntity.setKey(fxOrderMatchStatusEntityPk);
		fxOrderMatchStatusEntityList = new ArrayList<>();

		fxOrderMatchStatusEntityList.add(fxOrderMatchStatusEntity);

	}

	@Test
	void testCreatefxOrder() {
		FxOrderEntity savedFxOrder = fxRepository.createfxOrder(fxOrderOne);
		long orderId = savedFxOrder.getId();
		FxOrderEntity retrievedFxOrder = fxRepository.fetchOrderById(String.valueOf(orderId));
		assertEquals(savedFxOrder.getAmount(), retrievedFxOrder.getAmount());
		assertEquals(savedFxOrder.getCurrency(), retrievedFxOrder.getCurrency());

	}

	@Test
	void testFetchRecordsByStatusAndCurrencyType() {
		fxRepository.createfxOrder(fxOrderOne);
		List<FxOrderEntity> fxOrderEntities = fxRepository.fetchRecordsByStatusAndCurrencyType(statusList,
				currencyTypeList);
		assertEquals(1, fxOrderEntities.size());

	}

	@Test
	void testFetchAllRecords() {
		fxRepository.createfxOrder(fxOrderOne);
		List<FxOrderEntity> fxOrderEntities = fxRepository.fetchAllRecords();
		assertEquals(1, fxOrderEntities.size());

	}

	@Test
	void testSavefxOrderEntity() {
		fxRepository.savefxOrderEntity(fxOrders);
		List<FxOrderEntity> fxOrderEntities = fxRepository.fetchAllRecords();
		assertEquals(2, fxOrderEntities.size());
	}

	@Test
	void testFetchGBPBreadDownInformation() {
		fxRepository.saveFxOrderMatchStatusEntity(fxOrderMatchStatusEntityList);

		List<FxOrderMatchStatusEntity> fxOrderMatchStatusEntityList = fxRepository.fetchBreadDownInformation(1,
				FxConstant.GBP);
		assertEquals(1, fxOrderMatchStatusEntityList.size());

	}

	@Test
	void testFetchUSDBreadDownInformation() {
		fxRepository.saveFxOrderMatchStatusEntity(fxOrderMatchStatusEntityList);

		List<FxOrderMatchStatusEntity> fxOrderMatchStatusEntityList = fxRepository.fetchBreadDownInformation(2,
				FxConstant.USD);
		assertEquals(1, fxOrderMatchStatusEntityList.size());

	}

	@Test
	void testFetchBreadDownInformationWithNull() {
		fxRepository.saveFxOrderMatchStatusEntity(fxOrderMatchStatusEntityList);

		List<FxOrderMatchStatusEntity> fxOrderMatchStatusEntityList = fxRepository.fetchBreadDownInformation(2,
				"ABC");
		
		
		assertTrue(CollectionUtils.isEmpty(fxOrderMatchStatusEntityList));

	}
	
	
	@Test
	void testFetchOrderByIdNotFound() {

		assertThrows(ResourceNotFoundException.class, () -> {
			fxRepository.fetchOrderById(String.valueOf(1L));
		});

	}
}
