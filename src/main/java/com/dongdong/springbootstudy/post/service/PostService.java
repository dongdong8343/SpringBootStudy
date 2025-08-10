package com.dongdong.springbootstudy.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongdong.springbootstudy.post.entity.Post;
import com.dongdong.springbootstudy.post.provider.PostProvider;
import com.dongdong.springbootstudy.post.service.dto.ReadPost;
import com.dongdong.springbootstudy.post.service.dto.SavePost;
import com.dongdong.springbootstudy.post.service.dto.UpdatePost;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostProvider postProvider;

	@Transactional(readOnly = true)
	public ReadPost.Response findById(Long postId) {
		return ReadPost.from(postProvider.findById(postId));
	}

	@Transactional
	public SavePost.Response save(SavePost.Request request) {
		return SavePost.toResponse(
			postProvider.save(Post.create(request.getTitle(), request.getContent(), request.getAuthor()))
				.getId());
	}

	@Transactional
	public UpdatePost.Response update(Long postId, UpdatePost.Request request) {
		Post post = postProvider.findById(postId);

		post.update(request.getTitle(), request.getContent());

		return UpdatePost.toResponse(postId);
	}
}
