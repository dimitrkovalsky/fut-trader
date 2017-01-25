package com.liberty.errors;

import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Base Exception name for Error mapping.
 */
@Data
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {

  @NonNull
  private final ErrorCode code;
  private final String message;
  private final String developerMessage;
  private final Exception innerException;

  public ApplicationException(ErrorCode code) {
    this(code, null);
  }

  public ApplicationException(ErrorCode code, String message) {
    this(code, message, null);
  }

  public ApplicationException(ErrorCode code, String message, Exception innerException) {
    this(code, message, null, innerException);
  }

  public ApplicationException(ErrorCode code, String message, String developerMessage,
                              Exception innerException) {
    this.code = code;
    this.message = message;
    this.developerMessage = developerMessage;
    this.innerException = innerException;
  }

}
