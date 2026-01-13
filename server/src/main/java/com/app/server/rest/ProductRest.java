package com.app.server.rest;

import java.util.List;
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

import com.app.data.dto.MessageDto;
import com.app.data.dto.ProductDto;
import com.app.server.service.MessageService;
import com.app.server.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductRest {
	private ProductService service;

	public ProductRest(ProductService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ProductDto getProduct(@PathVariable final UUID id) {
		return service.getProduct(id);
	}

	@GetMapping("/all")
	public List<ProductDto> getAllProducts() {
		return service.getAllProducts();
	}

	@PostMapping("/create")
	public ProductDto createProduct(@RequestBody ProductDto dto) {
			return service.createProduct(dto);
	}

	@PutMapping("/update")
	public ProductDto updateProduct(@RequestBody ProductDto dto) {
		return service.updateProduct(dto);
	}

	@DeleteMapping("/delete")
	public void deleteProduct(@RequestParam final UUID id) {
		service.deleteProduct(id);
	}

}
