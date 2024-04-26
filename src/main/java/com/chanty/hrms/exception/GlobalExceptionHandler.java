package com.chanty.hrms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(
      UserNotFoundException exception) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .message(exception.getMessage())
            .status(HttpStatus.NOT_FOUND)
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler({ApiException.class})
  public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), e.getStatus());
    return ResponseEntity.status(e.getStatus()).body(errorResponse);
  }

  @ExceptionHandler(value = {UnauthorizedException.class, BadCredentialsException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
    ErrorResponse errorResponse =
        ErrorResponse.builder().message(e.getMessage()).status(HttpStatus.UNAUTHORIZED).build();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(KeycloakUserException.class)
  public ResponseEntity<ErrorResponse> handleKeycloakUserException(
      KeycloakUserException exception) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .message(exception.getMessage())
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  @ExceptionHandler(UnExpectedException.class)
  public ResponseEntity<ErrorResponse> handleUnExpectedException(UnExpectedException exception){
    ErrorResponse errorResponse = ErrorResponse.builder()
            .message(exception.getMessage())
            .status(HttpStatus.EXPECTATION_FAILED)
            .build();
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorResponse);
  }
}
