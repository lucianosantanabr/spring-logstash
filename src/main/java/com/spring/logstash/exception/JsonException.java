package com.spring.logstash.exception;

public class JsonException extends RuntimeException {
  private static final long serialVersionUID = -4398891155526530222L;

  public JsonException() {
    super();
  }

  public JsonException(Throwable cause) {
    super(cause);
  }

  public JsonException(String message, Throwable cause) {
    super(message, cause);
  }

  public JsonException(String message) {
    super(message);
  }

}
