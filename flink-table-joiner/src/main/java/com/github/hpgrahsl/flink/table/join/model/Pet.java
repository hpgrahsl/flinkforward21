package com.github.hpgrahsl.flink.table.join.model;

public class Pet {

  public Integer id;
  public String name;
  public String birth_date;
  public Integer type_id;
  public Integer owner_id;

  public Pet() {
  }

  public Pet(Integer id, String name, String birth_date, Integer type_id, Integer owner_id) {
    this.id = id;
    this.name = name;
    this.birth_date = birth_date;
    this.type_id = type_id;
    this.owner_id = owner_id;
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

  public String getBirth_date() {
    return birth_date;
  }

  public void setBirth_date(String birth_date) {
    this.birth_date = birth_date;
  }

  public Integer getType_id() {
    return type_id;
  }

  public void setType_id(Integer type_id) {
    this.type_id = type_id;
  }

  public Integer getOwner_id() {
    return owner_id;
  }

  public void setOwner_id(Integer owner_id) {
    this.owner_id = owner_id;
  }

  @Override
  public String toString() {
    return "Pet{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", birth_date='" + birth_date + '\'' +
        ", type_id=" + type_id +
        ", owner_id=" + owner_id +
        '}';
  }

}
