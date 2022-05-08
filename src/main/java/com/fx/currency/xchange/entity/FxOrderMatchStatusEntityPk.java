package com.fx.currency.xchange.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@EqualsAndHashCode

public class FxOrderMatchStatusEntityPk implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name = "GBP_ID", nullable = false)
	private long gbpId;

	@Column(name = "USD_ID", nullable = false)
	private long usdId;


}
