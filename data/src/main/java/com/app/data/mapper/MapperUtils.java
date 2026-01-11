package com.app.data.mapper;

import org.mapstruct.factory.Mappers;

public class MapperUtils {
	
	public static <M extends ModelMapper <Entity, Dto>, Entity, Dto> ModelMapper<Entity, Dto> getMapper(Class<M> clazz){
		return Mappers.getMapper(clazz);
	}
	
	public static <M extends EntityToDto <Entity, Dto>, Entity, Dto> EntityToDto<Entity, Dto> getEntityToDtoMapper(Class<M> clazz){
		return Mappers.getMapper(clazz);
	}
	
	public static <M extends DtoToEntity <Dto, Entity>, Entity, Dto> DtoToEntity<Dto, Entity> getDtoToEntityMapper(Class<M> clazz){
		return Mappers.getMapper(clazz);
	}

}
