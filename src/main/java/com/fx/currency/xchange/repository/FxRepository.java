package com.fx.currency.xchange.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.fx.currency.xchange.entity.FxOrderEntity;
import com.fx.currency.xchange.entity.FxOrderMatchStatusEntity;
import com.fx.currency.xchange.exception.ResourceNotFoundException;
import com.fx.currency.xchange.util.FxConstant;
import com.sun.istack.NotNull;

@Repository
public class FxRepository {

	@Autowired
	private EntityManager em;

	public FxOrderEntity fetchOrderById(@NotNull String orderId) {

		TypedQuery<FxOrderEntity> query = em.createNamedQuery("FxOrderEntity.getOrderByOrderId", FxOrderEntity.class);
		query.setParameter("orderid", Long.valueOf(orderId).longValue());
		List<FxOrderEntity> orderList = query.getResultList();

		if (CollectionUtils.isEmpty(orderList)) {
			throw new ResourceNotFoundException("Record not found with id : " + orderId);
		}

		return orderList.get(0);

	}

	public List<FxOrderEntity> fetchRecordsByStatusAndCurrencyType(@NotNull List<String> statusList,
			@NotNull List<String> currencyTypeList) {

		TypedQuery<FxOrderEntity> query = em.createNamedQuery("FxOrderEntity.findByStatus", FxOrderEntity.class);
		query.setParameter("statuslst", statusList);
		query.setParameter("currencylst", currencyTypeList);
		return query.getResultList();

	}

	public List<FxOrderEntity> fetchAllRecords() {
		TypedQuery<FxOrderEntity> query = em.createNamedQuery("FxOrderEntity.getAllOrders", FxOrderEntity.class);
		return query.getResultList();
	}

	public void savefxOrderEntity(List<FxOrderEntity> fxOrderEntityList) {
		for (FxOrderEntity fxOrderEntity : fxOrderEntityList) {
			em.merge(fxOrderEntity);
		}
	}

	public void saveFxOrderMatchStatusEntity(List<FxOrderMatchStatusEntity> fxOrderMatchStatusEntityList) {
		for (FxOrderMatchStatusEntity fxOrderMatchStatusEntity : fxOrderMatchStatusEntityList) {
			em.merge(fxOrderMatchStatusEntity);
		}

	}

	public List<FxOrderMatchStatusEntity> fetchBreadDownInformation(@NotNull long id, @NotNull String currency) {

		if (FxConstant.GBP.equals(currency)) {
			TypedQuery<FxOrderMatchStatusEntity> query = em.createNamedQuery("FxOrderMatchStatusEntity.findOrderByGbp",
					FxOrderMatchStatusEntity.class);
			query.setParameter("gbpid", id);
			return query.getResultList();
		} else if (FxConstant.USD.equals(currency)) {
			TypedQuery<FxOrderMatchStatusEntity> query = em.createNamedQuery("FxOrderMatchStatusEntity.findOrderByUsd",
					FxOrderMatchStatusEntity.class);
			query.setParameter("usdid", id);
			return query.getResultList();
		} else {
			return null;
		}

	}

	public FxOrderEntity createfxOrder(FxOrderEntity fxOrderEntity) {
		return em.merge(fxOrderEntity);
	}

}
