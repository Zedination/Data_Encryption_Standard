package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping(path = {"/","/index"})
	public String index() {
		return "index";
	}
	@GetMapping("/des")
	public String des_page() {
		return "des";
	}
	@GetMapping("/aes")
	public String aes_page() {
		return "aes";
	}
	@GetMapping("/rsa")
	public String rsa_page() {
		return "rsa";
	}
	@GetMapping("/rsa-thucong")
	public String rsa_page_02() {
		return "rsa02";
	}
}
