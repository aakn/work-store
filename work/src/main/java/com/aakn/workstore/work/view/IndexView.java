package com.aakn.workstore.work.view;

import lombok.Data;

@Data
public class IndexView extends BaseView {

  private String type = "index";

  public IndexView() {
    super("index.ftl");
  }
}
