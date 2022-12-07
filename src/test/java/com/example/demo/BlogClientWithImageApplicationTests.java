package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.BlogRepository;
import com.example.demo.dao.CategoryRepository;
import com.example.demo.dao.CommentRepository;
import com.example.demo.dao.UserRepository;

@SpringBootTest
class BlogClientWithImageApplicationTests {

	void contextLoads() {
	}
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CategoryRepository catRepo;
	
	@Autowired
	BlogRepository blogRepo;
	
	@Autowired
	CommentRepository comRepo;
	
	
	public void testReadAll() {
		List<Blog> list=blogRepo.findAll();
		assertThat(list).size().isGreaterThan(0);
	}
	
	public void testCreate() {
		
		User u=new User();
		u.setName("Alex");
		u.setPassword("123");
		u.setUserId("abc");
		u.setEmail("abc@gmail.com");
		u.setRole("user");	
		u.setFollowing(Arrays.asList(u));
	Category c=new Category();
		c.setCName("Education");
		catRepo.save(c);
		Blog b=new Blog();
		b.setCategory(c);
		b.setCreatedBy(u);
		b.setDate(new Date());
	//	
		b.setTitle("Maths");
		b.setContent("Maths is a subject which is not only applicable in our academics but also in real life. "
				+ "Making kids love this subject is a challenge, especially for parents. "
				+ "It takes a lot of brainpower to master Maths and this can be tough for kids. "
				+ "Some students may find Maths hard to learn. ");
		b.setStatus("pending");
		blogRepo.save(b);
		Comment comment=new Comment();
		comment.setBlog(b);
		comment.setMessage("Cool");
		comment.setNoOfLikes(2);
		comment.setUser(u);
		comRepo.save(comment);
	
		b.setComments(Arrays.asList(comment));
		userRepo.save(u);
		assertNotNull(userRepo.findById("abc").get());
		
		
		
	}











}
