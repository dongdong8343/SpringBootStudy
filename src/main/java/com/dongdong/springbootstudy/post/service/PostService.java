package com.dongdong.springbootstudy.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongdong.springbootstudy.post.entity.Post;
import com.dongdong.springbootstudy.post.provider.PostProvider;
import com.dongdong.springbootstudy.post.service.dto.SavePost;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostProvider postProvider;

	@Transactional
	public SavePost.Response save(SavePost.Request request) {
		return SavePost.toResponse(
			postProvider.save(Post.create(request.getTitle(), request.getContent(), request.getAuthor()))
				.getId());
	}
}
