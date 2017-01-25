package com.liberty.errors;

/**
 * Represent not found error.
 */
public class NotFoundException extends ApplicationException {

  public NotFoundException(String message) {
    super(ErrorCode.NOT_FOUND_ERROR, message);
  }
}
