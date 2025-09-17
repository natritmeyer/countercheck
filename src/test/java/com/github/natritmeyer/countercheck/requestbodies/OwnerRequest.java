package com.github.natritmeyer.countercheck.requestbodies;

import com.github.natritmeyer.countercheck.domain.Pet;
import java.util.List;

public record OwnerRequest(String firstName, String lastName, String address, String city, String telephone) {
}
