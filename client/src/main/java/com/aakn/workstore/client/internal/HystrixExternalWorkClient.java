package com.aakn.workstore.client.internal;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import com.aakn.workstore.client.ExternalWorkClient;
import com.aakn.workstore.client.dto.Works;
import com.aakn.workstore.client.exception.ExternalWorkApiException;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import java.net.URI;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class HystrixExternalWorkClient implements ExternalWorkClient {

  private final Provider<GetExternalWorkCommand> getExternalWorkCommandProvider;

  @Inject
  public HystrixExternalWorkClient(
      Provider<GetExternalWorkCommand> getExternalWorkCommandProvider) {
    this.getExternalWorkCommandProvider = getExternalWorkCommandProvider;
  }

  @Override
  public Works getWorks(URI uri) {
    try {
      return getExternalWorkCommandProvider.get()
          .uri(uri)
          .execute();
    } catch (HystrixRuntimeException e) {
      log.error("exception while calling the external API", e);
      throw new ExternalWorkApiException(e.getMessage(), uri);
    }
  }
}
