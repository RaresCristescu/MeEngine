package com.app.server.service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.data.dto.ProductChangePriceDto;
import com.app.data.dto.ProductCreateDto;
import com.app.data.dto.ProductResponseDto;
import com.app.data.dto.ProductUpdateDto;
import com.app.data.dto.ProductUpdateStockDto;
import com.app.data.entity.Inventory;
import com.app.data.entity.Product;
import com.app.data.entity.ProductStockView;
import com.app.data.entity.ProductStockView_;
import com.app.data.enums.RoleEnum;
import com.app.data.mapper.ProductMapper;
import com.app.data.mapper.ProductWithStockMapper;
import com.app.data.pagination.PaginatedRequest;
import com.app.data.pagination.PaginatedResponse;
import com.app.data.repo.ProductRepo;
import com.app.data.repo.ProductStockViewRepo;
import com.app.server.utils.PaginationSpecificationUtils;

import jakarta.persistence.metamodel.SingularAttribute;

@Service
public class ProductService {

	private final ProductRepo productRepo;
	private final ProductStockViewRepo productStockRepo;
	
	private final InventoryService inventoryService;
	private final SecurityService securityService;
	
	private final ProductMapper productMapper;
	private final ProductWithStockMapper productWithStockMapper;
	
	

	public ProductService(ProductRepo productRepo, ProductStockViewRepo productStockRepo,
			InventoryService inventoryService, SecurityService securityService, ProductMapper productMapper,
			ProductWithStockMapper productWithStockMapper) {
		this.productRepo = productRepo;
		this.productStockRepo = productStockRepo;
		this.inventoryService = inventoryService;
		this.securityService = securityService;
		this.productMapper = productMapper;
		this.productWithStockMapper = productWithStockMapper;
	}

	private ProductResponseDto setStockInfo(final UUID id, Product p) {
		ProductResponseDto pd = productMapper.entityToDto(p);
		pd.setInStock(inventoryService.isInstock(id));
		pd.setStockQuantity(inventoryService.getProductAvailableStock(p.getId()));
		return pd;
	}

	public ProductResponseDto getProduct(final UUID id) {
		Product p = productRepo.findById(id).orElseThrow(NoSuchElementException::new);
		ProductResponseDto pd = setStockInfo(id, p);
		return pd;
	}

	public ProductResponseDto createProduct(final ProductCreateDto dto) {
		if(!securityService.hasRole(RoleEnum.ROLE_ADMIN)) {
			throw new RuntimeException("User does not have privileges to create a product");
		}
		
		final Product p = new Product();
		p.setName(dto.getName());
		p.setDescription(dto.getDescription());
		p.setPrice(dto.getPrice());
		p.setIsActive(true);
		final Product dbP = productRepo.save(p);

		Inventory inventory = new Inventory();
		inventory.setTotalQuantity(dto.getStockQuantity());
		inventory.setReservedQuantity(0);
		inventory.setProduct(dbP);
		
		inventoryService.save(inventory);
		
		return productMapper.entityToDto(dbP);
	}

	public ProductResponseDto updateProduct(final ProductUpdateDto dto) {
		if(!securityService.hasRole(RoleEnum.ROLE_ADMIN)) {
			throw new RuntimeException("User does not have privileges to create a product");
		}
		Product p = productRepo.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
		if (dto.getName()!= null) {
			p.setName(dto.getName());
		}
		if (dto.getDescription() != null) {
			p.setDescription(dto.getDescription());
		}
		if (dto.getPrice() != null) {
			p.setPrice(dto.getPrice());
		}
		
		p = productRepo.save(p);
		
		return getProduct(dto.getId());
	}

	public ProductResponseDto changePrice(final ProductChangePriceDto dto) {
		if(!securityService.hasRole(RoleEnum.ROLE_ADMIN)) {
			throw new RuntimeException("User does not have privileges to create a product");
		}
		Product p = productRepo.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
		if (dto.getPrice() != null) {
			p.setPrice(dto.getPrice());
		}
		
		p = productRepo.save(p);
		
		return getProduct(dto.getId());
	}

	public ProductResponseDto updateStock(final ProductUpdateStockDto dto) {
		if(!securityService.hasRole(RoleEnum.ROLE_ADMIN)) {
			throw new RuntimeException("User does not have privileges to create a product");
		}
		inventoryService.updateTotalStock(dto.getId(), dto.getStockQuantity());
		return getProduct(dto.getId());
	}

	public void expireProduct(final UUID id) {
		if(!securityService.hasRole(RoleEnum.ROLE_ADMIN)) {
			throw new RuntimeException("User does not have privileges to create a product");
		}
		productRepo.expireProductById(id);
	}

	public void deleteProduct(final UUID id) {
		if(!securityService.hasRole(RoleEnum.ROLE_ADMIN)) {
			throw new RuntimeException("User does not have privileges to create a product");
		}
		productRepo.deleteById(id);
	}

	public PaginatedResponse<ProductResponseDto> getPaginated(PaginatedRequest request) {
		Specification<ProductStockView> spec = PaginationSpecificationUtils.buildSpecification(request.getFilters(),
				PRODUCT_STOCK_FILTERS);
		Page<ProductResponseDto> page = productStockRepo.findAll(spec, request.toPageable())
				.map(productWithStockMapper::entityToDto);
		return new PaginatedResponse<>(page);

	}

	private static final Map<String, SingularAttribute<ProductStockView, ?>> PRODUCT_STOCK_FILTERS = Map.of(
	            "productId", ProductStockView_.productId,
	            "price", ProductStockView_.price,
	            "description", ProductStockView_.description,
	            "stockQuantity", ProductStockView_.stockQuantity,
	            "inStock", ProductStockView_.inStock
	);
}
