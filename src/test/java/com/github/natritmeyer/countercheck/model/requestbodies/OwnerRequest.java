package com.github.natritmeyer.countercheck.model.requestbodies;

public record OwnerRequest(String firstName, String lastName, String address, String city, String telephone) {
}
