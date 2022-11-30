package com.rest.service;

import java.util.List;

import com.rest.config.except.TodoException;
import com.rest.model.TodoDTO;

import jakarta.validation.ConstraintViolationException;

public interface TodoService {

	public void createTodo(TodoDTO todo)  throws ConstraintViolationException,TodoException;
	
	public List<TodoDTO> getAllTodos();
	
	public TodoDTO getSingleTodo(String id) throws TodoException;
	
	public void updateTodo(String id, TodoDTO todo) throws TodoException;
	
	public void deleteById(String id) throws TodoException;
}
