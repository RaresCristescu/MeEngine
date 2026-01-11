package com.app.data.mapper;

import org.mapstruct.Mapper;

import com.app.data.dto.MessageDto;
import com.app.data.entity.Message;

@Mapper
public interface MessageMapper extends ModelMapper<Message, MessageDto>{

}
