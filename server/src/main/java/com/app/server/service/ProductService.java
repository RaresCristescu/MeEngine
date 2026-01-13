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
public class ProductService {

	private final ProductRepo productRepo;
	private final InventoryService inventoryService;
	private final ProductMapper productMapper;

	public ProductService(ProductRepo productRepo, InventoryService inventoryService, ProductMapper productMapper) {
		this.productRepo = productRepo;
		this.inventoryService = inventoryService;
		this.productMapper = productMapper;
	}

	private ProductDto setStockInfo(final UUID id, Product p) {
		ProductDto pd = productMapper.entityToDto(p);
		pd.setInStock(inventoryService.isInstock(id));
		pd.setStockQuantity(inventoryService.getProductAvailableStock(p.getId()));
		return pd;
	}

	public ProductDto getProduct(final UUID id) {
		Product p = productRepo.findById(id).orElseThrow(NoSuchElementException::new);
		ProductDto pd = setStockInfo(id, p);
		return pd;
	}

	public List<ProductDto> getAllProducts() {
		List<Product> pList = productRepo.findAll();
		List<ProductDto> pDto = pList.stream().map(p -> {
			return setStockInfo(p.getId(), p);
		}).collect(Collectors.toList());
		return pDto;

	}

	public ProductDto createProduct(final ProductDto dto) {
		Product p = productMapper.dtoToEntity(dto);
		p = productRepo.save(p);
		Inventory i = p.getInventory();
		i.setTotalQuantity(dto.getStockQuantity());
		return productMapper.entityToDto(p);
	}

	public ProductDto updateProduct(final ProductDto dto) {
		Product p = productRepo.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
		if (dto.getDescription() != null) {
			p.setDescription(dto.getDescription());
		}
		if (dto.getPrice() != null) {
			p.setPrice(dto.getPrice());
		}
		p = productRepo.save(p);
		return productMapper.entityToDto(p);
	}

	public void deleteProduct(final UUID id) {
		productRepo.deleteById(id);
	}

}
