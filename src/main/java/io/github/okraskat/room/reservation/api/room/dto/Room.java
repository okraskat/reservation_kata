package io.github.okraskat.room.reservation.api.room.dto;

import lombok.Data;

@Data
public class Room {

	private Long roomId;

	private String roomName;

	private String hotelName;

	private String dailyPrice;
}
