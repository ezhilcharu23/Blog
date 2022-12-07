package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Blog;
import com.example.demo.Comment;
import com.example.demo.User;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	public List<Comment> findByuser(User u);
	public List<Comment> findByblog(Blog blog);
}
