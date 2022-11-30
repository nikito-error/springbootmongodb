package com.rest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.config.except.TodoException;
import com.rest.model.TodoDTO;
import com.rest.repository.TodoRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	private TodoRepository repo;
	
	@Override
	public void createTodo(TodoDTO todo) throws ConstraintViolationException,TodoException {
	Optional<TodoDTO> todoOptional=repo.findByTodo(todo.getTodo());
	if(todoOptional.isPresent()) {
		throw new TodoException(TodoException.alreadyExist());
	}else {
		todo.setCreatedAt(new Date(System.currentTimeMillis()));
		repo.save(todo);
	}
	}

	@Override
	public List<TodoDTO> getAllTodos() {
	 List<TodoDTO> list=  repo.findAll();
	 if(list.size()>0) {
		 return list;
	 }else {
		 return new ArrayList<TodoDTO>();
	 }
	}

	@Override
	public TodoDTO getSingleTodo(String id) throws TodoException {
		Optional<TodoDTO> optionalTodo=repo.findById(id);
		if(!optionalTodo.isPresent()) {
			throw new TodoException(TodoException.notFoundException(id));
		}else {
			return optionalTodo.get();
		}
	}

	@Override
	public void updateTodo(String id, TodoDTO todo) throws TodoException {
		Optional<TodoDTO> todoWithId=repo.findById(id);
		Optional<TodoDTO> todoWithName=repo.findByTodo(todo.getTodo());
		if(todoWithId.isPresent()) {
			if(todoWithName.isPresent() && !todoWithName.get().getId().equals(id)) {
				throw new TodoException(TodoException.alreadyExist());
			}
			TodoDTO updateTodo=todoWithId.get();
			updateTodo.setTodo(todo.getTodo());
			updateTodo.setDescription(todo.getDescription());
			updateTodo.setCompleted(todo.getCompleted());
			updateTodo.setUpdatedAt(new Date(System.currentTimeMillis()));
			
			repo.save(updateTodo);
		}else {
			throw new TodoException(TodoException.notFoundException(id));
		}
		
	}

	@Override
	public void deleteById(String id) throws TodoException {
		Optional<TodoDTO> optionalTodo=repo.findById(id);
		if(!optionalTodo.isPresent()){
			throw new TodoException(TodoException.notFoundException(id));
		}else {
			repo.deleteById(id);
		}
		
	}

}
