package io.github.okraskat.room.reservation.repository.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "reservation")
@Data
@Table(name = "reservation")
public class Reservation {
	public static final String DATE_FROM = "dateFrom";
	public static final String DATE_TO = "dateTo";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_sequence")
	@SequenceGenerator(name = "reservation_sequence", sequenceName = "reservation_seq")
	private Long id;

	@ManyToOne(targetEntity = Room.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "room_id")
	private Room room;

	@Column(name = "date_from")
	private LocalDate dateFrom;

	@Column(name = "date_to")
	private LocalDate dateTo;

	@ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private Customer customer;

}
