package io.github.okraskat.room.reservation.repository.specificiation;

import io.github.okraskat.room.reservation.repository.entities.Reservation;
import io.github.okraskat.room.reservation.repository.entities.Room;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;

@AllArgsConstructor
public class FindRoomByPeriod implements Specification<Room> {

	private final LocalDate dateFrom;
	private final LocalDate dateTo;

	@Override
	public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (ObjectUtils.anyNotNull(dateFrom, dateTo)) {
			Subquery<Room> subQuery = query.subquery(Room.class);
			Root<Reservation> reservationEntityRoot = subQuery.from(Reservation.class);

			Join<Reservation, Room> reservationSubJoin = reservationEntityRoot.join(Room.ENTITY_NAME, JoinType.LEFT);
			Predicate dateFromPredicate = criteriaBuilder.greaterThanOrEqualTo(reservationEntityRoot.get(Reservation.DATE_FROM), dateTo);
			Predicate dateToPredicate = criteriaBuilder.greaterThanOrEqualTo(reservationEntityRoot.get(Reservation.DATE_TO), dateFrom);

			Predicate orPredicate = criteriaBuilder.or(dateFromPredicate, dateToPredicate);
			subQuery.select(reservationSubJoin).where(orPredicate);
			return criteriaBuilder.not(criteriaBuilder.in(root).value(subQuery));
		}
		return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
	}
}
