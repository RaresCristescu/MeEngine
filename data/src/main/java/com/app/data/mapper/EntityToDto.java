package com.app.data.mapper;

public interface EntityToDto<Entity, Dto> {
	Dto entityToDto(Entity e);
}
