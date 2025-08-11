package com.dongdong.springbootstudy.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.dongdong.springbootstudy.auth.service.CustomOAuth2UserService;
import com.dongdong.springbootstudy.user.entity.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // 스프링 시큐리티 설정들 활성화
@Configuration
public class SecurityConfig {
	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화
			.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // 해당 주소로 접근은 허용
				.requestMatchers("/api/v1/**").hasRole(Role.USER.name())
				.anyRequest().authenticated()) // 권한 있는 사용자만 접근 가능
			.logout(logout -> logout.logoutSuccessUrl("/")) // 로그아웃 성공 시 /로 이동
			.oauth2Login(oauth2Login -> oauth2Login.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService))); // 로그인 기능에 대한 설정, 소셜 로그인 성공시 사용자 정보 가져와서 추가로 진행할 기능 명시 가능
		return http.build();
	}

}