package com.aakn.workstore.client.internal;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import com.aakn.workstore.client.ExternalWorkClient;
import com.aakn.workstore.client.dto.Works;

import java.net.URI;

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
    return getExternalWorkCommandProvider.get()
        .uri(uri)
        .execute();
  }
}
