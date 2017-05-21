package com.aakn.workstore.work.view;

import com.aakn.workstore.work.dto.NamesResponse;

import lombok.Data;

@Data
public class IndexView extends BaseView {

  private String type = "index";
  private NamesResponse makeNames;

  public IndexView() {
    super("index.ftl");
  }
}
