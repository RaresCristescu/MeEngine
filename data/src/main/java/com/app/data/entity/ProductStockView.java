package com.app.data.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "product_stock_view")
public class ProductStockView implements Serializable{
	private static final long serialVersionUID = 3865811797650566755L;

	@Id
	@Column(name = "product_id")
	private UUID productId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Long price;
	
	@Column(name = "in_stock")
	private Boolean inStock;
	
	@Column(name = "stock_quantity")
	private Integer stockQuantity;
	
}
