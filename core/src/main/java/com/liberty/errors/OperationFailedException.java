package com.liberty.errors;

/**
 * Represents fail of operation executing.
 */
public class OperationFailedException extends ApplicationException {

  public OperationFailedException(String message) {
    super(ErrorCode.OPERATION_FAILED_ERROR, message);
  }
}
