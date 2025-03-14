package com.visp.gate_command.handler;

import com.visp.gate_command.exception.FileProcessingException;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.exception.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(FileProcessingException.class)
  public ResponseEntity<String> handleFileProcessingException(FileProcessingException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
