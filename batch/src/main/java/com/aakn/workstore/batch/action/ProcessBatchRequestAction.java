package com.aakn.workstore.batch.action;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.batch.dto.BatchWorkRequest;
import com.aakn.workstore.common.Action;

@Singleton
public class ProcessBatchRequestAction implements Action<BatchWorkRequest> {

  @Inject
  public ProcessBatchRequestAction() {
  }

  @Override
  public void accept(BatchWorkRequest batchWorkRequest) {

  }
}
