package com.liberty.errors;

/**
 * Represent not found error.
 */
public class AccessDeniedException extends ApplicationException {

  public AccessDeniedException(String message) {
    super(ErrorCode.ACCESS_DENIED, message);
  }

  public AccessDeniedException(String message, String developerMessage, Exception innerException) {
    super(ErrorCode.ACCESS_DENIED, message, developerMessage, innerException);
  }
}
