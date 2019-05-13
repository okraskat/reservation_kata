package io.github.okraskat.room.reservation.repository.specificiation;

import io.github.okraskat.room.reservation.repository.entities.Reservation;
import io.github.okraskat.room.reservation.repository.entities.Room;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@AllArgsConstructor
public class FindReservationByRoomId implements Specification<Reservation> {

	private final long roomId;

	@Override
	public Predicate toPredicate(Root<Reservation> reservation, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		Join<Room, Reservation> room = reservation.join(Room.ENTITY_NAME);
		return criteriaBuilder.equal(room.get("id"), roomId);
	}

}
