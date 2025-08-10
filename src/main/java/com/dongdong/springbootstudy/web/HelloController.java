package com.dongdong.springbootstudy.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dongdong.springbootstudy.dto.Hello;

@RestController // json 반환하는 컨트롤러로 만들어줌
public class HelloController {
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	@GetMapping("/hello/dto")
	public Hello.Response helloDto(@RequestParam("name") String name, @RequestParam int amount) {
		return new Hello.Response(name, amount);
	}
}
