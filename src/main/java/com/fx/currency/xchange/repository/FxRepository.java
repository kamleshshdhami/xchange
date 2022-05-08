package com.fx.currency.xchange.repository;

import java.util.List;

import com.fx.currency.xchange.entity.FxOrderEntity;
import com.fx.currency.xchange.entity.FxOrderMatchStatusEntity;
import com.sun.istack.NotNull;

public interface FxRepository {

	public FxOrderEntity fetchOrderById(@NotNull String orderId);

	public List<FxOrderEntity> fetchRecordsByStatusAndCurrencyType(@NotNull List<String> statusList,
			@NotNull List<String> currencyTypeList);

	public List<FxOrderEntity> fetchAllRecords();

	public void savefxOrderEntity(List<FxOrderEntity> fxOrderEntityList);

	public void saveFxOrderMatchStatusEntity(List<FxOrderMatchStatusEntity> fxOrderMatchStatusEntityList);

	public List<FxOrderMatchStatusEntity> fetchBreadDownInformation(@NotNull long id, @NotNull String currency);

	public FxOrderEntity createfxOrder(FxOrderEntity fxOrderEntity);

}
