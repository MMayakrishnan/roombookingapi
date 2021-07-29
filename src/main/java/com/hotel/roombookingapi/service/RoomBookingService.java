package com.hotel.roombookingapi.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.hotel.roombookingapi.pojo.AvailabilityStatistics;
import com.hotel.roombookingapi.pojo.BookingRequest;

@Service
public interface RoomBookingService {

	Set<String> fetchAvailableRoomType(Date arrival, Date depature);

	String checkAndBook(BookingRequest request);

	List<AvailabilityStatistics> getRoomStatistics(Date statsDate);
}
