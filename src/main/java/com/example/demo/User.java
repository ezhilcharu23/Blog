package com.example.demo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	private String userId;
	private String password;
	private String role;
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Blog> blogs;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Blog> savedBlogs;
	
	private String email;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnoreProperties("data")
	private FileDB profilePic;
	
	@ManyToMany
	@JsonIgnoreProperties("following")
	@Column(name="following")
	private List<User> following;
	
	

}
