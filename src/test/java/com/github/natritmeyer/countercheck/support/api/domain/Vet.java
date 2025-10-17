package com.github.natritmeyer.countercheck.support.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

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

  public Vet() {
  }

  public Vet(int id, String firstName, String lastName, List<VetSpecialty> specialties, int numberOfSpecialties) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.specialties = specialties;
    this.numberOfSpecialties = numberOfSpecialties;
  }

  @Override
  public String toString() {
    return "Vet{"
        + "id=" + id
        + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\''
        + ", specialties=" + specialties
        + ", numberOfSpecialties=" + numberOfSpecialties
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vet vet = (Vet) o;
    return id == vet.id && numberOfSpecialties == vet.numberOfSpecialties && Objects.equals(firstName, vet.firstName) && Objects.equals(lastName, vet.lastName) && Objects.equals(specialties, vet.specialties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, specialties, numberOfSpecialties);
  }

  public static class VetSpecialty {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    public VetSpecialty() {
    }

    public VetSpecialty(int id, String name) {
      this.id = id;
      this.name = name;
    }

    @Override
    public String toString() {
      return "VetSpecialty{"
          + "id=" + id
          + ", name='" + name + '\''
          + '}';
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      VetSpecialty that = (VetSpecialty) o;
      return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id, name);
    }
  }
}
