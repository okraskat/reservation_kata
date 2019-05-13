package io.github.okraskat.room.reservation.service;

import io.github.okraskat.room.reservation.domain.Room;
import io.github.okraskat.room.reservation.repository.RoomRepository;
import io.github.okraskat.room.reservation.repository.entities.transformers.RoomEntityTransformer;
import io.github.okraskat.room.reservation.repository.specificiation.FindRoomByHotelCity;
import io.github.okraskat.room.reservation.repository.specificiation.FindRoomByPeriod;
import io.github.okraskat.room.reservation.repository.specificiation.FindRoomByPrice;
import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomEntityTransformer roomEntityTransformer;

    @Autowired
    public RoomService(RoomRepository roomRepository, RoomEntityTransformer roomEntityTransformer) {
        this.roomRepository = roomRepository;
        this.roomEntityTransformer = roomEntityTransformer;
    }

    public List<Room> searchRooms(Range<LocalDate> periodRange, Range<BigDecimal> priceRange, String city, PageRequest pageRequest) {
        return roomRepository.findAll(createSearchSpecification(periodRange, priceRange, city), pageRequest)
                .get()
                .map(roomEntityTransformer::toDomain)
                .collect(Collectors.toList());
    }

    private Specification<io.github.okraskat.room.reservation.repository.entities.Room> createSearchSpecification(
            Range<LocalDate> periodRange, Range<BigDecimal> priceRange, String city) {
        return Specification
                .where(new FindRoomByHotelCity(city))
                .and(createPeriodFilter(periodRange))
                .and(new FindRoomByPrice(priceRange.getMinimum(), priceRange.getMaximum()));
    }

    private FindRoomByPeriod createPeriodFilter(Range<LocalDate> periodRange) {
        LocalDate minimum = periodRange.getMinimum() == LocalDate.MIN ? null : periodRange.getMinimum();
        LocalDate maximum = periodRange.getMaximum() == LocalDate.MAX ? null : periodRange.getMaximum();
        return new FindRoomByPeriod(minimum, maximum);
    }
}
