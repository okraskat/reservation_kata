package io.github.okraskat.room.reservation.api.reservation.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateReservationRequest {

	private long roomId;

	private long customerId;

	private LocalDate dateFrom;

	private LocalDate dateTo;
}
