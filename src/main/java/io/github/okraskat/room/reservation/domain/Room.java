package io.github.okraskat.room.reservation.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Room {
    private final Long roomId;

    private final String roomName;

    private final Hotel hotel;

    private final BigDecimal dailyPrice;

}
