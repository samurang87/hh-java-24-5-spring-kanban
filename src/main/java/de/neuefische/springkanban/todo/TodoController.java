package de.neuefische.springkanban.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoSvc;

    @GetMapping("/todo")
    public List<Todo> getTodos(@RequestParam(required = false) Status status) {
        if (status != null) {
            return getTodoByStatus(status);
        }
        return todoSvc.getTodos();
    }

    private List<Todo> getTodoByStatus(Status status) {
        return todoSvc.getTodosByStatus(status);
    }

    @GetMapping("/todo/{id}")
    public Todo getTodoById(@PathVariable String id) {
        return todoSvc.getTodoById(id);
    }

    @PostMapping("/todo")
    public ResponseEntity<String> createTodo(@RequestBody Todo todo) {
        String id = todoSvc.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/todo/{id}")
    public Todo editTodo(@PathVariable String id, @RequestBody Todo todo) {
        return todoSvc.editTodo(id, todo);
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        todoSvc.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }
}
