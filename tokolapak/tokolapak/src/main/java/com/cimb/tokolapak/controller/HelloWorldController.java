package com.cimb.tokolapak.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
@GetMapping("/hello")
public String helloWorld() {
	return "Helloooo";
}
@GetMapping("/hello/{name}")
public String helloName(@PathVariable() String name) {
	return "Hello" + name;
}
}
