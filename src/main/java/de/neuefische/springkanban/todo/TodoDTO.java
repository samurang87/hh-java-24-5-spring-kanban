package de.neuefische.springkanban.todo;

import lombok.With;

@With
public record TodoDTO (String description, Status status) {
}
