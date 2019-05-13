package io.github.okraskat.room.reservation.exceptions.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class CustomerEmailAlreadyExistsException extends RuntimeException {

    public CustomerEmailAlreadyExistsException(String customerEmail) {
        super(String.format("Customer with email address %s already exists", customerEmail));
    }

}
