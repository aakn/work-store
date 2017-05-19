package com.aakn.workstore.work.repository.impl

import com.aakn.workstore.work.model.Work
import com.aakn.workstore.work.repository.WorkRepository
import io.dropwizard.testing.junit.DAOTestRule
import org.junit.Rule
import spock.lang.Specification

class WorkRepositorySpec extends Specification {
  @Rule
  public DAOTestRule database = DAOTestRule.newBuilder()
    .addEntityClass(Work.class)
    .setShowSql(true)
    .build()

  private WorkRepository workRepository

  void setup() {
    workRepository = new HibernateWorkRepository(database.sessionFactory)
  }

  def "should create work"() {
    setup:
    def entity = buildWork()

    when:
    database.inTransaction {
      workRepository.persist(entity)
    }

    then:
    entity.id

  }

  private Work buildWork() {
    def images = new Work.Images(smallImage: "http://localhost/small.jpg")
    def exif = new Work.Exif(model: "N50", make: "NIKON")

    return new Work(exif: exif, images: images)
  }
}
