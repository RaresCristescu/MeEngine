package com.app.server.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.data.dto.MessageDto;
import com.app.data.dto.ProductDto;
import com.app.data.entity.Inventory;
import com.app.data.entity.Message;
import com.app.data.entity.Product;
import com.app.data.mapper.MessageMapper;
import com.app.data.mapper.ProductMapper;
import com.app.data.repo.InventoryRepo;
import com.app.data.repo.MessageRepo;
import com.app.data.repo.ProductRepo;

@Service
public class InventoryService {
	private final InventoryRepo inventoryRepo;

	public InventoryService(InventoryRepo inventoryRepo) {
		this.inventoryRepo = inventoryRepo;
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

}
