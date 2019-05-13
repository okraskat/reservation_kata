package io.github.okraskat.room.reservation.exceptions.room;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {
	public RoomNotFoundException(Long roomId) {
		super(String.format("Room not found %s", roomId));
	}
}
