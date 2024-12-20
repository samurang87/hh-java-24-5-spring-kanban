package de.neuefische.springkanban;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorMessage(HttpStatus status, String message, LocalDateTime timestamp) {

    public ErrorMessage(HttpStatus status, String message) {
        this(status, message, LocalDateTime.now());
    }

}
