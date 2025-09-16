package com.github.natritmeyer.countercheck.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public class Owner {
  @JsonProperty("id")
  private int id;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("address")
  private String address;

  @JsonProperty("city")
  private String city;

  @JsonProperty("telephone")
  private String telephone;

  @JsonProperty("pets")
  private List<Pet> pets;

  @Override
  public String toString() {
    return "Owner{"
        + "id=" + id
        + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\''
        + ", address='" + address + '\''
        + ", city='" + city + '\''
        + ", telephone='" + telephone + '\''
        + ", pets=" + pets
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Owner owner = (Owner) o;
    return id == owner.id && Objects.equals(firstName, owner.firstName) && Objects.equals(lastName, owner.lastName) && Objects.equals(address, owner.address) && Objects.equals(city, owner.city) && Objects.equals(telephone, owner.telephone) && Objects.equals(pets, owner.pets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, city, telephone, pets);
  }
}
