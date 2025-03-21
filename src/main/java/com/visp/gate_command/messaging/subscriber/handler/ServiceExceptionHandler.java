package com.visp.gate_command.messaging.subscriber.handler;

import com.visp.gate_command.domain.dto.ResponseDto;
import com.visp.gate_command.exception.FileProcessingException;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.exception.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ResponseDto> handleNotFoundException(NotFoundException ex) {
    return new ResponseEntity<>(
        ResponseDto.builder().code(HttpStatus.NOT_FOUND.name()).message(ex.getMessage()).build(),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(FileProcessingException.class)
  public ResponseEntity<ResponseDto> handleFileProcessingException(FileProcessingException ex) {
    return new ResponseEntity<>(
        ResponseDto.builder()
            .code(HttpStatus.SERVICE_UNAVAILABLE.name())
            .message(ex.getMessage())
            .build(),
        HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<ResponseDto> handleUserAlreadyExistsException(
      UserAlreadyExistException ex) {
    return new ResponseEntity<>(
        ResponseDto.builder().code(HttpStatus.BAD_REQUEST.name()).message(ex.getMessage()).build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    return new ResponseEntity<>(
        ResponseDto.builder().code(HttpStatus.BAD_REQUEST.name()).message(ex.getMessage()).build(),
        HttpStatus.BAD_REQUEST);
  }
}
