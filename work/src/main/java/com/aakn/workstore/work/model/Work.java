package com.aakn.workstore.work.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"externalId", "namespace"})})
public class Work {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotEmpty
  private String externalId;

  @NotEmpty
  private String namespace;

  @Embedded
  @NotNull
  private Exif exif;

  @Embedded
  @NotNull
  private Images images;

  @Embeddable
  @Data
  public static class Exif {

    private String model;
    private String make;
  }

  @Embeddable
  @Data
  public static class Images {

    @URL
    private String smallImage;
    @URL
    private String mediumImage;
    @URL
    private String largeImage;
  }

}
