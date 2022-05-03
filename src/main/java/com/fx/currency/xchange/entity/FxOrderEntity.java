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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getUsdAmt() {
		return usdAmt;
	}

	public void setUsdAmt(BigDecimal usdAmt) {
		this.usdAmt = usdAmt;
	}

	public BigDecimal getMatchedUsdAmt() {
		return matchedUsdAmt;
	}

	public void setMatchedUsdAmt(BigDecimal matchedUsdAmt) {
		this.matchedUsdAmt = matchedUsdAmt;
	}

}
