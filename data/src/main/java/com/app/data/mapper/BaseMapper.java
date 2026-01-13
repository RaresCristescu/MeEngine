package com.app.data.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface BaseMapper<E, D> {

    public abstract D entityToDto(E entity);

    public abstract E dtoToEntity(D dto);

    public abstract void updateEntityFromDto(D dto, @MappingTarget E entity);
}

