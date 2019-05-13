package io.github.okraskat.room.reservation.api.room.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SearchRoomsRequest {

	private Integer pageNumber;

	private Integer pageSize;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate periodFrom;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate periodTo;

	private String city;

	private BigDecimal dailyPriceFrom;

	private BigDecimal dailyPriceTo;

}
