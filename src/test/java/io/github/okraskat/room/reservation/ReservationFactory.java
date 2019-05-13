package io.github.okraskat.room.reservation;

import io.github.okraskat.room.reservation.api.reservation.dto.CreateReservationRequest;
import io.github.okraskat.room.reservation.api.reservation.dto.CreatedReservation;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

class ReservationFactory {

	private final RestTemplate restTemplate;
	private final int port;

	ReservationFactory(RestTemplate restTemplate, int port) {
		this.restTemplate = restTemplate;
		this.port = port;
	}

	CreatedReservation makeReservation(LocalDate dateFrom, LocalDate dateTo) {
		Long customerId = new CustomerFactory(restTemplate, port).registerCustomer().getCustomerId();
		Long roomId = new RoomProvider(restTemplate, port).getRoom().getRoomId();
		CreateReservationRequest newReservation = createNewReservation(roomId, customerId, dateFrom, dateTo);
		return makeReservation(newReservation);
	}

	private CreatedReservation makeReservation(CreateReservationRequest newReservation) {
		return restTemplate.postForObject(
				String.format("http://localhost:%d/reservations", port),
				newReservation,
				CreatedReservation.class);
	}

	private CreateReservationRequest createNewReservation(Long roomId, Long customerId, LocalDate dateFrom, LocalDate dateTo) {
		CreateReservationRequest createReservationRequest = new CreateReservationRequest();
		createReservationRequest.setRoomId(roomId);
		createReservationRequest.setCustomerId(customerId);
		createReservationRequest.setDateFrom(dateFrom);
		createReservationRequest.setDateTo(dateTo);
		return createReservationRequest;
	}

}