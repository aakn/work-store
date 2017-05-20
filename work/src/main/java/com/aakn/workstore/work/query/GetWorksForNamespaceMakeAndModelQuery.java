package com.aakn.workstore.work.query;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.common.Query3;
import com.aakn.workstore.work.dto.WorksResponse;
import com.aakn.workstore.work.function.WorkEntityToResponseFunction;
import com.aakn.workstore.work.model.Work;
import com.aakn.workstore.work.repository.WorkRepository;

import java.util.List;

@Singleton
public class GetWorksForNamespaceMakeAndModelQuery
    implements Query3<String, String, String, WorksResponse> {

  private final WorkRepository workRepository;
  private final WorkEntityToResponseFunction workEntityToResponseFunction;

  @Inject
  public GetWorksForNamespaceMakeAndModelQuery(WorkRepository workRepository,
                                               WorkEntityToResponseFunction workEntityToResponseFunction) {
    this.workRepository = workRepository;
    this.workEntityToResponseFunction = workEntityToResponseFunction;
  }

  @Override
  public WorksResponse apply(String namespace, String make, String model) {
    List<Work> workEntities =
        workRepository.getWorksForNamespaceMakeAndModel(namespace, make, model);
    return workEntityToResponseFunction.apply(workEntities);
  }
}
