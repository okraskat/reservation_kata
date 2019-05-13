package io.github.okraskat.room.reservation.exceptions.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Calling not existing reservation")
public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(long reservationId) {
        super(String.format("Reservation with id: %d does not exist.", reservationId));
    }

}
