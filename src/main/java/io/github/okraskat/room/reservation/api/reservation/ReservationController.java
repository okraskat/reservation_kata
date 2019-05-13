package io.github.okraskat.room.reservation.api.reservation;

import io.github.okraskat.room.reservation.api.reservation.dto.CreateReservationRequest;
import io.github.okraskat.room.reservation.api.reservation.dto.CreatedReservation;
import io.github.okraskat.room.reservation.api.reservation.dto.FoundReservations;
import io.github.okraskat.room.reservation.api.reservation.dto.Reservation;
import io.github.okraskat.room.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    CreatedReservation createReservation(@RequestBody CreateReservationRequest reservationRequest) {
        long reservationId = reservationService.createReservation(reservationRequest.getCustomerId(), reservationRequest.getRoomId(),
                reservationRequest.getDateFrom(), reservationRequest.getDateTo());
        return new CreatedReservation(reservationId);
    }

    @DeleteMapping("/{id}/cancel")
    ResponseEntity cancelReservation(@PathVariable long id) {
        reservationService.cancelReservation(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    FoundReservations findAllReservationByCustomerId(@RequestParam("customerId") Long customerId) {
        if (customerId == null) {
            return new FoundReservations(Collections.emptyList());
        }
        List<Reservation> reservations = reservationService.findReservationsByCustomer(customerId)
                .stream()
                .map(r -> new Reservation(r.getId(), r.getRoom().getRoomId(), r.getCustomer().getId(), r.getReservationStart(), r.getReservationEnd()))
                .collect(Collectors.toList());
        return new FoundReservations(reservations);
    }
}
