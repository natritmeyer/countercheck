package com.natritmeyer.countercheck.support.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class PetType {
  @JsonProperty("id")
  private int id;

  @JsonProperty("name")
  private String name;

  public PetType() {
  }

  public PetType(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return "PetType{"
        + "id=" + id
        + ", name='" + name + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PetType petType = (PetType) o;
    return id == petType.id && Objects.equals(name, petType.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
