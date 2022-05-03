package com.fx.currency.xchange.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "FX_ORDER_MATCH_STATUS")
@Entity(name = "fxordermatchstatus")
@NamedQueries({
		@NamedQuery(name = "FxOrderMatchStatusEntity.findOrderByGbp", query = "select a from fxordermatchstatus a "
				+ "where a.key.gbpId in (:gbpid)"),
		@NamedQuery(name = "FxOrderMatchStatusEntity.findOrderByUsd", query = "select a from fxordermatchstatus a "
				+ "where a.key.usdId in (:usdid)") })
public class FxOrderMatchStatusEntity implements Serializable {

	private static final long serialVersionUID = -5634030457935530467L;

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(column = @Column(name = "GBP_ID"), name = "key.gbpId"),
			@AttributeOverride(column = @Column(name = "USD_ID"), name = "key.usdId") })
	private FxOrderMatchStatusEntityPk key;

	public FxOrderMatchStatusEntityPk getKey() {
		return key;
	}

	public void setKey(FxOrderMatchStatusEntityPk key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FxOrderMatchStatusEntity))
			return false;
		FxOrderMatchStatusEntity other = (FxOrderMatchStatusEntity) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

//	@Id
//	@Column(name = "GBP_ID", nullable = false)
//	private long gbpId;
//
//	@Column(name = "USD_ID", nullable = false)
//	private long usdId;
//
//	public long getGbpId() {
//		return gbpId;
//	}
//
//	public void setGbpId(long gbpId) {
//		this.gbpId = gbpId;
//	}
//
//	public long getUsdId() {
//		return usdId;
//	}
//
//	public void setUsdId(long usdId) {
//		this.usdId = usdId;
//	}

}
