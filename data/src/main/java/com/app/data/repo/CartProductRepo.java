package com.app.data.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.app.data.entity.Cart;
import com.app.data.entity.CartProduct;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct, UUID>, JpaSpecificationExecutor<CartProduct> {
}
