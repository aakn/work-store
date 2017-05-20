package com.aakn.workstore.work.dto;

import org.hibernate.validator.constraints.URL;

import java.util.List;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Data;

@Data
@JsonSnakeCase
public class WorksResponse {

  private List<Work> works;

  @Data
  @JsonSnakeCase
  public static class Work {

    private String id;
    private Exif exif;
    private Images images;
    private String namespace;
  }

  @Data
  @JsonSnakeCase
  public static class Exif {

    private String model;
    private String make;
  }

  @Data
  @JsonSnakeCase
  public static class Images {

    @URL
    private String smallImage;
    @URL
    private String mediumImage;
    @URL
    private String largeImage;
  }
}
