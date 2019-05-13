package io.github.okraskat.room.reservation.repository.entities.transformers;

import io.github.okraskat.room.reservation.api.room.dto.Room;
import io.github.okraskat.room.reservation.domain.Hotel;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;

@Component
public class RoomEntityTransformer {
    public Room toDto(io.github.okraskat.room.reservation.domain.Room domain) {
        Room dto = new Room();
        dto.setRoomId(domain.getRoomId());
        dto.setHotelName(domain.getHotel().getName());
        dto.setRoomName(domain.getRoomName());
        dto.setDailyPrice(NumberFormat.getInstance().format(domain.getDailyPrice()));
        return dto;
    }

    public io.github.okraskat.room.reservation.domain.Room toDomain(io.github.okraskat.room.reservation.repository.entities.Room entity) {
        return new io.github.okraskat.room.reservation.domain.Room(entity.getId(), entity.getName(), new Hotel(entity.getHotel().getName()), entity.getDailyPrice());
    }
}
