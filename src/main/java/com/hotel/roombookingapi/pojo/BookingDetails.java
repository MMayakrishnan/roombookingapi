package com.hotel.roombookingapi.pojo;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookingDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reservationId;
	@Temporal(TemporalType.DATE)
	private Date arrival;
	@Temporal(TemporalType.DATE)
	private Date departure;
	@ManyToOne
	@JoinColumn(name="room_no")
	private Room room;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="registrationId")
	private User user;
	private int numberOfGuests;
	@OneToMany(cascade = CascadeType.ALL,
	            fetch = FetchType.LAZY
	          )
	@JoinColumn(name = "reservation_id")
	private Set<CustomerInfo> customers = new HashSet<>();
	public BookingDetails(Date arrival, Date departure, Room room, User user, int numberOfGuests,
			Set<CustomerInfo> customers) {
		super();
		this.arrival = arrival;
		this.departure = departure;
		this.room = room;
		this.user = user;
		this.numberOfGuests = numberOfGuests;
		this.customers = customers;
	}
	
	
}

