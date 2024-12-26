package de.neuefische.springkanban.todo;

import de.neuefische.springkanban.ids.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepo;
    private final IdService idSvc;
    private final Deque<Command> undoStack = new ArrayDeque<>();;
    private final Deque<Command> redoStack = new ArrayDeque<>();;

    public List<Todo> getTodos() {
        return todoRepo.findAll();
    }

    public List<Todo> getTodosByStatus(Status status) {
        return todoRepo.findAllByStatus(status);
    }

    public Todo getTodoById(String id) {
        return todoRepo.findById(id).orElseThrow();
    }

    public String createTodo(TodoDTO todo) {
        Todo newTodo = new Todo(idSvc.generateID(), todo.description(), todo.status());
        todoRepo.save(newTodo);
        undoStack.push(new Command(newTodo, null));
        return newTodo.id();
    }

    public Todo editTodo(String id, TodoDTO todo) {
        Todo existingTodo = todoRepo.findById(id).orElseThrow();
        Todo updatedTodo = existingTodo
                .withDescription(todo.description())
                .withStatus(todo.status());
        todoRepo.save(updatedTodo);
        undoStack.push(new Command(updatedTodo, existingTodo));
        return updatedTodo;
    }

    public void deleteTodoById(String id) {
        Todo todo = todoRepo.findById(id).orElseThrow();
        undoStack.push(new Command(null, todo));
        todoRepo.delete(todo);
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            return;
        }
        Command command = undoStack.pop();
        if (command.newTodo() != null && command.oldTodo() == null) {
            todoRepo.delete(command.newTodo());
        }
        if (command.newTodo() == null && command.oldTodo() != null) {
            todoRepo.save(command.oldTodo());
        }
        if (command.newTodo() != null && command.oldTodo() != null) {
            todoRepo.save(command.oldTodo());
        }
        redoStack.push(command);
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            return;
        }
        Command command = redoStack.pop();
        if (command.newTodo() != null && command.oldTodo() == null) {
            todoRepo.save(command.newTodo());
        }
        if (command.newTodo() == null && command.oldTodo() != null) {
            todoRepo.delete(command.oldTodo());
        }
        if (command.newTodo() != null && command.oldTodo() != null) {
            todoRepo.save(command.newTodo());
        }
        undoStack.push(command);
    }

}
