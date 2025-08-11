package com.dongdong.springbootstudy.auth.service.dto;

import java.io.Serializable;

import com.dongdong.springbootstudy.user.entity.User;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
	private String name;
	private String email;
	private String picture;

	private SessionUser(String name, String email, String picture) {
		this.name = name;
		this.email = email;
		this.picture = picture;
	}

	public static SessionUser fromEntity(User user){
		return new SessionUser(user.getName(), user.getEmail(), user.getPicture());
	}
}