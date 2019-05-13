package io.github.okraskat.room.reservation.repository.entities.transformers;

import io.github.okraskat.room.reservation.domain.Customer;
import io.github.okraskat.room.reservation.domain.Room;
import io.github.okraskat.room.reservation.repository.entities.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationEntityTransformer {
    private final RoomEntityTransformer roomEntityTransformer;
    private final CustomerEntityTransformer customerEntityTransformer;

    public ReservationEntityTransformer(RoomEntityTransformer roomEntityTransformer, CustomerEntityTransformer customerEntityTransformer) {
        this.roomEntityTransformer = roomEntityTransformer;
        this.customerEntityTransformer = customerEntityTransformer;
    }

    public io.github.okraskat.room.reservation.domain.Reservation toDomain(Reservation entity) {
        Room room = roomEntityTransformer.toDomain(entity.getRoom());
        Customer customer = customerEntityTransformer.toDomain(entity.getCustomer());
        return new io.github.okraskat.room.reservation.domain.Reservation(entity.getId(), room, customer, entity.getDateFrom(), entity.getDateTo());
    }
}
