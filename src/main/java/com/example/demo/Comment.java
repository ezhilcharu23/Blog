package com.example.demo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String message;
	
	@ManyToOne
	@JsonIgnoreProperties({"createdBy","comments","file"})	
	private Blog blog;
	
	@OneToOne
	@JsonIgnoreProperties({"blogs","savedBlogs"})
	private User user;
	
	private int noOfLikes;
	
	private Date date;
}
