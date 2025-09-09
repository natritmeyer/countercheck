package com.github.natritmeyer.countercheck.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Vet {
  @JsonProperty("id")
  private int id;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("specialties")
  private List<VetSpecialty> specialties;

  @JsonProperty("nrOfSpecialties")
  private int numberOfSpecialties;

  public Vet() {}

  public static class VetSpecialty {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    public VetSpecialty() {}
  }
}
