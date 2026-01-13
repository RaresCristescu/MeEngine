package com.app.data.mapper;

import org.mapstruct.Mapper;

import com.app.data.dto.MessageDto;
import com.app.data.dto.ProductDto;
import com.app.data.entity.Message;
import com.app.data.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductDto>{

}
