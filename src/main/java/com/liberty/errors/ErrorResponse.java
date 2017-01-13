package com.liberty.errors;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Pojo that represents error response for client.
 */
@Data
public class ErrorResponse {
  private int httpStatus;
  private int code;
  private String message;
  private String developerMessage;

  public ErrorResponse(int httpStatus, int code, String message, String developerMessage) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
    this.developerMessage = developerMessage;
  }

  public ErrorResponse(HttpStatus status, ErrorCode errorCode, String message, String developerMessage) {
    this.httpStatus = status.value();
    this.code = errorCode.getCode();
    this.message = message;
    this.developerMessage = developerMessage;
  }

  public ErrorResponse(int httpStatus, int code, String message) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
  }

  public ErrorResponse(HttpStatus status, ApplicationException e) {
    this.httpStatus = status.value();
    this.code = e.getCode().getCode();
    this.message = e.getMessage();
    this.developerMessage = e.getDeveloperMessage();
  }

}
