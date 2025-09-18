package com.github.natritmeyer.countercheck.requestbodies;

public record OwnerRequest(String firstName, String lastName, String address, String city, String telephone) {
}
