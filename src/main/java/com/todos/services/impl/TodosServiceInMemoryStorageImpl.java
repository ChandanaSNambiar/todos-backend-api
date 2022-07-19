package com.todos.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.todos.models.Todo;
import com.todos.services.TodosService;

//@Service
public class TodosServiceInMemoryStorageImpl implements TodosService {
	// shared across all objects-static
	private static List<Todo> todos = new ArrayList<>();
	private static int todosCount = 3;

	// its executed before constructor

	static {
//		todos.add(new Todo(1, "Jack", "Learn Spring Boot", new Date(), false));
//		todos.add(new Todo(2, "Jack", "Learn Spring JPA", new Date(), false));
//		todos.add(new Todo(3, "Jill", "Climb the hill", new Date(), false));
	}

	@Override
	public List<Todo> getAllTodos() {
		return todos;
	}

	@Override
	public List<Todo> getTodosByUser(String user) {
		List<Todo> userTodos = new ArrayList<>();
		for (Todo todo : todos) {
			if (todo.getUser().equals(user)) {
				userTodos.add(todo);
			}
		}
		return userTodos;
	}

	@Override
	public Todo getTodoById(int id) {
		for (Todo todo : todos) {
			if (todo.getId() == id) {
				return todo;
			}
		}
		return null;
	}

	@Override
	public Todo addToDo(Todo todo) {
		todo.setId(++todosCount);
		todos.add(todo);
		return todo;
	}

	@Override
	public Todo updateTodo(String name, int id, Todo todo) {
		Todo existingTodo = this.getTodoById(id);
		if (existingTodo!= null && name.equals(existingTodo.getUser())) {
			existingTodo.setDescription(todo.getDescription());
			existingTodo.setTargetDate(todo.getTargetDate());
			existingTodo.setDone(todo.isDone());
			return existingTodo;
		} else
			return null;
	}

	@Override
	public boolean deleteTodo(int id) {
		for (int i = 0; i < todos.size(); i++) {
			if (todos.get(i).getId() == id) {
				todos.remove(i);
				return true;
			}
		}
		return false;
	}

}