package com.dongdong.springbootstudy.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostViewController {

	@GetMapping("/")
	public String index(){
		return "index";
	}

	@GetMapping("/posts/save")
	public String postSave() {
		return "post-save";
	}

}
