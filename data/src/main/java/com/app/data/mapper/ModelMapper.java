package com.app.data.mapper;

public interface ModelMapper<Entity, Dto> extends EntityToDto<Entity, Dto>, DtoToEntity<Dto, Entity>{

}
