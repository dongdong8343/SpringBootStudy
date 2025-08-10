package com.dongdong.springbootstudy.post.provider;

import org.springframework.stereotype.Component;

import com.dongdong.springbootstudy.post.entity.Post;
import com.dongdong.springbootstudy.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PostProvider {
	private final PostRepository postRepository;

	public Post findById(Long postId) {
		return postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
	}

	public Post save(Post post) {
		return postRepository.save(post);
	}
}
