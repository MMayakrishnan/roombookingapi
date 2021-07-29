package com.hotel.roombookingapi.pojo;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CostAndFacilities {

	private BigDecimal price;
	private List<String> facilities;
	
}
