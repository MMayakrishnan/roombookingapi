package com.hotel.roombookingapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AvailabilityStatistics {

	private long total;
	private String roomType;
}
