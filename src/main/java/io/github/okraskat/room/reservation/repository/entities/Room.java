package io.github.okraskat.room.reservation.repository.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = Room.ENTITY_NAME)
@Data
@Table(name = "room")
public class Room {

	public static final String ENTITY_NAME = "room";
	public static final String DAILY_PRICE = "dailyPrice";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_sequence")
	@SequenceGenerator(name = "room_sequence", sequenceName = "room_seq")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "daily_price")
	private BigDecimal dailyPrice;

	@ManyToOne(targetEntity = Hotel.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	@OneToMany(targetEntity = Reservation.class, fetch = FetchType.LAZY)
	private List<Reservation> reservations;

	@Version
	@Column(name = "version")
	private int version;
}
