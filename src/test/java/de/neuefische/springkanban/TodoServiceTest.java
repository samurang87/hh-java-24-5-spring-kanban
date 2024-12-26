package de.neuefische.springkanban;

import de.neuefische.springkanban.ids.IdService;
import de.neuefische.springkanban.todo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class TodoServiceTest {

    private final IdService ids = mock(IdService.class);
    private final TodoRepository todoRepo = mock(TodoRepository.class);

    private final List<Todo> mockData = List.of(
            new Todo("1", "Buy milk", Status.OPEN),
            new Todo("2", "Do laundry", Status.IN_PROGRESS),
            new Todo("3", "Call brother", Status.DONE)
    );

    @Test
    void getTodos_returnsAllTodos() {

        TodoService ts = new TodoService(todoRepo, ids);
        when(todoRepo.findAll()).thenReturn(mockData);
        Assertions.assertEquals(mockData, ts.getTodos());

    }

    @Test
    void getTodosByStatus_returnsTodosByStatus() {
        TodoService ts = new TodoService(todoRepo, ids);
        List<Todo> openTodos = mockData.stream().filter(todo -> todo.status().equals(Status.OPEN)).toList();
        when(todoRepo.findAllByStatus(Status.OPEN)).thenReturn(openTodos);
        Assertions.assertEquals(openTodos, ts.getTodosByStatus(Status.OPEN));
    }

    @Test
    void getTodoById_returnsTodoById() {
        TodoService ts = new TodoService(todoRepo, ids);
        when(todoRepo.findById("2")).thenReturn(java.util.Optional.of(mockData.get(2)));
        Assertions.assertEquals(mockData.get(2), ts.getTodoById("2"));
    }

    @Test
    void createTodo_returnsId() {
        TodoService ts = new TodoService(todoRepo, ids);
        when(ids.generateID()).thenReturn("4");
        Todo newTodo = new Todo("4", "Take the trash out", Status.OPEN);
        when(todoRepo.save(newTodo)).thenReturn(newTodo);
        Assertions.assertEquals("4", ts.createTodo(new TodoDTO("Take the trash out", Status.OPEN)));
    }

    @Test
    void editTodo_returnsUpdatedTodo() {
        TodoService ts = new TodoService(todoRepo, ids);
        Todo existingTodo = new Todo("2", "Do laundry", Status.IN_PROGRESS);
        TodoDTO updatedTodoDTO = new TodoDTO( "Do laundry", Status.DONE);
        Todo updatedTodo = new Todo("2", "Do laundry", Status.DONE);
        when(todoRepo.findById("2")).thenReturn(java.util.Optional.of(existingTodo));
        when(todoRepo.save(updatedTodo)).thenReturn(updatedTodo);
        Assertions.assertEquals(updatedTodo, ts.editTodo("2", updatedTodoDTO));
    }

    @Test
    void deleteTodoById_callsDeleteInRepo() {
        TodoService ts = new TodoService(todoRepo, ids);
        Todo todo = new Todo("2", "Do laundry", Status.IN_PROGRESS);
        when(todoRepo.findById("2")).thenReturn(java.util.Optional.of(todo));
        ts.deleteTodoById("2");
        verify(todoRepo).delete(todo);
    }

}
