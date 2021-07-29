package com.hotel.roombookingapi.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.roombookingapi.pojo.AvailabilityStatistics;
import com.hotel.roombookingapi.pojo.BookingRequest;
import com.hotel.roombookingapi.service.RoomBookingService;

@RestController
@RequestMapping("/RoomBookingAPI")
public class RoomBookingContoller {

	@Autowired
	RoomBookingService roomBookingService;
	
	private static Logger logger=LoggerFactory.getLogger(RoomBookingContoller.class);
	
	
	// retrieves all available room types based on check in and check out date 
	@GetMapping("/getAvailableRoomType/{arrival}/{depature}")
	public ResponseEntity<Set<String>> getAvailableRoomType(@PathVariable("arrival") @DateTimeFormat(pattern = "yyyy-MM-dd") Date arrival,@PathVariable("depature") @DateTimeFormat(pattern = "yyyy-MM-dd") Date departure){
		logger.debug("request recieved  for searching available room type between "+arrival +" and "+departure);
		Set<String> rooms=roomBookingService.fetchAvailableRoomType(arrival,departure);
		return new ResponseEntity<Set<String>>(rooms, HttpStatus.OK);
	}
	
	// checks if room is available for the selected room type and books the room 
	@PostMapping("/checkAndBookRooms")
	public ResponseEntity<String> checkAndBookRooms(@RequestBody BookingRequest request) {
		logger.debug("request recieved  for searching available room type between "+request.getArrival()+" and "+request.getDeparture());
		return new ResponseEntity<String>(roomBookingService.checkAndBook(request), HttpStatus.OK);
	}
	
	//retrieves reprot for owners for the available room count based on the room type
	@GetMapping("/getAvailabilityStatistics/{statsDate}")
	public List<AvailabilityStatistics> getAvaialableRooms(@PathVariable("statsDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date statsDate) {
		logger.debug("request recieved  for getting available room status for "+statsDate);
		return roomBookingService.getRoomStatistics(statsDate);
	}
}
