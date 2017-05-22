package com.aakn.workstore.common.exception;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;

import java.util.Map;
import java.util.Random;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Provider
@Slf4j
public class BaseExceptionMapper implements ExceptionMapper<BaseException> {

  private static final Random RANDOM = new Random();

  @Override
  public Response toResponse(BaseException exception) {
    String id = String.format("%016x", RANDOM.nextLong());
    Error error = new Error();
    error.setId(id);
    error.setMessage(exception.getMessage());
    error.setType(exception.getType());
    error.setParams(exception.getParams());

    log.info("base exception {} with id {}", exception.getMessage(), id);
    return Response.status(exception.getStatus())
        .entity(error)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }

  @Data
  public static class Error {

    private String id;
    private String type;
    private String message;
    private Map<String, String> params = Maps.newHashMap();

  }
}
