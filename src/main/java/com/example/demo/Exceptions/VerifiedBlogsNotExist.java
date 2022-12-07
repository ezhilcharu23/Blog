package com.example.demo.Exceptions;

public class VerifiedBlogsNotExist extends RuntimeException {

	public VerifiedBlogsNotExist(String msg) {
		super(msg);
	}
}
