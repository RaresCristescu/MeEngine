package com.app.data.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.data.entity.Cart;
import com.app.data.entity.CartProduct;
import com.app.data.entity.Inventory;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, UUID>, JpaSpecificationExecutor<Inventory> {
	@Query("""
		    select case
		        when (COALESCE(i.totalQuantity, 0) - COALESCE(i.reservedQuantity, 0)) > 0
		        then true
		        else false
		    end
		    from Inventory i
		    where i.product.id = :id
		""")
	Boolean isInStock(UUID id);
	
	@Query("""
		    select COALESCE(i.totalQuantity, 0) - COALESCE(i.reservedQuantity, 0)
		    from Inventory i
		    where i.product.id = :id
		""")
	Integer findAvailableStockById(UUID id);
	
	Optional<Inventory> findByProductId(UUID productTd);
}
