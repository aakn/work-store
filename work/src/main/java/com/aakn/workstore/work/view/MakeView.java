package com.aakn.workstore.work.view;

import com.aakn.workstore.work.dto.NamesResponse;
import com.aakn.workstore.work.dto.WorksResponse;

import io.dropwizard.views.View;
import lombok.Data;

@Data
public class MakeView extends BaseView {

  private String type = "make";
  private String make;
  private NamesResponse modelNames;

  public MakeView() {
    super("make.ftl");
  }
}
