package io.github.okraskat.room.reservation.repository.entities.transformers;

import io.github.okraskat.room.reservation.repository.entities.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityTransformer {

    public io.github.okraskat.room.reservation.domain.Customer toDomain(Customer entity) {
        return new io.github.okraskat.room.reservation.domain.Customer(entity.getId(), entity.getFirstName(), entity.getFirstName(), entity.getLastName());
    }
}
