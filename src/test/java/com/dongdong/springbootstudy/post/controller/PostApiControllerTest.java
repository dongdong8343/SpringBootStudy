package com.dongdong.springbootstudy.post.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dongdong.springbootstudy.post.entity.Post;
import com.dongdong.springbootstudy.post.repository.PostRepository;
import com.dongdong.springbootstudy.post.service.dto.SavePost;
import com.dongdong.springbootstudy.post.service.dto.UpdatePost;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 포트 랜덤으로 해서 여러 테스트 시 충돌 방지
public class PostApiControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.build();
	}

	@AfterEach
	public void tearDown() throws Exception {
		postRepository.deleteAll();
	}

	@Test
	@WithMockUser(roles="USER") // 인증된 모듸 사용자 만들어 사용
	public void Post_등록() throws Exception {
		// given
		String title = "title";
		String content = "content";
		SavePost.Request request = new SavePost.Request(title, content, "author"); // 테스트만을 위해서 모든 속성에 값을 넣는 생성자 만들어야 할까요?
		String url = "http://localhost:" + port + "/api/v1/posts";

		// when
		mvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
			.andExpect(status().isOk());

		// then
		Post post = postRepository.findAll().get(0);
		assertThat(post.getTitle()).isEqualTo(title);
		assertThat(post.getContent()).isEqualTo(content);
	}

	@Test
	@WithMockUser(roles="USER")
	public void Post_수정() throws Exception {
		// given
		Post savedPost = postRepository.save(Post.create("title", "content", "author"));

		Long updateId = savedPost.getId();
		String expectedTitle = "title2";
		String expectedContent = "content2";

		UpdatePost.Request request = new UpdatePost.Request(expectedTitle, expectedContent);

		String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

		HttpEntity<UpdatePost.Request> requestHttpEntity = new HttpEntity<>(request);

		// when
		mvc.perform(patch(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
			.andExpect(status().isOk());

		// then
		List<Post> posts = postRepository.findAll();
		Post post = posts.get(0);

		assertThat(post.getTitle()).isEqualTo(expectedTitle);
		assertThat(post.getContent()).isEqualTo(expectedContent);
	}

	@Test
	public void BaseTimeEntity_등록() {
		// given
		LocalDateTime now = LocalDateTime.of(2025, 8, 10, 0, 0, 0);
		postRepository.save(Post.create("title", "content", "author"));

		// when
		List<Post> posts = postRepository.findAll();

		// then
		Post post = posts.get(0);

		System.out.println(">>>>>>>>>>>> createDate="+post.getCreatedAt()+", modifiedDate="+post.getUpdatedAt());

		assertThat(post.getCreatedAt()).isAfter(now);
		assertThat(post.getUpdatedAt()).isAfter(now);
	}
}
