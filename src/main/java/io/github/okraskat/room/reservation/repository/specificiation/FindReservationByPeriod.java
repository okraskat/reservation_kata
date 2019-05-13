package io.github.okraskat.room.reservation.repository.specificiation;

import io.github.okraskat.room.reservation.repository.entities.Reservation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

@AllArgsConstructor
public class FindReservationByPeriod implements Specification<Reservation> {

	private final LocalDate dateFrom;
	private final LocalDate dateTo;

	@Override
	public Predicate toPredicate(Root<Reservation> reservation, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (ObjectUtils.anyNotNull(dateFrom, dateTo)) {
			Predicate dateFromPredicate = criteriaBuilder.greaterThanOrEqualTo(reservation.get(Reservation.DATE_FROM), dateTo);
			Predicate dateToPredicate = criteriaBuilder.greaterThanOrEqualTo(reservation.get(Reservation.DATE_TO), dateFrom);

			return criteriaBuilder.or(dateFromPredicate, dateToPredicate);
		}
		return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
	}
}
