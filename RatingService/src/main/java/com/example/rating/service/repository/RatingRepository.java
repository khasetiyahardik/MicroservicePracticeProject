package com.example.rating.service.repository;

import com.example.rating.service.document.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    List<Rating> findByUserId(Long userId);

    List<Rating> findByHotelId(Long hotelId);

    List<Rating> findByRating(Integer rating);
}
