package com.dongdong.springbootstudy.auth.service.dto;

import java.util.Map;

import com.dongdong.springbootstudy.user.entity.Role;
import com.dongdong.springbootstudy.user.entity.User;

import lombok.Getter;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;

	private OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
		if ("naver".equals(registrationId)) {
			return ofNaver("id", attributes);
		}

		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");

		return new OAuthAttributes(response, userNameAttributeName, (String) response.get("name"), (String) response.get("email"), (String) response.get("picture"));
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
		return new OAuthAttributes(attributes, userNameAttributeName, (String) attributes.get("name"), (String) attributes.get("email"), (String) attributes.get("picture"));
	}

	public User toEntity(){
		return User.create(name, email, picture, Role.USER);
	}
}