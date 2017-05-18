package com.aakn.workstore.batch.config;

import com.google.inject.AbstractModule;

import com.aakn.workstore.batch.resource.BatchResource;

public class BatchModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(BatchResource.class);
  }
}
