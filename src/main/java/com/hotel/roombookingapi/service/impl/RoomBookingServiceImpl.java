package com.hotel.roombookingapi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hotel.roombookingapi.exception.IncorrectUserException;
import com.hotel.roombookingapi.exception.ResourceNotAvailableException;
import com.hotel.roombookingapi.pojo.AvailabilityStatistics;
import com.hotel.roombookingapi.pojo.BookingDetails;
import com.hotel.roombookingapi.pojo.BookingRequest;
import com.hotel.roombookingapi.pojo.Room;
import com.hotel.roombookingapi.pojo.User;
import com.hotel.roombookingapi.repository.BookingDetailsRepository;
import com.hotel.roombookingapi.repository.RoomRepository;
import com.hotel.roombookingapi.repository.RoomTypeRepository;
import com.hotel.roombookingapi.service.RoomBookingService;

@Service
public class RoomBookingServiceImpl implements RoomBookingService{

	
	private static Logger logger=LoggerFactory.getLogger(RoomBookingServiceImpl.class);
	
	@Autowired
	BookingDetailsRepository bookingDetailsRepository;

	@Autowired
	RoomRepository roomRepository;
	
	@Value("${authentication_api_url}")
	private String authenticationAPIUrl;

	@Autowired
	RoomTypeRepository roomTypeRepository;
	
	
	@Autowired
	RestTemplate restTemplate;
	
	// queries booking table to see for the rooms booked on the date and provide list of room types that are available for the date
	@Override
	public Set<String> fetchAvailableRoomType(Date arrival, Date depature) {
	
		List<Integer> bookedRoomNo = bookingDetailsRepository.getBookedRooms(new SimpleDateFormat("yyyy-MM-dd").format(arrival), new SimpleDateFormat("yyyy-MM-dd").format(depature));
		logger.info("Room No booked betweeen "+arrival+ "and "+depature + " are "+ bookedRoomNo);
		if(bookedRoomNo.isEmpty()) {
			return roomTypeRepository.getAllRoomTypes();
		}else {
			List<Room> availableRooms=roomRepository.findByRoomNoNotIn(bookedRoomNo);
			logger.info(" Available rooms are "+ availableRooms);
			if(!availableRooms.isEmpty()) {
				Set<String> roomTypes = availableRooms.stream().map(x->x.getRoomType().getRoomType()).collect(Collectors.toSet());
				logger.info("available room types are "+roomTypes);
					return roomTypes;
		}else {
			logger.info("All Rooms booked betweeen "+arrival+ "and "+depature );
			throw new ResourceNotAvailableException("All Rooms are Booked, Please try for a differnt Date");	
		}
	
		}
	
		
	}
	
	
	// queries booking table to see for the room type requested is free for the date and add entry to booking detail for the date 
	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public String checkAndBook(BookingRequest request) {
		List<Room> availableRooms;
		List<Integer> bookedRoomNo = bookingDetailsRepository.getBookedRooms(new SimpleDateFormat("yyyy-MM-dd").format(request.getArrival()), new SimpleDateFormat("yyyy-MM-dd").format(request.getDeparture()));
		logger.info("Room booked betweeen "+request.getArrival()+ "and "+request.getDeparture() + " are "+ bookedRoomNo);;
		if(bookedRoomNo.isEmpty()) {
			availableRooms=roomRepository.findByRoomType_RoomType(request.getRoomType());
		}
		else {
			 availableRooms=roomRepository.findByRoomType_RoomTypeAndRoomNoNotIn(request.getRoomType(),bookedRoomNo);
		}
			if(!availableRooms.isEmpty()) {
				Room room=availableRooms.get(0);
				logger.info("Booking room no "+room.getRoomNo());
				ResponseEntity<User> user=restTemplate.getForEntity(authenticationAPIUrl+request.getRegistrationId(),User.class);
				if(!user.getStatusCode().equals(HttpStatus.OK)) {
					logger.error("User Detail is invalid, registration number passed is "+request.getRegistrationId());
					throw new IncorrectUserException("Registration Id is not valid ");
				}
				BookingDetails booking=new BookingDetails(request.getArrival(), request.getDeparture(), room, user.getBody(), request.getCustomers().size(),request.getCustomers());
				
				BookingDetails bookedRequest=bookingDetailsRepository.save(booking);
				logger.info("Booking success");
				return "Booking Successful with reservation id"+bookedRequest.getReservationId();
		}else {
			logger.error("All rooms booked");
			throw new ResourceNotAvailableException("All Rooms are Booked,  Please try for a differnt Date");
		}
			
	}

	// queries booking table to see for the room type that are free, and gets counts of free room based on room type
	@Override
	public List<AvailabilityStatistics> getRoomStatistics(Date statsDate) {
		logger.info("getting free room statistics "+statsDate);
		List<Integer> bookedRoom = roomRepository.getBookedRooms(new SimpleDateFormat("yyyy-MM-dd").format(statsDate));
		List<AvailabilityStatistics> availableRooms = null;
		if(bookedRoom.isEmpty()) {
			availableRooms=roomRepository.getStats();
		}
		else {
				availableRooms=roomRepository.getStats(bookedRoom);
				if(availableRooms.isEmpty()) 	{
					logger.error("All rooms booked");
					throw new ResourceNotAvailableException("All Rooms are Booked");
				}
		}
		logger.info(" free room statistics generated");
		return availableRooms;
	}

}
