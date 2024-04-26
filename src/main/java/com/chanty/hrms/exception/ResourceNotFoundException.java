package com.chanty.hrms.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

  public ResourceNotFoundException(String resourceName, Long id) {
    super(HttpStatus.NOT_FOUND, String.format("%s With id = %d not found", resourceName, id));
  }

  public ResourceNotFoundException(String resourceName, String email) {
    super(HttpStatus.NOT_FOUND, String.format("%s %s is not found", resourceName, email));
  }
}
