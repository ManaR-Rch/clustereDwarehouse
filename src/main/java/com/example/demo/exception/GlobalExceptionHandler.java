package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
        .collect(Collectors.joining("; "));

    Map<String, Object> body = new HashMap<>();
    body.put("message", message.isEmpty() ? "Validation failed" : message);
    body.put("timestamp", LocalDateTime.now().toString());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArg(IllegalArgumentException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("timestamp", LocalDateTime.now().toString());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage() == null ? "Internal server error" : ex.getMessage());
    body.put("timestamp", LocalDateTime.now().toString());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }

}
