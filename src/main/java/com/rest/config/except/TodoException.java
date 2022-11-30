package com.rest.config.except;

public class TodoException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TodoException(String message){
		super(message);
	}
	
	public static String notFoundException(String id) {
		return "Todo with "+ id + " notFound!";
	}
	public static String alreadyExist() {
		return "Todo with given name already exist";
	}
}
