package com.app.server.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.data.dto.ProductChangePriceDto;
import com.app.data.dto.ProductCreateDto;
import com.app.data.dto.ProductResponseDto;
import com.app.data.dto.ProductUpdateDto;
import com.app.data.dto.ProductUpdateStockDto;
import com.app.data.pagination.PaginatedRequest;
import com.app.data.pagination.PaginatedResponse;
import com.app.server.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductRest {
	private ProductService service;

	public ProductRest(ProductService service) {
		this.service = service;
	}

	@GetMapping("/details/{id}")
	public ProductResponseDto getProduct(@PathVariable final UUID id) {
		return service.getProduct(id);
	}
	
	@PostMapping("/paginated")
	public PaginatedResponse<ProductResponseDto> getAllProducts2(@RequestBody PaginatedRequest request) {
		return service.getPaginated(request);
	}

	@PostMapping("/create")
	public ProductResponseDto createProduct(@RequestBody ProductCreateDto dto) {
			return service.createProduct(dto);
	}

	@PutMapping("/update")
	public ProductResponseDto updateProduct(@RequestBody ProductUpdateDto dto) {
		return service.updateProduct(dto);
	}
	
	@PutMapping("/expire")
	public void expireProduct(@RequestParam final UUID id) {
		service.expireProduct(id);
	}

	@DeleteMapping("/delete")
	public void deleteProduct(@RequestParam final UUID id) {
		service.deleteProduct(id);
	}

	@PutMapping("/change-price")
	public ProductResponseDto changePrice(@RequestBody ProductChangePriceDto dto) {
		return service.changePrice(dto);//TODO
	}

	@PutMapping("/update-stock")
	public ProductResponseDto updateStock(@RequestBody ProductUpdateStockDto dto) {
		return service.updateStock(dto);//TODO
	}

}
