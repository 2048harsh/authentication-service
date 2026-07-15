package com.finrec.authentication.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/util")
public class PasswordEncodingController {

	private final PasswordEncoder encoder;

	public PasswordEncodingController(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	@GetMapping("/encode")
	public String encodePassword(@RequestParam String rawPassword) {
		return encoder.encode(rawPassword);
	}
}
