package com.aakn.workstore.work.query;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.common.BiQuery;
import com.aakn.workstore.common.Query;
import com.aakn.workstore.work.dto.NamesResponse;
import com.aakn.workstore.work.repository.WorkRepository;

import java.util.List;

@Singleton
public class GetModelNamesQuery implements BiQuery<String, String, NamesResponse> {

  private final WorkRepository workRepository;

  @Inject
  public GetModelNamesQuery(WorkRepository workRepository) {
    this.workRepository = workRepository;
  }

  @Override
  public NamesResponse apply(String namespace, String make) {
    List<String> uniqueMakeNames = workRepository.getUniqueModelNames(namespace, make);
    NamesResponse response = new NamesResponse();
    response.setNames(uniqueMakeNames);
    return response;
  }
}
