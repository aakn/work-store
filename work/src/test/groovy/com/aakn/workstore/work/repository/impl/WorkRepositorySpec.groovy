package com.aakn.workstore.work.repository.impl

import com.aakn.test.hibernate.DAOTestRule
import com.aakn.workstore.work.model.Work
import com.aakn.workstore.work.repository.WorkRepository
import org.junit.Rule
import spock.lang.Specification

class WorkRepositorySpec extends Specification {

  @Rule
  public DAOTestRule database = DAOTestRule.newBuilder()
    .addEntityClass(Work.class)
    .setShowSql(true)
    .addResource("queries.hbm.xml")
    .build()

  private WorkRepository workRepository

  void setup() {
    workRepository = new HibernateWorkRepository(database.sessionFactory)
  }

  def "should create work"() {
    given:
    def entity = buildWork("100", "foo")

    when:
    database.inTransaction {
      workRepository.persist(entity)
    }

    then:
    entity.id
  }

  def "should get work for a namespace after persisting"() {
    given:
    def entities = [buildWork("100", "foo"), buildWork("101", "foo"),
                    buildWork("102", "foo"), buildWork("103", "bar")]

    when:
    database.inTransaction {
      entities.forEach { workRepository.persist(it) }
    }
    def works = workRepository.getWorksForNamespace("foo")

    then:
    works == entities[0..2]
  }

  def "should get work for a namespace and make after persisting"() {
    given:
    def entities = [buildWork("100", "foo"), buildWork("101", "foo"),
                    buildWork("102", "foo"), buildWork("103", "foo", "LEICA"),
                    buildWork("104", "bar")]

    when:
    database.inTransaction {
      entities.forEach { workRepository.persist(it) }
    }
    def works = workRepository.getWorksForNamespaceAndMake("foo", "NIKON")

    then:
    works == entities[0..2]
  }

  def "should get work for a namespace, make and model after persisting"() {
    given:
    def entities = [buildWork("100", "foo", "NIKON"), buildWork("101", "foo", "NIKON"),
                    buildWork("102", "foo", "NIKON"), buildWork("103", "foo", "LEICA"),
                    buildWork("104", "bar", "NIKON"), buildWork("104", "foo", "NIKON", "N80")]

    when:
    database.inTransaction {
      entities.forEach { workRepository.persist(it) }
    }
    def works = workRepository.getWorksForNamespaceMakeAndModel("foo", "NIKON", "N50")

    then:
    works == entities[0..2]
  }

  private Work buildWork(externalId, namespace, make = "NIKON", model = "N50") {
    def images = new Work.Images(smallImage: "http://localhost/small.jpg")
    def exif = new Work.Exif(model: model, make: make)

    return new Work(externalId: externalId, namespace: namespace, exif: exif, images: images)
  }
}
