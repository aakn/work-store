package com.aakn.workstore.client.exception;

import com.aakn.workstore.common.exception.BaseException;

import java.net.URI;

public class ExternalWorkApiException extends BaseException {

  public ExternalWorkApiException(String message, URI uri) {
    super("DOWNSTREAM_ERROR", message);
    this.getParams().put("uri", uri.toString());
  }
}
