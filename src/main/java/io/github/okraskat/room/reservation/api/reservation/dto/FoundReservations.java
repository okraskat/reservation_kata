package io.github.okraskat.room.reservation.api.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoundReservations {

    private List<Reservation> reservations;

}
