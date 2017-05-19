package com.aakn.workstore.work.config;

import com.google.inject.AbstractModule;

import com.aakn.workstore.work.repository.WorkRepository;
import com.aakn.workstore.work.repository.impl.HibernateWorkRepository;

public class WorkModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(WorkRepository.class).to(HibernateWorkRepository.class);
  }
}
