package io.github.okraskat.room.reservation.service;

import io.github.okraskat.room.reservation.exceptions.customer.CustomerNotFoundException;
import io.github.okraskat.room.reservation.exceptions.reservation.ReservationAlreadyExistsException;
import io.github.okraskat.room.reservation.exceptions.reservation.ReservationNotFoundException;
import io.github.okraskat.room.reservation.exceptions.room.RoomNotFoundException;
import io.github.okraskat.room.reservation.repository.CustomerRepository;
import io.github.okraskat.room.reservation.repository.ReservationRepository;
import io.github.okraskat.room.reservation.repository.RoomRepository;
import io.github.okraskat.room.reservation.repository.entities.Customer;
import io.github.okraskat.room.reservation.repository.entities.Reservation;
import io.github.okraskat.room.reservation.repository.entities.Room;
import io.github.okraskat.room.reservation.repository.entities.transformers.ReservationEntityTransformer;
import io.github.okraskat.room.reservation.repository.specificiation.FindReservationByPeriod;
import io.github.okraskat.room.reservation.repository.specificiation.FindReservationByRoomId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationEntityTransformer reservationEntityTransformer;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationEntityTransformer reservationEntityTransformer,
                              CustomerRepository customerRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationEntityTransformer = reservationEntityTransformer;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            reservationRepository.deleteById(reservationId);
        } else {
            throw new ReservationNotFoundException(reservationId);
        }
    }

    @Transactional(readOnly = true)
    public List<io.github.okraskat.room.reservation.domain.Reservation> findReservationsByCustomer(Long customerId) {
        return reservationRepository.findByCustomerId(customerId)
                .stream()
                .map(reservationEntityTransformer::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public long createReservation(long customerId, long roomId, LocalDate dateFrom, LocalDate dateTo) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
        List<Reservation> byRoomIdAndPeriods = reservationRepository.findAll(createSpecification(roomId, dateFrom, dateTo));

        if (byRoomIdAndPeriods.size() > 0) {
            throw new ReservationAlreadyExistsException();
        }
        Reservation reservationEntity = new Reservation();
        reservationEntity.setCustomer(customer);
        reservationEntity.setRoom(room);
        reservationEntity.setDateFrom(dateFrom);
        reservationEntity.setDateTo(dateTo);
        reservationEntity = reservationRepository.save(reservationEntity);
        return reservationEntity.getId();
    }

    private Specification<Reservation> createSpecification(long roomId, LocalDate dateFrom, LocalDate dateTo) {
        return Specification.where(new FindReservationByRoomId(roomId))
                .and(new FindReservationByPeriod(dateFrom, dateTo));
    }
}
