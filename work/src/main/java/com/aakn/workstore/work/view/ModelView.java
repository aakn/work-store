package com.aakn.workstore.work.view;

import com.aakn.workstore.work.dto.NamesResponse;

import lombok.Data;

@Data
public class ModelView extends BaseView {

  private String type = "model";
  private String make;
  private String model;
  private NamesResponse modelNames;

  public ModelView() {
    super("model.ftl");
  }
}
