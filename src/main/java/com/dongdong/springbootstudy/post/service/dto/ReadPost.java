package com.dongdong.springbootstudy.post.service.dto;

import com.dongdong.springbootstudy.post.entity.Post;

public class ReadPost {
	public static class Response {
		private Long id;
		private String title;
		private String content;
		private String author;

		private Response(Long id, String title, String content, String author) {
			this.id = id;
			this.title = title;
			this.content = content;
			this.author = author;
		}
	}

	public static Response from(Post post) {
		return new Response(post.getId(), post.getTitle(), post.getContent(), post.getAuthor());
	}
}
