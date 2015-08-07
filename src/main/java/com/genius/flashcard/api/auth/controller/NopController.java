package com.genius.flashcard.api.auth.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NopController {
	@RequestMapping(value = "/nop", method = RequestMethod.GET)
	public boolean nop() throws IOException {
		return true;
	}

	@RequestMapping(value = "/nop2", method = RequestMethod.GET)
	public boolean nop2() throws IOException {
		return true;
	}
}
