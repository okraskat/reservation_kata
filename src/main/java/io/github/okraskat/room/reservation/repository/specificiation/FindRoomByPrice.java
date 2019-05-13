package io.github.okraskat.room.reservation.repository.specificiation;

import io.github.okraskat.room.reservation.repository.entities.Room;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
public class FindRoomByPrice implements Specification<Room> {
	private final BigDecimal dailyPriceFrom;
	private final BigDecimal dailyPriceTo;

	@Override
	public Predicate toPredicate(Root<Room> room, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if (ObjectUtils.anyNotNull(dailyPriceFrom, dailyPriceTo)) {
			BigDecimal dailyPriceFromToSearch = Optional.ofNullable(dailyPriceFrom)
					.orElse(BigDecimal.ZERO);

			BigDecimal dailyPriceToToSearch = Optional.ofNullable(dailyPriceTo)
					.orElse(BigDecimal.valueOf(Long.MAX_VALUE));

			return criteriaBuilder.between(room.get(Room.DAILY_PRICE), dailyPriceFromToSearch, dailyPriceToToSearch);
		}

		return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
	}
}
