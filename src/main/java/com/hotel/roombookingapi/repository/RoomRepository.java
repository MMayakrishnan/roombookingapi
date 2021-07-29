package com.hotel.roombookingapi.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hotel.roombookingapi.pojo.AvailabilityStatistics;
import com.hotel.roombookingapi.pojo.Room;



public interface RoomRepository extends JpaRepository<Room, Integer>{


	List<Room> findByRoomNoNotIn(List<Integer> list);
	
	@Query(" select b.room.roomNo from BookingDetails b where PARSEDATETIME(FORMATDATETIME(b.departure, 'yyyy-MM-dd'), 'yyyy-MM-dd')>?1 and PARSEDATETIME(FORMATDATETIME(b.arrival, 'yyyy-MM-dd'), 'yyyy-MM-dd')<=?1")
	public List<Integer> getBookedRooms(String statsDate);
	
	
	@Query("select new com.hotel.roombookingapi.pojo.AvailabilityStatistics(COUNT(roomNo) as total, r.roomType.roomType as roomType) from Room r where r.roomNo not in (?1) group by r.roomType.roomType")
	public List<AvailabilityStatistics> getStats(List<Integer> roomNos);
	
	@Query("select new com.hotel.roombookingapi.pojo.AvailabilityStatistics(COUNT(roomNo) as total, r.roomType.roomType as roomType) from Room r group by r.roomType.roomType")
	public List<AvailabilityStatistics> getStats();
	//SELECT COUNT(ROOM_NO),ROOM_TYPE FROM ROOM R JOIN ROOM_TYPE RT where RT.ROOM_TYPE_ID=R.ROOM_TYPE_ID and  R.ROOM_NO NOT IN (8) GROUP BY ROOM_TYPE



	List<Room> findByRoomType_RoomType(String roomType);

	List<Room> findByRoomType_RoomTypeAndRoomNoNotIn(String roomType, List<Integer> bookedRoomNo);
}
