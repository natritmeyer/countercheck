package com.github.natritmeyer.countercheck.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.Objects;

public class Pet {

  @JsonProperty("id")
  private int id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("birthDate")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @JsonProperty("type")
  private PetType petType;

  @Override
  public String toString() {
    return "Pet{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", birthDate=" + birthDate
        + ", petType=" + petType
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pet pet = (Pet) o;
    return id == pet.id && Objects.equals(name, pet.name) && Objects.equals(birthDate, pet.birthDate) && Objects.equals(petType, pet.petType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, birthDate, petType);
  }
}
