package com.chanty.hrms.exception;

import java.time.OffsetDateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
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
    ErrorResponse errorResponse =
        new ErrorResponse(e.getMessage(), e.getStatus(), OffsetDateTime.now());
    return ResponseEntity.status(e.getStatus()).body(errorResponse);
  }

  @ExceptionHandler(value = {UnauthorizedException.class, BadCredentialsException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
    ErrorResponse errorResponse =
        ErrorResponse.builder().message(e.getMessage()).status(HttpStatus.UNAUTHORIZED).build();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  @ExceptionHandler(UnExpectedException.class)
  public ResponseEntity<ErrorResponse> handleUnExpectedException(UnExpectedException exception) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .message(exception.getMessage())
            .status(HttpStatus.EXPECTATION_FAILED)
            .build();
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorResponse);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(FileIOException.class)
  public ResponseEntity<ErrorResponse> handleFileIOException(FileIOException exception) {
    ErrorResponse response =
        ErrorResponse.builder()
            .message(exception.getMessage())
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(MailException.class)
  public ResponseEntity<ErrorResponse> handleMailException(MailException exception) {
    ErrorResponse response =
        ErrorResponse.builder()
            .message(exception.getMessage())
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(MultipartException.class)
  public ResponseEntity<ErrorResponse> handleMultipartFileException(MultipartException exception) {
    var errorResponse =
        ErrorResponse.builder()
            .message(exception.getMessage())
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .timestamp(OffsetDateTime.now())
            .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataViolationException(
      DataIntegrityViolationException exception) {
    var message = "The username or email have already in used";
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .message(message)
            .timestamp(OffsetDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(CommonException.class)
  public ResponseEntity<ErrorResponse> handleCommonExceptionHandler(CommonException exception) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(HttpStatus.FORBIDDEN)
            .message(exception.getMessage())
            .timestamp(OffsetDateTime.now())
            .build();
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
  }

  @ExceptionHandler(MailVerificationException.class)
  public ResponseEntity<ErrorResponse> handleMailVerificationException(
      MailVerificationException exception) {
    ErrorResponse response =
        ErrorResponse.builder()
            .timestamp(OffsetDateTime.now())
            .message(exception.getMessage())
            .status(HttpStatus.NOT_FOUND)
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }
  @ExceptionHandler(UsernameNotFoundException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception){
    ErrorResponse errorResponse= ErrorResponse.builder()
            .message(exception.getMessage())
            .status(HttpStatus.UNAUTHORIZED)
            .timestamp(OffsetDateTime.now())
            .build();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }
}
