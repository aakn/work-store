package com.aakn.workstore.work.dto;

import java.util.Optional;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class WorksRequest {

  private String namespace;
  private Optional<String> model = Optional.empty();
  private Optional<String> make = Optional.empty();
  private Page page = new Page();

  public WorksRequest make(String make) {
    this.make = Optional.ofNullable(make);
    return this;
  }

  public WorksRequest model(String model) {
    this.model = Optional.ofNullable(model);
    return this;
  }

  @Data
  @Accessors(fluent = true)
  public static class Page {

    private int pageNumber = 1;
    private int pageSize = 10;
  }
}
