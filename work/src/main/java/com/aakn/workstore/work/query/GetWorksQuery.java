package com.aakn.workstore.work.query;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.common.Query;
import com.aakn.workstore.work.dto.WorksRequest;
import com.aakn.workstore.work.dto.WorksResponse;
import com.aakn.workstore.work.function.WorkEntityToResponseFunction;
import com.aakn.workstore.work.model.Work;
import com.aakn.workstore.work.repository.WorkRepository;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class GetWorksQuery implements Query<WorksRequest, WorksResponse> {

  private final WorkRepository workRepository;
  private final WorkEntityToResponseFunction workEntityToResponseFunction;

  @Inject
  public GetWorksQuery(WorkRepository workRepository,
                       WorkEntityToResponseFunction workEntityToResponseFunction) {
    this.workRepository = workRepository;
    this.workEntityToResponseFunction = workEntityToResponseFunction;
  }

  public WorksResponse apply(WorksRequest worksRequest) {
    log.info("request to get works: {}", worksRequest);
    List<Work> workEntities = workRepository.getWorks(worksRequest);
    return workEntityToResponseFunction.apply(workEntities);
  }
}
