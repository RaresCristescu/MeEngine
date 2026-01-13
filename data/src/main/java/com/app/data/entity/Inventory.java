package com.app.data.entity;

import java.math.BigDecimal;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventories")
@AttributeOverride(
	    name = "id",
	    column = @Column(name = "product_id")
	)
public class Inventory extends CommonEntity {
	private static final long serialVersionUID = -5554115834257877633L;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "total_quantity")
	private Integer totalQuantity;

	@Column(name = "reserved_quantity")
	private Integer reservedQuantity;

}
