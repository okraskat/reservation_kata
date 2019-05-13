package io.github.okraskat.room.reservation.repository.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = Hotel.ENTITY_NAME)
@Data
@Table(name = "hotel")
public class Hotel {

	public static final String ENTITY_NAME = "hotel";
	public static final String CITY = "city";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_sequence")
	@SequenceGenerator(name = "hotel_sequence", sequenceName = "hotel_seq")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = CITY)
	private String city;
}
