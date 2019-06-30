package com.zalizniak.couchbase.exception;

/**
 * Api base runtime exception
 * 
 * @author charz
 *
 */
public abstract class BaseRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public BaseRuntimeException(String message) {
    super(message);
  }

  public abstract Integer getCode();

}
