package com.liberty.errors;

/**
 * Internal error code.
 */
public enum ErrorCode {

  ACCESS_DENIED(1),
  NOT_FOUND_ERROR(10),
  WRONG_INPUT_PARAMETERS_ERROR(20),
  DATA_CONFLICT_ERROR(30),
  DATABASE_ERROR(40),
  REMOTE_RESOURCE_ERROR(50),
  OPERATION_NOT_PERMITTED_ERROR(60),
  INTERNAL_SERVER_ERROR(70),
  OPERATION_FAILED_ERROR(80),
  VALIDATION_FAILED_ERROR(85),
  ILLEGAL_ARGUMENT_ERROR(90),
  SEARCH_TERM_INVALID(91);

  private int code;

  ErrorCode(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
