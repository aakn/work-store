package com.aakn.workstore.batch.action;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.batch.dto.BatchWorkRequest;
import com.aakn.workstore.client.ExternalWorkClient;
import com.aakn.workstore.common.Action;

@Singleton
public class ProcessBatchRequestAction implements Action<BatchWorkRequest> {

  private final ExternalWorkClient externalWorkClient;

  @Inject
  public ProcessBatchRequestAction(ExternalWorkClient externalWorkClient) {
    this.externalWorkClient = externalWorkClient;
  }

  @Override
  public void accept(BatchWorkRequest batchWorkRequest) {
    externalWorkClient.getWorks(batchWorkRequest.getUrl());
  }
}
