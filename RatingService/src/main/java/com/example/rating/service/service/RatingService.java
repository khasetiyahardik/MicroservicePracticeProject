package com.example.rating.service.service;

import com.example.rating.service.dto.AddRatingDTO;
import com.example.rating.service.dto.ResponseDTO;

public interface RatingService {
    ResponseDTO addRating(AddRatingDTO addRatingDTO);

    ResponseDTO getAllRatings();

    ResponseDTO getAllRatingsByUserId(Long userId);

    ResponseDTO getAllRatingsOfHotel(Long hotelId);

    ResponseDTO getAllRatingsHavingSameNumberOfRatings(Integer rating);
}
