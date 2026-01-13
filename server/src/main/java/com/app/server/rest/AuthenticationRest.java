package com.app.server.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.data.dto.UserAuthenticationDto;
import com.app.server.service.SecurityService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthenticationRest {
	private final SecurityService securityService;

	public AuthenticationRest(SecurityService securityService) {
		this.securityService = securityService;
	}

	@PostMapping("/login")
	public String login(@RequestBody UserAuthenticationDto user) {
		String token = securityService.login(user.getUsername(), user.getPassword());
		return token;
	}
}
