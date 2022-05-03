package com.fx.currency.xchange.entity;

import java.io.Serializable;

import javax.persistence.Column;


public class FxOrderMatchStatusEntityPk implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name = "GBP_ID", nullable = false)
	private long gbpId;

	@Column(name = "USD_ID", nullable = false)
	private long usdId;

	public long getGbpId() {
		return gbpId;
	}

	public void setGbpId(long gbpId) {
		this.gbpId = gbpId;
	}

	public long getUsdId() {
		return usdId;
	}

	public void setUsdId(long usdId) {
		this.usdId = usdId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (gbpId ^ (gbpId >>> 32));
		result = prime * result + (int) (usdId ^ (usdId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FxOrderMatchStatusEntityPk))
			return false;
		FxOrderMatchStatusEntityPk other = (FxOrderMatchStatusEntityPk) obj;
		if (gbpId != other.gbpId)
			return false;
		if (usdId != other.usdId)
			return false;
		return true;
	}
	
	

}
