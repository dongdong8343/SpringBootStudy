package com.dongdong.springbootstudy.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongdong.springbootstudy.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
