package com.fx.currency.xchange.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter


@Entity(name = "fxorder")
@Table(name = "FX_ORDER")
@NamedQueries({
		@NamedQuery(name = "FxOrderEntity.findByStatus", query = "select a from fxorder a "
				+ "where a.status in (:statuslst) and a.currency in (:currencylst) order by a.id"),
		@NamedQuery(name = "FxOrderEntity.getAllOrders", query = "select a from fxorder a "
				+ "order by a.id"),
		@NamedQuery(name = "FxOrderEntity.getOrderByOrderId", query = "select a from fxorder a "
				+ "where a.id in (:orderid) order by a.id")})
public class FxOrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private long id;

	@Column(name = "ORIGINAL_AMT", nullable = false, columnDefinition = "NUMBER(14,2)")
	private BigDecimal amount;

	@Column(name = "USD_AMT", nullable = true, columnDefinition = "NUMBER(14,2)")
	private BigDecimal usdAmt;

	@Column(name = "MATCHED_AMT", nullable = true, columnDefinition = "NUMBER(14,2)")
	private BigDecimal matchedUsdAmt;

	@Column(name = "CURRENCY", nullable = false, length = 3)
	private String currency;

	@Column(name = "STATUS", nullable = true, length = 100)
	private String status;


}
