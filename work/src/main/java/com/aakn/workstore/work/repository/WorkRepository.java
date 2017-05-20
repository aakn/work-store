package com.aakn.workstore.work.repository;

import com.aakn.workstore.work.model.Work;

import java.util.List;

public interface WorkRepository {

  Work persist(Work work);

  List<Work> getWorksForNamespace(String namespace);

}
