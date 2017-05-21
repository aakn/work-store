package com.aakn.workstore.batch.action;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.batch.dto.BatchWorkRequest;
import com.aakn.workstore.client.ExternalWorkClient;
import com.aakn.workstore.client.dto.Works;
import com.aakn.workstore.common.Action;
import com.aakn.workstore.work.model.Work;
import com.aakn.workstore.work.repository.WorkRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class ProcessBatchRequestAction implements Action<BatchWorkRequest> {

  private final ExternalWorkClient externalWorkClient;
  private final WorkRepository workRepository;

  @Inject
  public ProcessBatchRequestAction(ExternalWorkClient externalWorkClient,
                                   WorkRepository workRepository) {
    this.externalWorkClient = externalWorkClient;
    this.workRepository = workRepository;
  }

  @Override
  public void accept(BatchWorkRequest batchWorkRequest) {
    Works works = externalWorkClient.getWorks(batchWorkRequest.url());
    persistWorks(works, batchWorkRequest);
  }

  private void persistWorks(Works works, BatchWorkRequest batchWorkRequest) {
    works.getWork().stream()
        .map(work -> {
          Work entity = new Work();

          Work.Exif exif = new Work.Exif();
          exif.setModel(work.getExif().getModel());
          exif.setMake(work.getExif().getMake());

          entity.setExternalId(work.getId());
          entity.setNamespace(batchWorkRequest.getDirectory());
          entity.setExif(exif);
          entity.setImages(buildEntityImage(work));
          return entity;
        })
        .forEach(workRepository::persist);
  }

  private Work.Images buildEntityImage(Works.Work work) {
    Work.Images images = new Work.Images();
    Map<String, String> typeToImage = work.getImages().stream()
        .collect(Collectors.toMap(Works.Image::getType, Works.Image::getImage, (a, b) -> a));
    images.setLargeImage(typeToImage.get("large"));
    images.setMediumImage(typeToImage.get("medium"));
    images.setSmallImage(typeToImage.get("small"));
    return images;
  }
}
