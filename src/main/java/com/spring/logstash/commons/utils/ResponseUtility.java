package com.spring.logstash.commons.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseUtility {
  
  default <T> ResponseEntity<T> response(HttpStatus status, T body) {
    return ResponseEntity.status(status).body(body);
  }
  
  default <T> ResponseEntity<T> response(HttpStatus status) {
    return response(status, null);
  }

  default <T> ResponseEntity<T> unauthorized() {
    return response(HttpStatus.UNAUTHORIZED);
  }
  
  default <T> ResponseEntity<T> noContent() {
    return response(HttpStatus.NO_CONTENT);
  }
  
  default <T> ResponseEntity<T> conflict() {
    return response(HttpStatus.CONFLICT);
  }
  
  default <T> ResponseEntity<T> created() {
    return response(HttpStatus.CREATED);
  }
  
  default <T> ResponseEntity<T> failedDependency() {
    return response(HttpStatus.FAILED_DEPENDENCY);
  }
  
  default <T> ResponseEntity<T> preconditionFailed() {
    return response(HttpStatus.PRECONDITION_FAILED);
  }
  
  default <T> ResponseEntity<T> notAcceptable() {
    return response(HttpStatus.NOT_ACCEPTABLE);
  }
  
  default <T> ResponseEntity<T> accepted() {
    return response(HttpStatus.ACCEPTED);
  }
  
  default <T> ResponseEntity<T> ok() {
    return response(HttpStatus.OK);
  }
  
  default <T> ResponseEntity<T> ok(T body) {
    return response(HttpStatus.OK, body);
  }
  
  default <T> ResponseEntity<T> internalServerError(T body) {
    return response(HttpStatus.INTERNAL_SERVER_ERROR, body);
  }
  
  default <T> ResponseEntity<T> internalServerError() {
    return response(HttpStatus.INTERNAL_SERVER_ERROR, null);
  }
  
}
