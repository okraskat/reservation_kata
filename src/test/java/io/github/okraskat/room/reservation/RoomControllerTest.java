package io.github.okraskat.room.reservation;

import io.github.okraskat.room.reservation.api.room.dto.FoundRooms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Configuration
public class RoomControllerTest {

	private RestTemplate restTemplate = new RestTemplateBuilder().build();

	@LocalServerPort
	private int port;

	@Test
	public void shouldFindRooms() {
		// given
        // when
		FoundRooms rooms = restTemplate.getForObject(
				String.format("http://localhost:%d/rooms" +
						"?pageNumber=0&pageSize=100&city=Warszawa&dailyPriceFrom=100.0&dailyPriceTo=200.10" +
						"&periodFrom=%s&periodTo=%s", port, "2019-06-01", "2019-06-10"),
				FoundRooms.class);

		// then
		assertThat(rooms).isNotNull();
		assertThat(rooms.getRooms().size()).isGreaterThanOrEqualTo(1);
	}

	@Test
	public void shouldFindLessRoomsAfterReservation() {
		// given
		LocalDate dateFrom = LocalDate.parse("2019-06-01");
		LocalDate dateTo = LocalDate.parse("2019-06-10");

		FoundRooms roomsBeforeReservation = restTemplate.getForObject(
				String.format("http://localhost:%d/rooms" +
						"?pageNumber=0&pageSize=100&city=Warszawa&dailyPriceFrom=100.0&dailyPriceTo=200.10" +
						"&periodFrom=%s&periodTo=%s", port, dateFrom, dateTo),
				FoundRooms.class);

		new ReservationFactory(restTemplate, port).makeReservation(dateFrom, dateTo);

		// when
		FoundRooms roomsAfterReservation = restTemplate.getForObject(
				String.format("http://localhost:%d/rooms" +
						"?pageNumber=0&pageSize=100&city=Warszawa&dailyPriceFrom=100.0&dailyPriceTo=200.10" +
							"&periodFrom=%s&periodTo=%s", port, dateFrom, dateTo),
				FoundRooms.class);

		// then
		assertThat(roomsAfterReservation).isNotNull();
		assertThat(roomsBeforeReservation).isNotNull();
		assertThat(roomsBeforeReservation.getRooms().size()).isGreaterThan(roomsAfterReservation.getRooms().size());
	}

}
