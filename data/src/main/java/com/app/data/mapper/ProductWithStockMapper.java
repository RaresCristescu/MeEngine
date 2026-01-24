package com.app.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.data.dto.ProductResponseDto;
import com.app.data.entity.ProductStockView;

@Mapper(componentModel = "spring")
public interface ProductWithStockMapper extends BaseMapper<ProductStockView, ProductResponseDto>{
    @Mapping(source = "productId", target = "id")
    ProductResponseDto entityToDto(ProductStockView entity);

}
