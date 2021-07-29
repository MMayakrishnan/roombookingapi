package com.hotel.roombookingapi.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotel.roombookingapi.pojo.BookingDetails;


@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Integer>{

	
	@Query("select distinct r.roomType.roomType from Room r where r.roomNo not in ( select r.roomNo from BookingDetails b where PARSEDATETIME(FORMATDATETIME(b.departure, 'yyyy-MM-dd'), 'yyyy-MM-dd')>=?1 and PARSEDATETIME(FORMATDATETIME(b.arrival, 'yyyy-MM-dd'), 'yyyy-MM-dd')<=?2)")
	public List<String> getAvailableRooms(String string, String depature);

	
	@Query("select distinct r.roomType.roomType from Room r where r.roomNo not in ( select r.roomNo from BookingDetails b where b.departure>=?1 and b.arrival<=?2)")
	public List<Integer> getAvailableRooms(Date arrival, Date depature, String roomType);

	@Query(" select b.room.roomNo from BookingDetails b where PARSEDATETIME(FORMATDATETIME(b.departure, 'yyyy-MM-dd'), 'yyyy-MM-dd')>=?1 and PARSEDATETIME(FORMATDATETIME(b.arrival, 'yyyy-MM-dd'), 'yyyy-MM-dd')<=?2")
	public List<Integer> getBookedRooms(String arrival, String depature);


	@Query(" select b from BookingDetails b where PARSEDATETIME(FORMATDATETIME(b.departure, 'yyyy-MM-dd'), 'yyyy-MM-dd')>=?1 and PARSEDATETIME(FORMATDATETIME(b.arrival, 'yyyy-MM-dd'), 'yyyy-MM-dd')<=?1")
	public List<BookingDetails> getReservations(Date arrival);

	@Query(" select b.room.roomNo from BookingDetails b where PARSEDATETIME(FORMATDATETIME(b.departure, 'yyyy-MM-dd'), 'yyyy-MM-dd')>=?1 and PARSEDATETIME(FORMATDATETIME(b.arrival, 'yyyy-MM-dd'), 'yyyy-MM-dd')<=?2")
	public List<Integer> getBookedRooms(String format, String format2, String roomType); 
}

//
//select roomNo, roomType.roomTypeid
//from rooms 
//where roomNo not in 
//(
//  select roomNo 
//  from booking 
//  where departure >= to_date(? /* desired arrival date */, 'yyyy-mm-dd')
//  and arrival <= to_date(? /* desired departure date */, 'yyyy-mm-dd')
//)
//and roomType.roomTypeid = ? /* desired room type */
//order by roomNo;