package com.liberty.errors;

public class DatabaseException extends ApplicationException {

  public DatabaseException(String message) {
    super(ErrorCode.DATABASE_ERROR, message);
  }

  public DatabaseException(String message, Exception e) {
    super(ErrorCode.DATABASE_ERROR, message, e);
  }
}
