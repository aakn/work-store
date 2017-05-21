package com.aakn.workstore.work.view;

import com.aakn.workstore.work.dto.NamesResponse;
import com.aakn.workstore.work.dto.WorksResponse;

import io.dropwizard.views.View;
import lombok.Data;

@Data
public abstract class BaseView extends View {

  private String type;
  private WorksResponse works;
  private String namespace;
  private NamesResponse makeNames;

  public BaseView(String templateName) {
    super(templateName);
  }
}
