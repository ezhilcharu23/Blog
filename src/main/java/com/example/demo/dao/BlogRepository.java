package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Blog;
import com.example.demo.User;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

	public List<Blog> findBycreatedBy(User u);
	public List<Blog> findBystatus(String status);
	public List<Blog> findByOrderByDateDesc();
	
	@Query(value="select e from Blog e where e.createdBy.userId=:id")
	public List<Blog> getByCreaterId(String id);
	
}
