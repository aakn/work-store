package com.aakn.workstore.client.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlValue;

import lombok.Data;


@Data
public class Works {

  private List<Work> work;

  @Data
  public static class Work {

    private String id;
    private Exif exif;
    private List<Image> images;

    @XmlElementWrapper(name = "urls")
    @XmlElement(name = "url")
    public List<Image> getImages() {
      return images;
    }
  }

  @Data
  public static class Exif {

    private String model;
    private String make;
  }

  @Data
  public static class Image {

    private String type;

    private String image;

    @XmlAttribute
    public String getType() {
      return type;
    }

    @XmlValue
    public String getImage() {
      return image;
    }
  }
}
