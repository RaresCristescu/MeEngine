package com.app.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactRest {

	@GetMapping("/contact")
	public String getContactInfo() {
		return "INTRA AICI CA SA BLA BLA";
	}

}
