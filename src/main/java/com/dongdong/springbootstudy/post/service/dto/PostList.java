package com.dongdong.springbootstudy.post.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.dongdong.springbootstudy.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class PostList {
	@Getter
	@AllArgsConstructor
	public static class PostItem {
		private Long id;
		private String title;
		private String author;
		private LocalDateTime updatedAt;
	}

	@Getter
	@AllArgsConstructor
	public static class Response {
		private List<PostItem> postItems;
	}

	public static Response toResponse(List<Post> posts) {
		return new Response(posts.stream()
			.map(post -> new PostItem(post.getId(), post.getTitle(), post.getAuthor(), post.getUpdatedAt()))
			.toList());
	}
}
