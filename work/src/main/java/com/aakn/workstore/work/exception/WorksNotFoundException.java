package com.aakn.workstore.work.exception;

import com.aakn.workstore.common.exception.BaseException;

import java.util.Optional;

public class WorksNotFoundException extends BaseException {

  private static String MESSAGE_FORMAT = "no works found for namespace: %s, make: %s and model: %s";

  public WorksNotFoundException(String namespace, Optional<String> make, Optional<String> model) {
    super("WORKS_NOT_FOUND", String.format(MESSAGE_FORMAT, namespace, make, model));
    setStatus(404);
  }
}
