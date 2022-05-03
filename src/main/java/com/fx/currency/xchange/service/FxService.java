package com.fx.currency.xchange.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fx.currency.xchange.controller.CurrencyAllocationDetail;
import com.fx.currency.xchange.controller.FxOrder;
import com.fx.currency.xchange.controller.FxOrderSummary;
import com.fx.currency.xchange.entity.FxOrderEntity;
import com.fx.currency.xchange.entity.FxOrderMatchStatusEntity;
import com.fx.currency.xchange.entity.FxOrderMatchStatusEntityPk;
import com.fx.currency.xchange.repository.FxRepository;
import com.fx.currency.xchange.util.FxConstant;

@Service
public class FxService {

	@Autowired
	private FxRepository fxRepository;

	@Autowired
	private CurrencyConversionService currencyConversionService;

	public List<FxOrderSummary> retrieveOrderSummary() {
		List<FxOrderEntity> fxOrderList = fxRepository.fetchAllRecords();

		List<FxOrderSummary> fxOrderSummaryList = new ArrayList<>();
		List<CurrencyAllocationDetail> currencyAllocationDetails = null;
		List<FxOrderMatchStatusEntity> fxOrderMatchStatusEntityList = null;

		for (FxOrderEntity fxOrderEntity : fxOrderList) {

			currencyAllocationDetails = new ArrayList<>();

			fxOrderMatchStatusEntityList = fxRepository.fetchBreadDownInformation(fxOrderEntity.getId(),
					fxOrderEntity.getCurrency());

			for (FxOrderMatchStatusEntity fxOrderMatchStatusEntity : fxOrderMatchStatusEntityList) {
				currencyAllocationDetails.add(new CurrencyAllocationDetail(fxOrderMatchStatusEntity.getKey().getGbpId(),
						fxOrderMatchStatusEntity.getKey().getUsdId()));
			}

			fxOrderSummaryList.add(
					new FxOrderSummary(fxOrderEntity.getId(), fxOrderEntity.getStatus(), currencyAllocationDetails));
		}

		return fxOrderSummaryList;
	}

	public FxOrder retrieveOrderById(String orderId) {
		FxOrderEntity fxOrderEntity = fxRepository.fetchOrderById(orderId);
		return new FxOrder(String.valueOf(fxOrderEntity.getId()), fxOrderEntity.getAmount().toString(),
				fxOrderEntity.getCurrency());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String createfxOrder(FxOrder fxOrder) {

		FxOrderEntity fxOrderEntity = new FxOrderEntity();
		fxOrderEntity.setAmount(BigDecimal.valueOf(Double.valueOf(fxOrder.getAmount())));
		fxOrderEntity.setCurrency(fxOrder.getCurrencyType());
		fxOrderEntity.setStatus(FxConstant.PENDING);
		fxOrderEntity.setMatchedUsdAmt(BigDecimal.ZERO);
		if (fxOrder.getCurrencyType().equals(FxConstant.GBP)) {
			fxOrderEntity.setUsdAmt(currencyConversionService
					.convert(BigDecimal.valueOf(Double.valueOf(fxOrder.getAmount())), FxConstant.GBP, FxConstant.USD));
		} else {
			fxOrderEntity.setUsdAmt(BigDecimal.valueOf(Double.valueOf(fxOrder.getAmount())));
		}

		FxOrderEntity persistedFxOrderEntity = fxRepository.createfxOrder(fxOrderEntity);

		return String.valueOf(persistedFxOrderEntity.getId());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void matchOrder() {
		List<String> notSettledRecordStatus = new ArrayList<>();
		notSettledRecordStatus.add(FxConstant.PENDING);
		notSettledRecordStatus.add(FxConstant.PARTIALLY_MATCHED);

		List<String> currencyTypeGbp = new ArrayList<>();
		currencyTypeGbp.add(FxConstant.GBP);

		List<String> currencyTypeUsd = new ArrayList<>();
		currencyTypeUsd.add(FxConstant.USD);

		List<FxOrderEntity> notSettledRecordGbpList = fxRepository.fetchRecordsByStatusAndCurrencyType(notSettledRecordStatus,
				currencyTypeGbp);

		List<FxOrderEntity> notSettledRecordUsdList = fxRepository.fetchRecordsByStatusAndCurrencyType(notSettledRecordStatus,
				currencyTypeUsd);

		if (!CollectionUtils.isEmpty(notSettledRecordUsdList) && !CollectionUtils.isEmpty(notSettledRecordGbpList)) {
			System.out.println("Settle Transation " + new Date());

			List<FxOrderMatchStatusEntity> fxOrderMatchStatusEntityList = new ArrayList<>();

			for (FxOrderEntity gbpFxOrderEntity : notSettledRecordGbpList) {
				BigDecimal gbpAmtInUsdToBeSettled = null;

				if (gbpFxOrderEntity.getMatchedUsdAmt() != null) {
					gbpAmtInUsdToBeSettled = gbpFxOrderEntity.getUsdAmt().subtract(gbpFxOrderEntity.getMatchedUsdAmt());
				} else {
					gbpAmtInUsdToBeSettled = gbpFxOrderEntity.getUsdAmt();
				}

				BigDecimal usdAmtToBeSettled = null;

				for (FxOrderEntity usdFxOrderEntity : notSettledRecordUsdList) {

					if (usdFxOrderEntity.getUsdAmt().compareTo(usdFxOrderEntity.getMatchedUsdAmt()) == 1) {

						usdAmtToBeSettled = usdFxOrderEntity.getUsdAmt().subtract(usdFxOrderEntity.getMatchedUsdAmt());

						int result = gbpAmtInUsdToBeSettled.compareTo(usdAmtToBeSettled);

						if (result == 0) {
							// Both Amount are same //GBP is fully settled

							gbpFxOrderEntity
									.setMatchedUsdAmt(gbpFxOrderEntity.getMatchedUsdAmt().add(gbpAmtInUsdToBeSettled));
							gbpFxOrderEntity.setStatus(FxConstant.FULLY_MATCHED);

							usdFxOrderEntity
									.setMatchedUsdAmt(usdFxOrderEntity.getMatchedUsdAmt().add(usdAmtToBeSettled));
							usdFxOrderEntity.setStatus(FxConstant.FULLY_MATCHED);

							fxOrderMatchStatusEntityList.add(
									getFxOrderMatchStatusEntity(gbpFxOrderEntity.getId(), usdFxOrderEntity.getId()));

							break;

						} else if (result == 1) {
							// gbpAmtInUsdToBeSettled is greater
							gbpAmtInUsdToBeSettled = gbpAmtInUsdToBeSettled.subtract(usdAmtToBeSettled);

							gbpFxOrderEntity
									.setMatchedUsdAmt(gbpFxOrderEntity.getMatchedUsdAmt().add(usdAmtToBeSettled));
							gbpFxOrderEntity.setStatus(FxConstant.PARTIALLY_MATCHED);

							usdFxOrderEntity
									.setMatchedUsdAmt(usdFxOrderEntity.getMatchedUsdAmt().add(usdAmtToBeSettled));
							usdFxOrderEntity.setStatus(FxConstant.FULLY_MATCHED);

							fxOrderMatchStatusEntityList.add(
									getFxOrderMatchStatusEntity(gbpFxOrderEntity.getId(), usdFxOrderEntity.getId()));

						} else {
							// usdAmtToBeSettled is greater //GBP is fully settled
							gbpFxOrderEntity
									.setMatchedUsdAmt(gbpFxOrderEntity.getMatchedUsdAmt().add(gbpAmtInUsdToBeSettled));
							gbpFxOrderEntity.setStatus(FxConstant.FULLY_MATCHED);

							usdFxOrderEntity
									.setMatchedUsdAmt(usdFxOrderEntity.getMatchedUsdAmt().add(gbpAmtInUsdToBeSettled));
							usdFxOrderEntity.setStatus(FxConstant.PARTIALLY_MATCHED);
							fxOrderMatchStatusEntityList.add(
									getFxOrderMatchStatusEntity(gbpFxOrderEntity.getId(), usdFxOrderEntity.getId()));
							break;
						}
					}

				}

			}

			fxRepository.savefxOrderEntity(notSettledRecordGbpList);
			fxRepository.savefxOrderEntity(notSettledRecordUsdList);

			fxRepository.saveFxOrderMatchStatusEntity(fxOrderMatchStatusEntityList);
		}
	}

	private FxOrderMatchStatusEntity getFxOrderMatchStatusEntity(long gbpId, long usdId) {
		FxOrderMatchStatusEntity fxOrderMatchStatusEntity = new FxOrderMatchStatusEntity();
		FxOrderMatchStatusEntityPk fxOrderMatchStatusEntityPk = new FxOrderMatchStatusEntityPk();

		fxOrderMatchStatusEntityPk.setGbpId(gbpId);
		fxOrderMatchStatusEntityPk.setUsdId(usdId);
		fxOrderMatchStatusEntity.setKey(fxOrderMatchStatusEntityPk);

		return fxOrderMatchStatusEntity;
	}

}
