package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Category {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	private String cName;
	
}
