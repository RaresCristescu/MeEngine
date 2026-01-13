//package com.app.server.rest;
//
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.app.data.dto.MessageDto;
//import com.app.server.service.MessageService;
//
//@RestController
//@RequestMapping("/api/cart")
//public class CartRest {
//	private MessageService messageService;
//
//	public CartRest(MessageService messageService) {
//		this.messageService = messageService;
//	}
//
//	@GetMapping("/{id}")
//	public MessageDto getMessage(@PathVariable final UUID id) {
//		return messageService.getMessage(id);
//	}
//
//	@GetMapping("/all")
//	public List<MessageDto> getMessage() {
//		return messageService.getAllMessage();
//	}
//
//	@PostMapping("/create")
//	public MessageDto createMessage(@RequestBody MessageDto dto) {
//			return messageService.createMessage(dto);
//	}
//
//	@PutMapping("/update")
//	public MessageDto updateMessage(@RequestBody MessageDto dto) {
//		return messageService.updateMessage(dto);
//	}
//
//	@DeleteMapping("/delete")
//	public void login(@RequestParam final UUID id) {
//		messageService.deleteMessage(id);
//	}
//
//}
