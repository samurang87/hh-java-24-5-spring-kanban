package de.neuefische.springkanban;

import lombok.With;

public record Todo(String id, @With String description, @With Status status) {
}
