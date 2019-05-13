package io.github.okraskat.room.reservation.api.customer.register.dto;

import lombok.Data;

@Data
public class RegisterCustomerRequest {
    private String firstName;
    private String lastName;
    private String email;
}
