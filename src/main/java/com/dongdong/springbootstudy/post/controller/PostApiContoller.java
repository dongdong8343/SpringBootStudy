package com.dongdong.springbootstudy.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongdong.springbootstudy.post.service.PostService;
import com.dongdong.springbootstudy.post.service.dto.ReadPost;
import com.dongdong.springbootstudy.post.service.dto.SavePost;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostApiContoller {
	private final PostService postService;

	@GetMapping("/{postId}")
	public ReadPost.Response readPost(@PathVariable Long postId) {
		return postService.findById(postId);
	}

	@PostMapping
	public SavePost.Response save(@RequestBody SavePost.Request request) {
		return postService.save(request);
	}


}
