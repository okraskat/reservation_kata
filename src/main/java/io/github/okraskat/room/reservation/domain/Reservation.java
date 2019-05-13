package io.github.okraskat.room.reservation.domain;

import lombok.Value;

import java.time.LocalDate;

@Value
public class Reservation {

    private final long id;

    private final Room room;

    private final Customer customer;

    private final LocalDate reservationStart;

    private final LocalDate reservationEnd;
}
