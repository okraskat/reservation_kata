package io.github.okraskat.room.reservation.exceptions.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(long customerId) {
        super(String.format("Customer with id: %d not found.", customerId));
    }

}
