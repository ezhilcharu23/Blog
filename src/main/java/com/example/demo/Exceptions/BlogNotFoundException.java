package com.example.demo.Exceptions;

public class BlogNotFoundException extends RuntimeException{
	public BlogNotFoundException(String msg) {
		super(msg);
	}

}
