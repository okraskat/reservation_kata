package io.github.okraskat.room.reservation.exceptions.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class CustomerInvalidEmailAddressException extends RuntimeException {

    public CustomerInvalidEmailAddressException(String email) {
        super(String.format("Invalid email address: %s", email));
    }

}
