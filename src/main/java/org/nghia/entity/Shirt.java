package org.nghia.entity;

import java.io.Serializable;
import java.util.List;

public class Shirt implements Serializable {
  private Integer id;
  private String name;
  private String clb;
  private Size size;
  private Type type;
  private List<String> materials;

  public Shirt(String name, String clb, Size size, Type type, List<String> materials) {
    this.name = name;
    this.clb = clb;
    this.size = size;
    this.type = type;
    this.materials = materials;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getClb() {
    return clb;
  }

  public void setClb(String clb) {
    this.clb = clb;
  }

  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  public List<String> getMaterials() {
    return materials;
  }

  public void setMaterials(List<String> materials) {
    this.materials = materials;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }
}
