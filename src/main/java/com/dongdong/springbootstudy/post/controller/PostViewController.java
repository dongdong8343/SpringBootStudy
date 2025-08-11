package com.dongdong.springbootstudy.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dongdong.springbootstudy.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PostViewController {

	private final PostService postService;

	@GetMapping("/")
	public String index(Model model){
		model.addAttribute("posts", postService.findAll().getPostItems());

		return "index";
	}

	@GetMapping("/posts/save")
	public String postSave() {
		return "post-save";
	}

}
