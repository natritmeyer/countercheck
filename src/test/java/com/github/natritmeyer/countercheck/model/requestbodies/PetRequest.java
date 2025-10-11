package com.github.natritmeyer.countercheck.model.requestbodies;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class PetRequest {
  @JsonProperty("name")
  private String name;

  @JsonProperty("typeId")
  private int petTypeId;

  @JsonProperty("birthDate")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  public PetRequest() {
  }

  public PetRequest(String name, LocalDate birthDate, int petTypeId) {
    this.name = name;
    this.birthDate = birthDate;
    this.petTypeId = petTypeId;
  }
}
