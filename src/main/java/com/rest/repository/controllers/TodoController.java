package com.rest.repository.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.config.except.TodoException;
import com.rest.model.TodoDTO;
import com.rest.repository.TodoRepository;
import com.rest.service.TodoService;

import jakarta.validation.ConstraintViolationException;

@RestController
public class TodoController {
	
	@Autowired
	private TodoRepository repo;
	
	@Autowired
	private TodoService service;
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos(){
	 List<TodoDTO> todos=service.getAllTodos();
	 return new ResponseEntity<>(todos,todos.size()>0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
		try {
			service.createTodo(todo);
			return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);
		}catch(ConstraintViolationException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
		}catch(TodoException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id){
		try {
			return new ResponseEntity<>(service.getSingleTodo(id),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todo ){
		try {
			service.updateTodo(id, todo);
			return new ResponseEntity<>("update todo with id "+id,HttpStatus.OK);
		}catch(ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
		}catch(TodoException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id){
		try {
			service.deleteById(id);
			return new ResponseEntity<>("Successfully deleted with id "+id,HttpStatus.OK);
			
		}catch(TodoException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
}
