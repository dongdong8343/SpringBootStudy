package com.dongdong.springbootstudy.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Hello {
	@Getter
	@RequiredArgsConstructor
	public static class Response {
		private final String name;
		private final int amount;
	}
}
