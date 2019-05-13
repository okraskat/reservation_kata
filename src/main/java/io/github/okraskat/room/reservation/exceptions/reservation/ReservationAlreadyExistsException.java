package io.github.okraskat.room.reservation.exceptions.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="No available room at requested period")
public class ReservationAlreadyExistsException extends RuntimeException {
}
