package com.visp.gate_command.handler;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.visp.gate_command.domain.dto.ResponseDto;
import com.visp.gate_command.exception.FileProcessingException;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.exception.UserAlreadyExistException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

class ServiceExceptionHandlerTest {

    private final ServiceExceptionHandler exceptionHandler = new ServiceExceptionHandler();

    @Test
    void testHandleNotFoundException() {
        // Arrange
        NotFoundException exception = new NotFoundException("Resource not found");

        // Act
        ResponseEntity<ResponseDto> response = exceptionHandler.handleNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.name(), response.getBody().getCode());
        assertEquals("Resource not found", response.getBody().getMessage());
    }

    @Test
    void testHandleFileProcessingException() {
        // Arrange
        FileProcessingException exception = new FileProcessingException("Error processing file");

        // Act
        ResponseEntity<ResponseDto> response = exceptionHandler.handleFileProcessingException(exception);

        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE.name(), response.getBody().getCode());
        assertEquals("Error processing file", response.getBody().getMessage());
    }

    @Test
    void testHandleUserAlreadyExistsException() {
        // Arrange
        UserAlreadyExistException exception = new UserAlreadyExistException("User already exists");

        // Act
        ResponseEntity<ResponseDto> response =
                exceptionHandler.handleUserAlreadyExistsException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.name(), response.getBody().getCode());
        assertEquals("User already exists", response.getBody().getMessage());
    }

    @Test
    void testHandleMethodArgumentNotValidException() {
        // Arrange
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getMessage()).thenReturn("Validation failed for argument");

        // Act
        ResponseEntity<ResponseDto> response =
                exceptionHandler.handleMethodArgumentNotValidException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.name(), response.getBody().getCode());
        assertEquals("Validation failed for argument", response.getBody().getMessage());
    }
}