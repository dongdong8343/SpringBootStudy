package com.dongdong.springbootstudy.post.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dongdong.springbootstudy.post.service.PostService;
import com.dongdong.springbootstudy.post.service.dto.SavePost;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostApiContoller {
	private final PostService postService;

	@PostMapping("/api/v1/posts")
	public SavePost.Response save(@RequestBody SavePost.Request request) {
		return postService.save(request);
	}
}
