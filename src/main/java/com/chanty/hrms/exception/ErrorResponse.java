package com.chanty.hrms.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
  private String message;
  private HttpStatus status;
  private OffsetDateTime timestamp;
}
