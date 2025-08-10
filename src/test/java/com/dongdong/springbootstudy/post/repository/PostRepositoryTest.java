package com.dongdong.springbootstudy.post.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dongdong.springbootstudy.post.entity.Post;

@SpringBootTest // h2 DB 자동으로 실행해줌
public class PostRepositoryTest {
	@Autowired
	PostRepository postRepository;

	@AfterEach
	public void cleanup() {
		postRepository.deleteAll();
	}

	@Test
	public void 게시글저장_불러오기() {
		//given
		String title = "테스트 게시글";
		String content = "테스트 본문";

		postRepository.save(Post.create(title, content, "dongdong@naver.com"));

		// when
		List<Post> posts = postRepository.findAll();

		//then
		Post post = posts.get(0);
		assertThat(post.getTitle()).isEqualTo(title);
		assertThat(post.getContent()).isEqualTo(content);
	}
}
