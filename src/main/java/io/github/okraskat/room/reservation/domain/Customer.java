package io.github.okraskat.room.reservation.domain;

import lombok.Value;

@Value
public class Customer {

    private long id;

    private final String firstName;

    private final String lastName;

    private final String email;

}
