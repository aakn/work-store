package com.aakn.workstore.work.query;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.common.Query;
import com.aakn.workstore.work.dto.NamesResponse;
import com.aakn.workstore.work.repository.WorkRepository;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class GetMakeNamesQuery implements Query<String, NamesResponse> {

  private final WorkRepository workRepository;

  @Inject
  public GetMakeNamesQuery(WorkRepository workRepository) {
    this.workRepository = workRepository;
  }

  @Override
  public NamesResponse apply(String namespace) {
    log.info("getting the makes for {}", namespace);
    List<String> uniqueMakeNames = workRepository.getUniqueMakeNames(namespace);
    NamesResponse response = new NamesResponse();
    response.setNames(uniqueMakeNames);
    return response;
  }
}
