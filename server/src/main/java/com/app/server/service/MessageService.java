package com.app.server.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.data.dto.MessageDto;
import com.app.data.entity.Message;
import com.app.data.mapper.MessageMapper;
import com.app.data.repo.MessageRepo;

@Service
public class MessageService {

	private final MessageRepo repo;
	private final MessageMapper messageMapper;

	public MessageService(MessageRepo repo, MessageMapper messageMapper) {
		this.repo = repo;
		this.messageMapper = messageMapper;
	}
	
	public MessageDto getMessage(final UUID id) {
		Message m = repo.findById(id).orElseThrow(NoSuchElementException::new);
		MessageDto md = messageMapper.entityToDto(m);
		return md;
	}
	
	public List<MessageDto> getAllMessage() {
		List<Message> mList = repo.findAll();
		List<MessageDto> md = mList.stream()
					.map(messageMapper::entityToDto)
					.collect(Collectors.toList());
		return md;
	}
	
	public MessageDto createMessage(final MessageDto dto) {
		Message m = messageMapper.dtoToEntity(dto);
		m =repo.save(m);
		return messageMapper.entityToDto(m);
	}
	
	public MessageDto updateMessage(final MessageDto dto) {
		Message m = repo.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
		m.setDescription(dto.getDescription());
		m = repo.save(m);
		return messageMapper.entityToDto(m);
	}
	
	public void deleteMessage(final UUID id) {
		repo.deleteById(id);
	}

	

}
