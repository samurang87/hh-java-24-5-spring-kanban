package de.neuefische.springkanban;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepo;
    private final IdService idSvc;

    public List<Todo> getTodos() {
        return todoRepo.findAll();
    }

    public List<Todo> getTodosByStatus(Status status) {
        return todoRepo.findAllByStatus(status);
    }

    public Todo getTodoById(String id) {
        return todoRepo.findById(id).orElseThrow();
    }

    public String createTodo(Todo todo) {
        Todo newTodo = new Todo(idSvc.generateID(), todo.description(), todo.status());
        todoRepo.save(newTodo);
        return newTodo.id();
    }

    public Todo editTodo(String id, Todo todo) {
        Todo existingTodo = todoRepo.findById(id).orElseThrow();
        Todo updatedTodo = existingTodo
                .withDescription(todo.description())
                .withStatus(todo.status());
        todoRepo.save(updatedTodo);
        return updatedTodo;
    }

    public void deleteTodoById(String id) {
        Todo todo = todoRepo.findById(id).orElseThrow();
        todoRepo.delete(todo);
    }

}
