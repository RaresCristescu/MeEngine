package com.app.data.mapper;

import org.mapstruct.MappingTarget;

public interface DtoToEntity<Dto, Entity> {
	Entity dtoToEntity(Dto dto);
	
	void updateEntityFromDto(Dto dto, @MappingTarget Entity entity);
}
