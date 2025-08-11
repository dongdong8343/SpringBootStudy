package com.dongdong.springbootstudy.auth.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.dongdong.springbootstudy.auth.service.dto.OAuthAttributes;
import com.dongdong.springbootstudy.auth.service.dto.SessionUser;
import com.dongdong.springbootstudy.user.entity.User;
import com.dongdong.springbootstudy.user.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final UserRepository userRepository;
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		// 로그인 진행 중인 서비스 구분하는 id
		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		// OAuth2 로그인 진행 시 키가 되는 필드값
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

		// OAuth2User의 attribute를 담을 클래스
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

		User user = saveOrUpdate(attributes);

		// SessionUser - 세션에 사용자 정보 저장하기 위한 DTO
		httpSession.setAttribute("user", SessionUser.fromEntity(user));

		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
	}

	private User saveOrUpdate(OAuthAttributes attributes) {
		User user = userRepository.findByEmail(attributes.getEmail())
			.map(entity -> entity.update(attributes.getName(), attributes.getPicture())).orElse(attributes.toEntity());

		return userRepository.save(user);
	}
}