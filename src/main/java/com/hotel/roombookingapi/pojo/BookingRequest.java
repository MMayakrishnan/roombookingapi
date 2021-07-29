package com.hotel.roombookingapi.pojo;

import java.util.Date;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingRequest {

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date arrival;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date departure;
	@NotEmpty
	private String roomType;
	@NotEmpty
	private int registrationId;
	@NotEmpty
	private Set<CustomerInfo> customers; 

}
