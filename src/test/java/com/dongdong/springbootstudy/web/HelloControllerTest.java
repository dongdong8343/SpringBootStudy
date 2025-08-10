package com.dongdong.springbootstudy.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dongdong.springbootstudy.web.dto.Hello;

@WebMvcTest(controllers = HelloController.class) // Web(Spring MVC)에 집중할 수 있는 어노테이션. 선언할 경우 @Controller, @ControllerAdvice등을 사용할 수 있다.
public class HelloControllerTest {
	@Autowired
	private MockMvc mvc; // api 테스트 시 사용

	@Test
	public void hello_리턴() throws Exception {
		String hello = "hello";

		mvc.perform(get("/hello"))
			.andExpect(status().isOk())
			.andExpect(content().string(hello));
	}

	@Test
	public void 롬복_기능_테스트() {
		// given
		String name = "test";
		int amount = 1000;

		// when
		Hello.Response dto = new Hello.Response(name, amount);

		// then
		assertThat(dto.getName()).isEqualTo(name);
		assertThat(dto.getAmount()).isEqualTo(amount);
	}

	@Test
	public void helloDto_리턴() throws Exception {
		String name = "hello";
		int amount = 1000;

		mvc.perform(
				get("/hello/dto")
					.param("name", name)
					.param("amount", String.valueOf(amount))
			).andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(name))
			.andExpect(jsonPath("$.amount").value(amount));
	}
}
