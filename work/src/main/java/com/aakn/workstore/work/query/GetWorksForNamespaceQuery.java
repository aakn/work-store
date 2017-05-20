package com.aakn.workstore.work.query;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.common.Query;
import com.aakn.workstore.work.dto.WorksResponse;
import com.aakn.workstore.work.model.Work;
import com.aakn.workstore.work.repository.WorkRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class GetWorksForNamespaceQuery implements Query<String, WorksResponse> {

  private final WorkRepository workRepository;

  @Inject
  public GetWorksForNamespaceQuery(WorkRepository workRepository) {
    this.workRepository = workRepository;
  }

  @Override
  public WorksResponse apply(String namespace) {
    List<Work> workEntities = workRepository.getWorksForNamespace(namespace);

    List<WorksResponse.Work> works = workEntities.stream()
        .map(work -> {
          WorksResponse.Work workResponse = new WorksResponse.Work();
          workResponse.setId(work.getExternalId());
          workResponse.setExif(buildExif(work.getExif()));
          workResponse.setImages(buildImages(work.getImages()));
          return workResponse;
        })
        .collect(Collectors.toList());
    WorksResponse response = new WorksResponse();
    response.setNamespace(namespace);
    response.setWorks(works);
    return response;
  }

  private WorksResponse.Exif buildExif(Work.Exif exif) {
    return Optional.ofNullable(exif)
        .map(exifEntity -> {
          WorksResponse.Exif exifResponse = new WorksResponse.Exif();
          exifResponse.setMake(exifEntity.getMake());
          exifResponse.setModel(exifEntity.getModel());
          return exifResponse;
        })
        .orElseGet(WorksResponse.Exif::new);
  }

  private WorksResponse.Images buildImages(Work.Images images) {
    return Optional.ofNullable(images)
        .map(imagesEntity -> {
          WorksResponse.Images imagesResponse = new WorksResponse.Images();
          imagesResponse.setLargeImage(imagesEntity.getLargeImage());
          imagesResponse.setMediumImage(imagesEntity.getMediumImage());
          imagesResponse.setSmallImage(imagesEntity.getSmallImage());
          return imagesResponse;
        }).orElseGet(WorksResponse.Images::new);
  }
}
