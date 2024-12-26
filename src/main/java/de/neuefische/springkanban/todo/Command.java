package de.neuefische.springkanban.todo;

public record Command(Todo newTodo, Todo oldTodo) {
}
