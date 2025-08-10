package com.dongdong.springbootstudy.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dongdong.springbootstudy.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
