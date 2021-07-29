package com.hotel.roombookingapi.pojo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Amenities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int amenetiesId;
	private String amenitiesInfo;
	

}
