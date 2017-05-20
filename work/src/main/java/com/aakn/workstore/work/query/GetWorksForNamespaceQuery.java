package com.aakn.workstore.work.query;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.common.Query;
import com.aakn.workstore.work.dto.WorksResponse;
import com.aakn.workstore.work.function.WorkEntityToResponseFunction;
import com.aakn.workstore.work.model.Work;
import com.aakn.workstore.work.repository.WorkRepository;

import java.util.List;

@Singleton
public class GetWorksForNamespaceQuery implements Query<String, WorksResponse> {

  private final WorkRepository workRepository;
  private final WorkEntityToResponseFunction workEntityToResponseFunction;

  @Inject
  public GetWorksForNamespaceQuery(WorkRepository workRepository,
                                   WorkEntityToResponseFunction workEntityToResponseFunction) {
    this.workRepository = workRepository;
    this.workEntityToResponseFunction = workEntityToResponseFunction;
  }

  @Override
  public WorksResponse apply(String namespace) {
    List<Work> workEntities = workRepository.getWorksForNamespace(namespace);
    return workEntityToResponseFunction.apply(workEntities);
  }
}
