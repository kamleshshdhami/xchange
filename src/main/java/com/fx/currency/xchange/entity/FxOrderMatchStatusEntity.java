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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@EqualsAndHashCode

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


}
