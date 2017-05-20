package com.aakn.workstore.work.query;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.common.BiQuery;
import com.aakn.workstore.work.dto.WorksResponse;
import com.aakn.workstore.work.function.WorkEntityToResponseFunction;
import com.aakn.workstore.work.model.Work;
import com.aakn.workstore.work.repository.WorkRepository;

import java.util.List;

@Singleton
public class GetWorksForNamespaceAndMakeQuery implements BiQuery<String, String, WorksResponse> {

  private final WorkRepository workRepository;
  private final WorkEntityToResponseFunction workEntityToResponseFunction;

  @Inject
  public GetWorksForNamespaceAndMakeQuery(WorkRepository workRepository,
                                          WorkEntityToResponseFunction workEntityToResponseFunction) {
    this.workRepository = workRepository;
    this.workEntityToResponseFunction = workEntityToResponseFunction;
  }

  @Override
  public WorksResponse apply(String namespace, String make) {
    List<Work> workEntities = workRepository.getWorksForNamespaceAndMake(namespace, make);
    return workEntityToResponseFunction.apply(workEntities);
  }
}
