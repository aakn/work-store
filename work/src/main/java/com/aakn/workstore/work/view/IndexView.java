package com.aakn.workstore.work.view;

import com.aakn.workstore.work.dto.NamesResponse;
import com.aakn.workstore.work.dto.WorksResponse;

import io.dropwizard.views.View;
import lombok.Data;

@Data
public class IndexView extends View {

  private WorksResponse works;
  private NamesResponse makeNames;
  private String namespace;

  public IndexView() {
    super("index.ftl");
  }
}
