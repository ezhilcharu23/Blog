package com.example.demo.Exceptions;

import lombok.Data;

@Data
public class Error {

	private String erCode;
	private String erMsg;
	
	public Error() {}
	
	public Error(String erCode,String erMsg) {
		this.erCode=erCode;
		this.erMsg=erMsg;
	}
	
}
