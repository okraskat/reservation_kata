package io.github.okraskat.room.reservation.repository;

import io.github.okraskat.room.reservation.repository.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends PagingAndSortingRepository<Room, Long>, JpaSpecificationExecutor<Room> {

	Page<Room> findAll(Specification<Room> spec, Pageable pageable);

}
