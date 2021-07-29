package com.hotel.roombookingapi.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.hotel.roombookingapi.exception.ResourceNotAvailableException;
import com.hotel.roombookingapi.pojo.AvailabilityStatistics;
import com.hotel.roombookingapi.pojo.BookingDetails;
import com.hotel.roombookingapi.pojo.BookingRequest;
import com.hotel.roombookingapi.pojo.Room;
import com.hotel.roombookingapi.pojo.RoomType;
import com.hotel.roombookingapi.repository.BookingDetailsRepository;
import com.hotel.roombookingapi.repository.RoomRepository;
import com.hotel.roombookingapi.repository.RoomTypeRepository;

public class RoomBookingServiceImplTest {

	  @InjectMocks
	  RoomBookingServiceImpl roomBookingServiceImpl;
	     
	    @Mock
		BookingDetailsRepository bookingDetailsRepository;

	    @Mock
		RoomRepository roomRepository;
		

	    @Mock
		RoomTypeRepository roomTypeRepository;
	    

	    @BeforeEach
	    public  void init() {
	        MockitoAnnotations.initMocks(this);
	    }
	    
	    @Test
	    public  void fetchAllAvailableRoomTypeAllAvailableHappyPathTest() {
	    	
	    	List<Integer> bookedRoomlist = new ArrayList<Integer>();
	    	Set<String> roomTypeList = new HashSet();
	    	roomTypeList.add("Deluxe");
	     	roomTypeList.add("Luxury");
	    	when(bookingDetailsRepository.getBookedRooms(Mockito.any(),Mockito.any())).thenReturn(bookedRoomlist);
	    	when(roomTypeRepository.getAllRoomTypes()).thenReturn(roomTypeList);
	
	    Set<String> result=roomBookingServiceImpl.fetchAvailableRoomType(new Date(), new Date());
	    assertEquals(2, result.size());
			
		}
	    

	    @Test
	    public  void fetchAllAvailableRoomTypeFewAvailableHappyPathTest() {
	    	
	    	List<Integer> bookedRoomlist = new ArrayList<Integer>();
	    	bookedRoomlist.add(8);
	    	 List<Room> roomList= new ArrayList<Room>();
	    	Room room=new Room();
	    	RoomType roomType1=new RoomType();
	    	roomType1.setPrice( new BigDecimal(25));
	    	roomType1.setRoomType("Deluxe");
	    	room.setRoomType(roomType1);
	    	Room room2=new Room();
	    	RoomType roomType2=new RoomType();
	    	roomType1.setPrice( new BigDecimal(25));
	    	roomType1.setRoomType("Deluxe");
	    	room2.setRoomType(roomType2);
	    	roomList.add(room);
	    	roomList.add(room2);
	    	when(bookingDetailsRepository.getBookedRooms(Mockito.any(),Mockito.any())).thenReturn(bookedRoomlist);
	    	when(roomRepository.findByRoomNoNotIn(Mockito.any())).thenReturn(roomList);
	
	    	Set<String> result=roomBookingServiceImpl.fetchAvailableRoomType(new Date(), new Date());
	    	assertEquals(2, result.size());
			
		}
	    
	  
	    
	    @Test
	    public void fetchAllAvailableRoomTypeNoRoomAvailableExceptionPathTest() {
	    	
	    	List<Integer> bookedRoomlist = new ArrayList<Integer>();
	    	bookedRoomlist.add(8);
	    	 List<Room> roomList= new ArrayList<Room>();
	    	when(bookingDetailsRepository.getBookedRooms(Mockito.any(),Mockito.any())).thenReturn(bookedRoomlist);
	    	when(roomRepository.findByRoomNoNotIn(Mockito.any())).thenReturn(roomList);
	    	try{
	    		roomBookingServiceImpl.fetchAvailableRoomType(new Date(), new Date());
	    		assert false;
	    	}catch(ResourceNotAvailableException ex){
	    		assertEquals(ex.getMessage(),"All Rooms are Booked, Please try for a differnt Date");
	    	}
	    	
			
		}
	    
	    
	    
	    
	    
	    
	    @Test
	    public  void getRoomStatisticsHappyPathTest() {
	    	List<Integer> bookedRoomlist = new ArrayList<Integer>();
	    	bookedRoomlist.add(8);
	    	List<AvailabilityStatistics> availableRooms=new ArrayList<>();
	    	AvailabilityStatistics mockObj=new AvailabilityStatistics();
	    	availableRooms.add(mockObj);
	    	when(roomRepository.getBookedRooms(Mockito.any())).thenReturn(bookedRoomlist);
	    	when(roomRepository.getStats(Mockito.any())).thenReturn(availableRooms);
	    	List<AvailabilityStatistics> result=roomBookingServiceImpl.getRoomStatistics(new Date());
	    	assertEquals(1, result.size());
	    }
	    
	    @Test
	    public  void getRoomStatisticsRoomFullExceptionPathTest() {
	    	List<Integer> bookedRoomlist = new ArrayList<Integer>();
	    	bookedRoomlist.add(8);
	    	List<AvailabilityStatistics> availableRooms=new ArrayList<>();
	    	when(roomRepository.getBookedRooms(Mockito.any())).thenReturn(bookedRoomlist);
	    	when(roomRepository.getStats()).thenReturn(availableRooms);
	    	try {
	    		roomBookingServiceImpl.getRoomStatistics(new Date());
	    		assert false;
	    	}catch(ResourceNotAvailableException ex){
	    		assertEquals("All Rooms are Booked", ex.getMessage());
	    	}
	    }
	    
	    @Test
	    public  void getRoomStatisticsAllRoomExceptionPathTest() {
	    	List<Integer> bookedRoomlist = new ArrayList<Integer>();
	    	List<AvailabilityStatistics> availableRooms=new ArrayList<>();
	    	AvailabilityStatistics mockObj=new AvailabilityStatistics();
	    	availableRooms.add(mockObj);
	    	when(roomRepository.getBookedRooms(Mockito.any())).thenReturn(bookedRoomlist);
	    	when(roomRepository.getStats()).thenReturn(availableRooms);
	    	List<AvailabilityStatistics> result=roomBookingServiceImpl.getRoomStatistics(new Date());
	    	assertEquals(1, result.size());
	    }
	    
	    
	   
	    
//	    @Override
//		public String checkAndBook(BookingRequest request) {
//			List<Room> availableRooms;
//			List<Integer> bookedRoomNo = bookingDetailsRepository.getBookedRooms(new SimpleDateFormat("yyyy-MM-dd").format(request.getArrival()), new SimpleDateFormat("yyyy-MM-dd").format(request.getDeparture()));
//			logger.info("Room booked betweeen "+request.getArrival()+ "and "+request.getDeparture() + " are "+ bookedRoomNo);;
//			if(bookedRoomNo.isEmpty()) {
//				availableRooms=roomRepository.findByRoomType_RoomType(request.getRoomType());
//			}
//			else {
//				 availableRooms=roomRepository.findByRoomType_RoomTypeAndRoomNoNotIn(request.getRoomType(),bookedRoomNo);
//			}
//				if(!availableRooms.isEmpty()) {
//					Room room=availableRooms.get(0);
//					logger.info("Booking room no "+room.getRoomNo());
//					BookingDetails booking=new BookingDetails(request.getArrival(), request.getDeparture(), room, request.getCustomerId(), request.getCustomers().size());
//					booking.setCustomers(request.getCustomers());
//					BookingDetails bookedRequest=bookingDetailsRepository.save(booking);
//					logger.info("Booking success");
//					return "Booking Successful with reservation id"+bookedRequest.getReservationId();
//			}else {
//				logger.error("All rooms booked");
//				throw new ResourceNotAvailableException("All Rooms are Booked,  Please try for a differnt Date");
//			}
//				
//		}
//	    
}
