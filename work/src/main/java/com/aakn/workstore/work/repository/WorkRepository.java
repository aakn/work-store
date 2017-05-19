package com.aakn.workstore.work.repository;

import com.aakn.workstore.work.model.Work;

public interface WorkRepository {

  Work persist(Work work);

}
