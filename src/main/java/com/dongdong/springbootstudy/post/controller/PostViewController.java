package com.dongdong.springbootstudy.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dongdong.springbootstudy.auth.annotation.LoginUser;
import com.dongdong.springbootstudy.auth.service.dto.SessionUser;
import com.dongdong.springbootstudy.post.service.PostService;
import com.dongdong.springbootstudy.post.service.dto.ReadPost;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PostViewController {

	private final PostService postService;

	@GetMapping("/")
	public String index(Model model, @LoginUser SessionUser user){
		model.addAttribute("posts", postService.findAll().getPostItems());

		if (user != null) {
			model.addAttribute("userName", user.getName());
		}

		return "index";
	}

	@GetMapping("/posts/save")
	public String postSave() {
		return "post-save";
	}

	@GetMapping("/posts/update/{id}")
	public String postsUpdate(@PathVariable Long id, Model model) {
		System.out.println("id : " + id);
		ReadPost.Response response = postService.findById(id);
		model.addAttribute("post", response);

		return "post-update";
	}
}
