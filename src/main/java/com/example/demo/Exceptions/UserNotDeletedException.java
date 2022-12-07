package com.example.demo.Exceptions;

public class UserNotDeletedException extends RuntimeException {

	public UserNotDeletedException(String msg) {
		super(msg);
	}
}