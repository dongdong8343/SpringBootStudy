package com.dongdong.springbootstudy.post.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dongdong.springbootstudy.post.entity.Post;
import com.dongdong.springbootstudy.post.repository.PostRepository;
import com.dongdong.springbootstudy.post.service.dto.SavePost;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 포트 랜덤으로 해서 여러 테스트 시 충돌 방지
public class PostApiControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostRepository postRepository;

	@AfterEach
	public void tearDown() throws Exception {
		postRepository.deleteAll();
	}

	@Test
	public void Post_등록() throws Exception {
		// given
		String title = "title";
		String content = "content";
		SavePost.Request request = new SavePost.Request(title, content, "author"); // 테스트만을 위해서 모든 속성에 값을 넣는 생성자 만들어야 할까요?
		String url = "http://localhost:" + port + "/api/v1/posts";

		// when
		ResponseEntity<SavePost.Response> responseEntity = restTemplate.postForEntity(url, request, SavePost.Response.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody().getId()).isGreaterThan(0L);

		Post post = postRepository.findAll().get(0);
		assertThat(post.getTitle()).isEqualTo(title);
		assertThat(post.getContent()).isEqualTo(content);
	}
}
