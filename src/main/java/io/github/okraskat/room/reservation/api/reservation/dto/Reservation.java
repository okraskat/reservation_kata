package io.github.okraskat.room.reservation.api.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    private long id;

    private long roomId;

    private long customerId;

    private LocalDate dateFrom;

    private LocalDate dateTo;
}
