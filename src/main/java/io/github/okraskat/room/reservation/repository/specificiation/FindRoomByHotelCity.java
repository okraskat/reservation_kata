package io.github.okraskat.room.reservation.repository.specificiation;

import io.github.okraskat.room.reservation.repository.entities.Hotel;
import io.github.okraskat.room.reservation.repository.entities.Room;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@AllArgsConstructor
public class FindRoomByHotelCity implements Specification<Room> {

	private final String city;

	@Override
	public Predicate toPredicate(Root<Room> room, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (StringUtils.isEmpty(city)) {
			return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
		}
		Join<Hotel, Room> hotel = room.join(Hotel.ENTITY_NAME);
		return criteriaBuilder.equal(hotel.get(Hotel.CITY), city);
	}

}
