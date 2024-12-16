package de.neuefische.springkanban;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    @GetMapping("/todo")
    public List<Todo> getTodos() {
        return new ArrayList<>();
    }

    @GetMapping("/todo/{id}")
    public Todo getTodoById(@PathVariable String id) {
        return new Todo(id, "Test task", Status.OPEN);
    }

    @PostMapping("/todo")
    public ResponseEntity<String> createTodo(@RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body("x");
    }

    @PutMapping("/todo")
    public Todo editTodo(@RequestBody Todo todo) {
        return todo;
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        return ResponseEntity.noContent().build();
    }
}
