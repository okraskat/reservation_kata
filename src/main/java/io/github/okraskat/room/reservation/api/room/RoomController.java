package io.github.okraskat.room.reservation.api.room;

import io.github.okraskat.room.reservation.api.room.dto.FoundRooms;
import io.github.okraskat.room.reservation.api.room.dto.Room;
import io.github.okraskat.room.reservation.api.room.dto.SearchRoomsRequest;
import io.github.okraskat.room.reservation.repository.entities.transformers.RoomEntityTransformer;
import io.github.okraskat.room.reservation.service.RoomService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController("/rooms")
class RoomController {
    private final RoomService roomService;
    private final RoomEntityTransformer roomEntityTransformer;

    @Autowired
    RoomController(RoomService roomService, RoomEntityTransformer roomEntityTransformer) {
        this.roomService = roomService;
        this.roomEntityTransformer = roomEntityTransformer;
    }

    @GetMapping
    FoundRooms searchRooms(SearchRoomsRequest roomQuery) {
        Range<LocalDate> dateRange = createDateRange(roomQuery);
        Range<BigDecimal> priceRange = createPriceRange(roomQuery);
        List<Room> rooms = roomService.searchRooms(dateRange, priceRange, roomQuery.getCity(), createPageRequest(roomQuery))
                .stream()
                .map(roomEntityTransformer::toDto)
                .collect(Collectors.toList());
        return new FoundRooms(rooms);
    }

    private Range<BigDecimal> createPriceRange(SearchRoomsRequest roomQuery) {
        BigDecimal dailyPriceFrom = ObjectUtils.defaultIfNull(roomQuery.getDailyPriceFrom(), BigDecimal.ZERO);
        BigDecimal dailyPriceTo = ObjectUtils.defaultIfNull(roomQuery.getDailyPriceTo(), BigDecimal.valueOf(Long.MAX_VALUE));
        return Range.between(dailyPriceFrom, dailyPriceTo);
    }

    private Range<LocalDate> createDateRange(SearchRoomsRequest roomQuery) {
        LocalDate fromInclusive = ObjectUtils.defaultIfNull(roomQuery.getPeriodFrom(), LocalDate.MIN);
        LocalDate toInclusive = ObjectUtils.defaultIfNull(roomQuery.getPeriodTo(), LocalDate.MAX);
        return Range.between(fromInclusive, toInclusive, Comparator.naturalOrder());
    }

    private PageRequest createPageRequest(SearchRoomsRequest roomQuery) {
        int pageNumber = ObjectUtils.defaultIfNull(roomQuery.getPageNumber(), 0);
        int pageSize = ObjectUtils.defaultIfNull(roomQuery.getPageSize(), 100);
        return PageRequest.of(pageNumber, pageSize);
    }
}
