package io.github.okraskat.room.reservation.exceptions.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class CustomerEmptyFieldException extends RuntimeException {

    public CustomerEmptyFieldException(String fieldName) {
        super(String.format("Customer field: %s is empty", fieldName));
    }

}
