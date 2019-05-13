package io.github.okraskat.room.reservation;

import io.github.okraskat.room.reservation.api.room.dto.FoundRooms;
import io.github.okraskat.room.reservation.api.room.dto.Room;
import org.springframework.web.client.RestTemplate;

class RoomProvider {
    private final RestTemplate restTemplate;
    private final int port;

    public RoomProvider(RestTemplate restTemplate, int port) {
        this.restTemplate = restTemplate;
        this.port = port;
    }

    Room getRoom() {
        FoundRooms rooms = this.restTemplate.getForObject(
                String.format("http://localhost:%d/rooms?pageNumber=0&pageSize=100", port),
                FoundRooms.class);

        assert rooms != null;
        return rooms.getRooms()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("There is no available rooms"));
    }
}
