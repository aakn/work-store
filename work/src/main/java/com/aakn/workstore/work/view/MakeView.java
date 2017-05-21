package com.aakn.workstore.work.view;

import com.aakn.workstore.work.dto.NamesResponse;
import com.aakn.workstore.work.dto.WorksResponse;

import io.dropwizard.views.View;
import lombok.Data;

@Data
public class MakeView extends BaseView {

  private String type = "make";
  private NamesResponse modelNames;
  private String make;

  public MakeView() {
    super("make.ftl");
  }
}
