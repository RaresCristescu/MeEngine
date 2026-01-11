package com.app.server.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.data.dto.UserDetailsDto;
import com.app.data.dto.UserDto;
import com.app.server.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRest {
	private UserService userService;

	public UserRest(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/myAccountInfo/{id}")
	public UserDetailsDto getAccountDetails(@PathVariable final UUID id) {
		return userService.getAccountDetails(id);
	}

	@PostMapping("/register")
	public void registerUser(@RequestBody UserDto userDto) {
		userService.registerUser(userDto);
	}

}
