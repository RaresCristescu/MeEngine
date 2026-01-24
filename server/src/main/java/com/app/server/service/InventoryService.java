package com.app.server.service;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.data.entity.Inventory;
import com.app.data.entity.ProductStockView;
import com.app.data.repo.InventoryRepo;
import com.app.data.repo.ProductStockViewRepo;

@Service
public class InventoryService {
	private final InventoryRepo inventoryRepo;
	private final ProductStockViewRepo productStockViewRepo;

	public InventoryService(InventoryRepo inventoryRepo, ProductStockViewRepo productStockViewRepo) {
		this.inventoryRepo = inventoryRepo;
		this.productStockViewRepo = productStockViewRepo;
	}

	public Integer getProductAvailableStock(final UUID id) {
		return inventoryRepo.findAvailableStockById(id);
	}
	
	public Boolean isInstock(final UUID id) {
		return inventoryRepo.isInStock(id);
	}

	public void updateStock(final UUID id, Integer reservedStock) {
		Inventory inventory = inventoryRepo.findById(id).orElseThrow(NoSuchElementException::new);
		inventory.setReservedQuantity(inventory.getReservedQuantity()+reservedStock);
	}

	public void updateTotalStock(final UUID id, Integer totalStock) {
		Inventory inventory = inventoryRepo.findByProductId(id).orElseThrow(NoSuchElementException::new);
		inventory.setTotalQuantity(totalStock);
		inventoryRepo.save(inventory);
	}

	public Inventory save(final Inventory entity) {
		return inventoryRepo.save(entity);
		
	}

	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public Integer getProductAvailableStock2(final UUID id) {
		ProductStockView psView =productStockViewRepo.findById(id).orElseGet(null);
		if(psView==null) {
			return 0;
		}
		return psView.getStockQuantity();
	}
	
	public Boolean isInstock2(final UUID id) {
		ProductStockView psView =productStockViewRepo.findById(id).orElseGet(null);
		if(psView==null) {
			return false;
		}
		return psView.getInStock();
	}

}
