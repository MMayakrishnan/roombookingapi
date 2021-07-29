package com.hotel.roombookingapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotel.roombookingapi.pojo.RoomType;



@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {


	@Query("select roomType from RoomType")
	Set<String> getAllRoomTypes();
	
}
