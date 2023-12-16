package com.example.hotel.service.repository;

import com.example.hotel.service.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    @Query(value = "SELECT * FROM hotel_entity WHERE name=:name",nativeQuery = true)
    Optional<HotelEntity> findByHotelName(String name);
}
