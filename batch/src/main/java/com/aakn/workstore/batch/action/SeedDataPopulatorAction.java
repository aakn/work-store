package com.aakn.workstore.batch.action;

import com.google.inject.Inject;

import com.aakn.workstore.batch.config.SeedDataConfiguration;
import com.aakn.workstore.batch.dto.BatchWorkRequest;
import com.aakn.workstore.work.model.Work;
import com.aakn.workstore.work.repository.WorkRepository;

import java.util.List;

import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeedDataPopulatorAction {

  private final SeedDataConfiguration seedDataConfiguration;
  private final WorkRepository workRepository;
  private final ProcessBatchRequestAction processBatchRequestAction;

  @Inject
  public SeedDataPopulatorAction(SeedDataConfiguration seedDataConfiguration,
                                 WorkRepository workRepository,
                                 ProcessBatchRequestAction processBatchRequestAction) {
    this.seedDataConfiguration = seedDataConfiguration;
    this.workRepository = workRepository;
    this.processBatchRequestAction = processBatchRequestAction;
  }

  @UnitOfWork
  public void accept() {
    String namespace = seedDataConfiguration.getNamespace();

    log.info("checking if seed data is already populated for {}", namespace);

    if (workRepository.getWorksForNamespace(namespace).isEmpty()) {
      log.info("populating the seed data for namespace {}", namespace);
      BatchWorkRequest request = new BatchWorkRequest();
      request.setDirectory(namespace);
      request.setUrl(seedDataConfiguration.getUrl());
      processBatchRequestAction.accept(request);
      log.info("done populating the seed data");
    }
  }
}
