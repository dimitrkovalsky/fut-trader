package com.liberty.errors;

/**
 * Represents validation fail.
 */
public class ValidationException extends ApplicationException {

  public ValidationException(String message) {
    super(ErrorCode.VALIDATION_FAILED_ERROR, message);
  }
}
