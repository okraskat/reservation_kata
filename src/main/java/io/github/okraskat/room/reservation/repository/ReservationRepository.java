package io.github.okraskat.room.reservation.repository;

import io.github.okraskat.room.reservation.repository.entities.Reservation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

	@Query(value = "select r from reservation r where r.room.id = :roomId and " +
			"((:dateTo < r.dateFrom) or (:dateFrom < r.dateTo))")
	List<Reservation> findByRoomIdAndPeriods(@Param("roomId") Long roomId, @Param("dateFrom") LocalDate dateFrom,
                                                   @Param("dateTo") LocalDate dateTo);

	List<Reservation> findByCustomerId(Long customerId);
}
