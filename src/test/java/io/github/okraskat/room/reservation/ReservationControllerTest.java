package io.github.okraskat.room.reservation;

import io.github.okraskat.room.reservation.api.reservation.dto.CreatedReservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Configuration
public class ReservationControllerTest {

	private RestTemplate restTemplate = new RestTemplateBuilder().build();

	@LocalServerPort
	private int port;

	@Test
	public void shouldReserveRoomInHotel() {
		// given
		ReservationFactory reservationFactory = new ReservationFactory(restTemplate, port);
		LocalDate dateFrom = parse("2018-06-10");
		LocalDate dateTo = parse("2018-06-20");

		// when
		CreatedReservation createdReservation = reservationFactory.makeReservation(dateFrom, dateTo);

		// then
		assertThat(createdReservation).isNotNull();
		assertThat(createdReservation.getId()).isGreaterThanOrEqualTo(1);
	}

	@Test(expected = HttpClientErrorException.Conflict.class)
	public void shouldThrowExceptionWhenRoomIsAlreadyReserved() {
		// given
		ReservationFactory reservationFactory = new ReservationFactory(restTemplate, port);
		LocalDate dateFrom = parse("2018-06-01");
		LocalDate dateTo = parse("2018-06-10");

		// when
		CreatedReservation createdReservation = reservationFactory.makeReservation(dateFrom, dateTo);

		// then
		assertThat(createdReservation).isNotNull();
		assertThat(createdReservation.getId()).isGreaterThanOrEqualTo(1);

		//and
		reservationFactory.makeReservation(dateFrom, dateTo);
	}

	@Test
	public void shouldCancelReservation() {
		// given
		ReservationFactory reservationFactory = new ReservationFactory(restTemplate, port);
		LocalDate dateFrom = parse("2018-06-21");
		LocalDate dateTo = parse("2018-06-30");

		// when
		CreatedReservation resultDto = reservationFactory.makeReservation(dateFrom, dateTo);

		// then
		assertThat(resultDto).isNotNull();
		assertThat(resultDto.getId()).isGreaterThanOrEqualTo(1);

		//and
		restTemplate.delete(String.format("http://localhost:%d/reservations/%d/cancel", port, resultDto.getId()));
	}

}
