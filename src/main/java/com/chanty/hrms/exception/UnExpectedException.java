package com.chanty.hrms.exception;

public class UnExpectedException extends RuntimeException {
  public UnExpectedException(String message) {
    super(message);
  }
  public UnExpectedException(String field, String message) {
    super("%s %s".formatted(field,message));
  }
}
