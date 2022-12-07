package com.example.demo.Exceptions;

public class CategoryNotFoundException extends RuntimeException {
	
	public CategoryNotFoundException(String msg) {
		super(msg);
	}

}
