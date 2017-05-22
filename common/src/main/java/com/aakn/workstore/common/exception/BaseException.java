package com.aakn.workstore.common.exception;

import com.google.common.collect.Maps;

import java.util.Map;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {

  private int status = 500;
  private String type;
  private String message;
  private Map<String, String> params = Maps.newHashMap();

  public BaseException(String type, String message) {
    super(message);
    this.type = type;
    this.message = message;
  }
}
