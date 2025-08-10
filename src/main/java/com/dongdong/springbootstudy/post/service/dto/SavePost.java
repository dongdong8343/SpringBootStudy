package com.dongdong.springbootstudy.post.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SavePost {
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		private String title;
		private String content;
		private String author;
	}

	@Getter
	public static class Response {
		private Long id;

		private Response(Long id) {
			this.id = id;
		}
	}

	public static Response toResponse(Long id) {
		return new Response(id);
	}
}
