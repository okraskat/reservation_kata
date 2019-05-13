package io.github.okraskat.room.reservation.api.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoundRooms {
	private List<Room> rooms;
}
