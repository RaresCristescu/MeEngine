package com.app.data.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.app.data.entity.ProductStockView;

@Repository
public interface ProductStockViewRepo extends JpaRepository<ProductStockView, UUID>, JpaSpecificationExecutor<ProductStockView> {
}
