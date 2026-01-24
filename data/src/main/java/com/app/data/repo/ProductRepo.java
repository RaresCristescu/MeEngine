package com.app.data.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.data.entity.Cart;
import com.app.data.entity.CartProduct;
import com.app.data.entity.Inventory;
import com.app.data.entity.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepo extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
	
	@Modifying
	@Transactional
	@Query("update Product p set p.expires = CURRENT_TIMESTAMP where p.id = :id")
	void expireProductById(@Param("id") UUID id);
}
