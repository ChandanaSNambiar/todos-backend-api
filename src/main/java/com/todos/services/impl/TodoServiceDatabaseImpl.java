package com.todos.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.todos.models.Todo;
import com.todos.repositories.TodosRepository;
import com.todos.services.TodosService;

@Service
public class TodoServiceDatabaseImpl implements TodosService {

	@Autowired
	private TodosRepository todosRepository;

	@Override
	public List<Todo> getAllTodos() {
		// Use the repository to get all the todos
		return todosRepository.findAll(Sort.by(Sort.Direction.ASC,"user"));
	}

	@Override
	public List<Todo> getTodosByUser(String user) {
		return todosRepository.findByUser(user);
	}

	@Override
	public Todo getTodoById(int id) {
		//use the default findbyId method to get by id the todos
		Optional<Todo> todo = todosRepository.findById(id);
		if (todo.isPresent())
			return todo.get();
		return null;
	}

	@Override
	public Todo addToDo(Todo todo) {
		// Save the value to the db using repository save method
		return todosRepository.save(todo);
	}

	@Override
	public Todo updateTodo(String name, int id, Todo todo) {
		Optional<Todo> existingTodo = todosRepository.findById(id);
		if(existingTodo.isPresent() && existingTodo.get().getUser().equals(name)){
			Todo updatedTodo = existingTodo.get();
			updatedTodo.setDescription(todo.getDescription());
			updatedTodo.setTargetDate(todo.getTargetDate());
			updatedTodo.setDone(todo.isDone());
			//save to db
			todosRepository.save(updatedTodo);
			return updatedTodo;
		}
		return null;
	}

	@Override
	public boolean deleteTodo(int id) {
		Optional<Todo> existingTodo = todosRepository.findById(id);
		if(existingTodo.isPresent()) {
			todosRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
